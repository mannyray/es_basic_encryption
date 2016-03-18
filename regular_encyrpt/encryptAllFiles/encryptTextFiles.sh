#!/bin/bash
#program that encrypts all txt files in current directory using encryptFile.cc, word by word
#with the help of crypto++
#The encrypted words are stored as hex numbers in order to avoid storing storing nasty
#characters
#program avoids encrypting txt files that begin with encrypted as those are treated
#as already been processed.

#first we compile encryptFile.cc
#gcc version 4.9.2 (Ubuntu 4.9.2-10ubuntu13)
#In the file encrypt
g++ -o encrypt encryptFile.cc -L/usr/lib/crypto++ -lcrypto++

#then we encrypt all the appropriate file using ./encrypt

#find . -name "*.txt" -not -name "encrypted*.txt" | ./encrypt yourKeyString


for line in $(find . -name "*.txt" -not -name "encrypted*.txt"); do 
	 line=${line:2}
	 echo $line
     ./encrypt mykey "$line"
done
