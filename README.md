# Manual
The purpose of this document is to provide basic usage/testing guidelines of the provided code in the repository. The code is used to model a client/server relationship and explore the efficiency of encryption techniques on data.


## Regular encryption and no encryption
Storing large quantities of text on Elasticsearch using encryption or no encryption has a very similar design. With no encryption, the data is simply uploaded to Elasticsearch and then retrieved based on queries. With encryption, the data is first encrypted (word by word) and then uploaded. To execute search queries, the search terms must be first encrypted and then passed on to Elasticsearch to find. The results of the queries must then be decrypted to be meaningful to the user. This section will go in detail how to achieve this functionality using the provided code.

Since there a lot of similarities between the encrypted and non encrypted methods, the explanation for both will be in one section where differences between the two will be explicitly mentioned.

The section will cover:


1. [Generating data](#gd)
2. [Cleaning up the data](#cutd)
3. [Uploading data](#ud)
4. [Searching data](#sd)

<a name="gd">
###1. Generating data
</a>
If you have your own text data, you can skip this section.

There are many methods of obtaining test data for Elasticsearch. Here are some samples:
* **Random text:** in [es\_basic\_encryption/text\_generator/](https://github.com/mannyray/es_basic_encryption/tree/master/text_generator) there is sample code that can be used for this (_generateText.cc_ ). 
* **Project Gutenberg:** has a lot of books. Here is a great [link](https://www.gutenberg.org/wiki/Gutenberg:The_CD_and_DVD_Project).
* **Wikipedia:** There are lots of options to consider when downloading data from Wikipedia. Some of these options can be overwhelming by producing too much data. The method tested in this project extracted articles from a specific category of choice using [Petscan](https://meta.wikimedia.org/wiki/PetScan/en).  As an example: go to the [app](https://petscan.wmflabs.org/), and for _categories_ enter _history_ and set _depth_ for _2_. Go to _Ouput_ tab and set Format to _CSV_.

<a name="cutd">
###2. Cleaning up the data
</a>
Elasticsearch can be very picky when uploading text. Certain characters are considered invalid and will provide you with you great pain when uploading the data. The script _process.sh_ in [es\_basic\_encryption/text\_generator/](https://github.com/mannyray/es_basic_encryption/tree/master/text_generator) cleans up a lot of these characters as well as encloses the resultant data in json format in order to prep for uploading.


**Encryption version**

The script _encryptTextFiles.sh_ in [es_basic_encryption/regular_encyrpt/encryptAllFiles/](https://github.com/mannyray/es_basic_encryption/tree/master/regular_encyrpt/encryptAllFiles) encrypts the data, word by word, all under the same key. AES is used to encrypt the data where the cipher words are stored in hex format. The storage format considerably inflates the data size but avoids invalid characters (example: 'well' can be translated to 0x731b31922c9228465e0f0ea51ea7f). Details can be found in the README.md of the script directory. 


Note: If you are testing performance between encrypted and unecrypted methods, then it makes sense to clean up the data as described in the previous paragraph and then encrypt in order to be consistent. If you are not testing, then this is unnecessary. However, if you are not cleaning up the data, then there will be a great difference between the search term 'the' and 'the,' since the encryption of _encryptTextFiles.sh_ encrypts by space separated strings.


<a name="ud">
###3. Uploading data
</a>
Both encrypted and regular data can be uploaded in the same fashion. Assuming you have Elasticsearch running in local on your server, then by running the script _upload.sh_ [es_basic_encryption/regular_encyrpt/uploadDocuments/](https://github.com/mannyray/es_basic_encryption/tree/master/regular_encyrpt/uploadDocuments) you will upload your data.


<a name="sd">
###4. Searching data
</a>
At this point you have a server with data loaded Elasticsearch running in the background. On the server side you want to run _server.sh_ and _client.sh_ on client from [es_basic_encryption/client_server_connection/](https://github.com/mannyray/es_basic_encryption/tree/master/client_server_connection) with argument types and other details provided in script directory.

**Encryption version**
Since data is stored in encrypted format on server, then the client has to first encrypt the search word using _translateKey.cc_ from [es_basic_encryption/regular_encyrpt/searchDocuments/](https://github.com/mannyray/es_basic_encryption/tree/master/regular_encyrpt/searchDocuments) and then use the cipher text as the search word.

**Testing search**
For testing the following data is recorded for each query:
* search word
* total time from sending query, to server response
* query result size
* search time on server end (milliseconds) 

For encrypted you might want to add encryption/decryption overhead time.

* unecrypted: run _testServer.sh_ from [es_basic_encryption/client_server_connection/](https://github.com/mannyray/es_basic_encryption/tree/master/client_server_connection) where _most\_common\_words.txt_ will contain the list of search words to test. The output of the script is to standard output which can be piped to _parseData.sh_ in order to output in a neat table:
```
Search-Word          Total-time           Result-size     Search-Time(ms)
-------------------------------------------------------------------------
command              0m52.773s            65M             222            
heavenly             0m36.143s            43M             193            
milk                 0m47.600s            60M             223            
calm                 0m44.961s            57M             255            
chess                0m29.485s            36M             182            
produce              0m53.601s            66M             240            
snake                0m33.036s            41M             202            
robust               0m30.863s            38M             196            
play                 0m53.502s            66M             244            
bright               0m49.110s            61M             197            
return               0m53.437s            66M             263            
invention            0m43.215s            54M             148 
```

* encrypted: can also run _testServer.sh_ except you will have to encrypt the words in _most\_common\_words.txt_ before using them. In addition you will have to decrypt the text that is returned. The scripts for this will soon be added.

The scripts for graphically representing the data results will also soon be provided.
