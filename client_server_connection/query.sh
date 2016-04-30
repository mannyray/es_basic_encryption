#!/bin/bash
curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{"from" : 0, "size" : 10000,"query":{"match":{"content":"gg"}}}' > out.txt