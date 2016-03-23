#!/bin/bash


for word in $(<mywords.txt)
do
	encrypted=$(./encrypt mykey $word)
	echo $word
	curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{"fields":[], "query":{"match":{"content":"'$encrypted'"}}}'
    #echo "$encrypted"
done
