#!/bin/bash
for word in $(<mywords.txt)
do
		curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{"fields":[], "query":{"match":{"content":"0x9b1f6db63bb16c6c7cddd82b0c2358e"}}}'
    echo "$word"
done
