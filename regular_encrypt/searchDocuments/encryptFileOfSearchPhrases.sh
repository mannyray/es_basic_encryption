#!/bin/bash

#File is designed to encrypt every single word on every single line
#thus transforming the search queries to encrypted search queries.


while read line           
do           
    #echo $line
		encryptedtext=""
		for word in $line; do
			encryptedtext="$encryptedtext "$(java AESEncryptWord $word)
			#echo $word;
		done
		echo $encryptedtext           
done <$1   


