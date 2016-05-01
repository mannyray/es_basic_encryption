translateKey.cc can be used to encrypt a single word.

1. `g++ -o encrypt encryptFile.cc -L/usr/lib/crypto++ -lcrypto++`

2. `./encrypt <key> <search_word> `



For library details go to: [es_basic_encryption/regular_encyrpt/encryptAllFiles/](https://github.com/mannyray/es_basic_encryption/tree/master/regular_encyrpt/encryptAllFiles)



searchThroughAllWords.sh is a rough code that can be used on server side to search for specific words in Elasticsearch without outputing the entire file content.
