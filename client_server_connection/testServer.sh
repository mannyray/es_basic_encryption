#!/bin/bash

for word in $(<most_common_words.txt)
do
	#$ (time ls) > outfile 2>&1

	$(time $(./client.sh ubuntu1204-004.student.cs.uwaterloo.ca 45854 $word > out.txt))  > time.txt 2>&1
	result=$(tail -c 8 out.txt)
	while [[  "$result" != *"}"*  ]] ##[[ $url != *.txt ]]
	do
		echo "fail"
		$(time $(./client.sh ubuntu1204-004.student.cs.uwaterloo.ca 45854 $word > out.txt))  > time.txt 2>&1
		result=$(tail -c 8 out.txt)
	done
	echo ""
	echo $word
	echo "fileSize:"
	ls -lh out.txt
	head -c 20 out.txt 
	cat time.txt
	echo ""
done

#TIME="$(sh -c "time myexec -args &> /dev/null" 2>&1)
