#!/bin/bash
# Create dummy input
#for i in $(seq 1 10) ; do echo $i >>data ; done

# Create new file handle 5
exec 5< data

# Now you can use "<&5" to read from this file
echo "Search-Word" "Total-time" "Result-size" "Search-Time(ms)" | awk '{ printf "%-20s %-20s %-15s %-15s\n", $1, $2, $3, $4}'
echo "-------------------------------------------------------------------------"
while read line1 <&5 ; do
	read line2 <&5
	read line3 <&5
	read line4 <&5
	read line5 <&5
	read line6 <&5
	read line7 <&5
	read line8 <&5
	read line9 <&5
	read line10 <&5
	read line11 <&5
	arr=($line2)
	arr2=($line8)
	arr3=($line10)
	line2=${arr[1]}
	line8=${arr2[4]}
	line10=${arr3[2]}
        echo $line6 $line2 $line8 ${line10::-1} | awk '{ printf "%-20s %-20s %-15s %-15s\n", $1, $2, $3, $4}'

done

# Close file handle 5
exec 5<&-
