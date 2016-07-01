#!/bin/bash

curl -XDELETE http://localhost:9200/newindex?pretty

curl -XPUT http://localhost:9200/newindex?pretty

curl -XPUT http://localhost:9200/newindex/doc/1 -d @35386-0.txt

curl -XPUT http://localhost:9200/newindex/doc/2 -d @47115-0.txt

curl -XPUT http://localhost:9200/newindex/doc/3 -d @48359-0.txt

curl -XPUT http://localhost:9200/newindex/doc/4 -d @50379-0.txt

curl -XPUT http://localhost:9200/newindex/doc/5 -d @moby10b.txt

curl -XPUT http://localhost:9200/newindex/doc/6 -d @out.txt

curl -XPUT http://localhost:9200/newindex/doc/7 -d @pg17690.txt

curl -XPUT http://localhost:9200/newindex/doc/8 -d @pg41010.txt

curl -XPUT http://localhost:9200/newindex/doc/9 -d @pg8582.txt

