#Important to Note:

encryptTextFiles.sh encrypts all .txt files in current directory using AESEncryption.java, word by word.

The encrypted words are stored in hex format in order to avoid nasty characters and ease of storage on elasticsearch.

Program avoids encrypting .txt files that begin with "encrypted" as those are treated as have already been encrypted in case multiple reruns are made in the same directory.


