#!/bin/bash

lineNumber=1
match=0
regularMatch=0
encryptedMatch=0
differenceReg=0
differenceEnc=0
searchTimeGreaterForRegular=0
theNumberSearchTime=0
totalRegTime=0
regularArray=""
totalEncTime=0
encryptArray=""
while read -r -u3 regular; read -r -u4 encrypted; do 
  #printf '%s;%s\n' "$product" "$comp"
	if [[ $regular != *"4.0K"* ]] && [[ $encrypted != *"4.0K"* ]]
	then
		match=$((match+1))
	fi	
	
	if [[ $regular != *"4.0K"* ]] 
	then
		regularMatch=$((regularMatch+1))
	fi

	if [[ $encrypted != *"4.0K"* ]] 
	then 
		encryptedMatch=$((encryptedMatch+1))
	fi


	if [[ $encrypted != *"4.0K"* ]] && [[ $regular == *"4.0K"* ]]
	then 
		differenceEnc=$((differenceEnc+1))
	fi

	if [[ $encrypted == *"4.0K"* ]] && [[ $regular != *"4.0K"* ]]
	then 
		differenceReg=$((differenceReg+1))
	fi

	N=4
	searchTimeRegular=$(echo $regular | cut -d " " -f $N)
	regularArray=$regularArray","$searchTimeRegular
	totalRegTime=$((totalRegTime + searchTimeRegular))

	searchTimeEncrypted=$(echo $encrypted | cut -d " " -f $N)
	encryptArray=$encryptArray","$searchTimeEncrypted
	totalEncTime=$((totalEncTime + searchTimeEncrypted))

	if (( searchTimeRegular < searchTimeEncrypted )); then
  	searchTimeGreaterForRegular=$((searchTimeGreaterForRegular+1))
	else
		if [[ $encrypted == *"4.0K"* ]] && [[ $regular != *"4.0K"* ]]
		then 
			theNumberSearchTime=$((theNumberSearchTime+1))
		fi
	fi

	lineNumber=$((lineNumber+1))
done 3<regularOutput.txt 4<encryptedOutput.txt


echo "function ura_plots() " > ura_plots.m
#echo "rng default;">>ura_plots.m
regularArray=${regularArray:1}
encryptArray=${encryptArray:1}
echo "y=[$regularArray]">>ura_plots.m
echo "g=[$encryptArray]">>ura_plots.m
echo "cdfplot(y)">>ura_plots.m
echo "hold on">>ura_plots.m
echo "cdfplot(g)">>ura_plots.m
echo "gg = cdfplot(g)">>ura_plots.m
echo "set(gg,'color','r')">>ura_plots.m
echo "saveas(gcf,'results.png')">>ura_plots.m
echo "end">>ura_plots.m



echo "Total query count: "$lineNumber
echo "Regular and encrypted match: "$match
echo "Encrypted match: "$encryptedMatch
echo "Encrypted match only: "$differenceEnc
echo "Regular match: "$regularMatch
echo "Regular match only: "$differenceReg
echo "Regular query time greater than encrypted: "$searchTimeGreaterForRegular
echo "Where the query time wasnt greather because of search: "$theNumberSearchTime
echo "Total encrypted time: "$totalEncTime
echo "Total regular time: "$totalRegTime

