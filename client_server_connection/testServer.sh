#!/bin/bash

argument=2
function output_parse(){
#$1 server port address
for word in $(<most_common_words.txt)
do
	echo $word	#127.0.0.1 can be replaced by your server address
	var=`(/usr/bin/time -f "%e" ./client.sh 127.0.0.1 $argument $word > /dev/null) 2>&1`
	echo $var
	du -sh result.txt |  awk  '{print $1}'
	gg=$(sed -n '2p' result.txt |  awk  '{print $3}')
	echo "${gg::-1}"
	echo
	echo
done
}

argument="$1"
output_parse > data.txt
echo "Search-Word" "Total-time" "Result-size" "Search-Time(ms)" | awk '{ printf "%-15s %-23s %-13s %-15s\n", $1, $2, $3, $4}'
echo "-------------------------------------------------------------------------"
exec 5<"data.txt"
while read line1 <&5 ; do
	read line2 <&5
	read line3 <&5
	read line4 <&5
	read line5 <&5
	read line6 <&5
	echo $line1 $line2 $line3 $line4 | awk '{ printf "%-15s %-23s %-13s %-15s\n", $1, $2, $3, $4}'

done <"data.txt"
exec 5<&-

