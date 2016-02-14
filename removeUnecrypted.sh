#!/bin/bash
#Script that removes unecrypted .cfs files
#$1 ~ location where needs to be done 
#https://www.elastic.co/blog/found-dive-into-elasticsearch-storage#lucene-index-files
cd $1
find $directory -type f -name "*.cfs" | while read line; do
    rm $line
done
