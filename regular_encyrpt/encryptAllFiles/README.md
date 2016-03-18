encryptTExtFiles.sh encrypts all .txt files in current directory using encryptFile.cc, word by word with the help of crypto++ 5.6.3.

The encrypted words are stored in hex format in order to avoid nasty characters and ease of storage on elasticsearch.

Program avoids encrypting .txt files that begin with "encrypted" as those are treated as have already been encrypted in case multiple reruns are made in the same directory.

-encryptFile.cc compiled using (gcc version 4.9.2)
More on crypto++:
-https://www.cryptopp.com/
-sudo apt-get install libcrypto++-dev libcrypto++-doc libcrypto++-utils



