#!/bin/bash

curl -XDELETE http://localhost:9200/newindex?pretty

curl -XPUT http://localhost:9200/newindex?pretty

curl -XPUT http://localhost:9200/newindex/doc/1 -d @*.txt

