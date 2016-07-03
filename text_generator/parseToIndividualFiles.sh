#!/bin/bash

for fileName in $(find . -name "*" ! -name '*.sh' ! -name '*.txt')
do
	echo "Processing file $fileName"
	title=""
	text=""
	while read line
	do

		if [[ $line == *"<doc id=\""* ]]
			then
				title=$(echo $line | sed 's/.*title=\"//' | sed 's/".*//'); # 's/.*://'
				title=$(echo $title | sed -e 's/ /_/g')
				echo "FOUND: $title"
		elif  [[ $line == *"</doc>"* ]]
			then
				echo $text > "$title.txt"
				text=""
		else
			text="$text $line" 
		fi
	done < $fileName
done


