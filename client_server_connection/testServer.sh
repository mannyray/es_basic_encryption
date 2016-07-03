#!/bin/bash

argument=46486
function output_parse(){
#$1 server port address
while read word; do

	echo $word	#127.0.0.1 can be replaced by your server address
	var=`(/usr/bin/time -f "%e" ./client.sh 129.97.167.135 "$argument" "$word" > /dev/null) 2>&1`
	echo $var
	du -sh result.txt |  awk  '{print $1}'
	gg=$(sed -n '2p' result.txt |  awk  '{print $3}')
	echo "${gg::-1}"
	echo
	echo
done <most_common_words.txt
}

argument="$1"
#output_parse > data1.txt

CollumnLength=$(wc -L data1.txt | awk '{print $1}')
CollumnLength=$((CollumnLength)) #assuming longest word search length is greater than 13
variable=""
for a in `seq $CollumnLength`:
do
	variable="$variable ";
done

#awk -v var="$variable" 'BEGIN {print var}'
echo "Search-Word" "Total-time" "Result-size" "Search-Time(ms)" | awk '{ printf "%-50s %-23s %-13s %-15s\n", $1, $2, $3, $4}'
echo "----------------------------------------------------------------------------------------------------------"
exec 5<"data1.txt"
while read line1 <&5 ; do
	read line2 <&5
	read line3 <&5
	read line4 <&5
	read line5 <&5
	read line6 <&5
	line1=$(echo $line1 | tr " " _)

	echo "toolong" "$line2" $line3 $line4 | awk -v var="$variable" '{ printf "%-50s %-23s %-13s %-15s\n", $1, $2, $3, $4}'

done <"data1.txt"
exec 5<&-

