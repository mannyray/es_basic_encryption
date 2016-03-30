# Manual
The purpose of this document is to provide basic usage/testing guidelines of the provided code in the repository.


## Regular encryption and no encryption
Storing large quantities of text on Elasticsearch using encryption or no encryption has a very similar design. With no encryption, the data is simply uploaded to Elasticsearch and then retrieved based on queries. With encryption, the data is first encrypted (word by word) and then uploaded. To execute search queries, the search terms must be first encrypted and then passed on to Elasticsearch to find. The results of the queries must then be decrypted to be meaningful to the user. This section will go in detail how to achieve this functionality using the provided code.

Since there a lot of similarities between the encrypted and non encrypted methods, the explanation for both will be in one section where differences between the two will be explicitly mentioned.

The section will cover:
1. [Generating data][]
2. 


###Generating data
If you have your own text data, you can skip this section.

There are many methods of obtaining test data for Elasticsearch. Here are some samples:
* **Random text:** in es\_basic\_encryption/text\_generator/ there is sample code that can be used for this. 
* **Project Gutenberg:** has a lot of books. Here is a great [link](https://www.gutenberg.org/wiki/Gutenberg:The_CD_and_DVD_Project)
* **Wikipedia:**


