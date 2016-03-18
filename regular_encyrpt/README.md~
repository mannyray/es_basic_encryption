The purpose of regular_encrypt is to provide some sense of encryption when dealing with elasticsearch. This method works well when uploading multiple text documents in order to later on search in what files specific keywords are present.

Before uploading the text files to elasticsearch server, they are processed by the client. The client encrypts each word in each file using the same key. This means that the same word will map to the same ciphertext for all files. Once this process is complete, they are uploaded to elasticsearch.

When a client wants to determine what files contain a certain keyword: they first encrypt the search word using the same key from before and then send the encrypted search word to elasticsearch to find. Elasticsearch will return the file numbers that the word is present in.

This search method can be extended to search for consecutive words and phrases as well.

This method of storing data is not completely secure as for one, it provides an adversary the ability to do frequency analysis on the stored elasticsearch files once they have access to the server.


In terms of setting up the system one should look at the folders in the following order: encryptAllFiles>uploadDocuments>searchDocuments

