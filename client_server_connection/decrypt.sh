#!/bin/bash
#$1 ~ password
#$2 ~ elasticsearch location 
#Script that decrypts .cfs files
#https://www.elastic.co/blog/found-dive-into-elasticsearch-storage#lucene-index-files
cd $2
find $directory -type f -name "*.cfs.gpg" | while read line; do
    echo $1 | gpg --passphrase-fd 0 $line
done
