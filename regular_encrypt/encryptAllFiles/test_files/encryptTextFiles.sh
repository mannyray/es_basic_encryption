#!/bin/bash
#program that encrypts all txt files in current directory using encryptFile.cc, word by word

#The encrypted words are stored as hex numbers in order to avoid storing storing nasty
#characters

#program avoids encrypting txt files that begin with encrypted_ as those are treated
#as already been processed.

javac AESEncryption.java

for line in $(find . -name "*.txt" -not -name "encrypted_*.txt"); do 
	line=${line:2}
	echo $line
	java AESEncryption "$line"
done
