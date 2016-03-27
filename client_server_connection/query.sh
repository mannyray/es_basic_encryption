#!/bin/bash
curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{"query":{"match":{"content":"the"}}}' > out.txt