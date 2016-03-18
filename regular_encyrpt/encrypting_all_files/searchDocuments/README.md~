After encrypting your documents and uploading them, one can search for key words to see in what files they exist.
Here is a sample query/output that can be used after your encrypted documents have been uploaded.  Will return all the files that contain the encrypted word: 

> curl -XGET http://localhost:9200/newindex/_search?pretty=true -d '{"fields":[], "query":{"match":{"content":"0x9b1f6db63bb16c6c7cddd82b0c2358e"}}}'
{
  "took" : 16,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "failed" : 0
  },
  "hits" : {
    "total" : 3,
    "max_score" : 0.04867162,
    "hits" : [ {
      "_index" : "newindex",
      "_type" : "doc",
      "_id" : "4",
      "_score" : 0.04867162
    }, {
      "_index" : "newindex",
      "_type" : "doc",
      "_id" : "1",
      "_score" : 0.016478628
    }, {
      "_index" : "newindex",
      "_type" : "doc",
      "_id" : "3",
      "_score" : 0.011441203
    } ]
  }
}
[1]+  Done                    curl -XGET http://localhost:9200/newindex/_search

The query returns "_id" # which isn't the actual file name as you may notice. However if you analyze the code that is used to upload the original encrypted files (upload.sh), then one will notice that it is done in alphabetical order. The nth file you upload will correspond to the nth _id. This mapping can be done at the client side for more meaningful output.


