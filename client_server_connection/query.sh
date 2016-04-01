#!/bin/bash
curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{"from" : 0, "size" : 10000,"query":{"match":{"content":"0x5f57ada55dab522f643437b8cf6a968a"}}}' > out.txt