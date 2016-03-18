#!/bin/bash
#$1 ~ elasticsearch location 
#Script that sets up the initial encryption of the data
#https://www.elastic.co/blog/found-dive-into-elasticsearch-storage#lucene-index-files
cd $1
find $directory -type f -name "*.cfs" | while read line; do
    gpg -c $line
	 rm $line
done

