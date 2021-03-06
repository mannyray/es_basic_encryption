# Server/client model


`make` can be used to compile the server.java and client.java.


1. [Server](#s)
2. [Client](#c)



<a name="s">
##1. Server
</a>
####Running:
Make sure Elasticsearch is running in the background on server. In new terminal window, run `pwd` which will be _directory_. Then run `./server.sh directory`. The immediate output will be `SERVER_PORT=#NUM`. All clients that connect to server will need to know the _NUM_.

####Explanation:
Server is always running, waiting for new clients to connect. Server handles clients, one by one. For each client, it takes their search word, writes it down to script _query.sh_, and executes the script. In return, the script runs the query through Elasticsearch and has it write the result to _out.txt_. The server.sh, then gets the contents of _out.txt_ and sends back over to the client and closes off the connection.


<a name="c">
##2. Client
</a>
####Running:
Client runs `./client.sh  <server_address> <NUM> <query_word>`
* server\_address: address on which server is running can be ip or even name (such as ubuntu1204-004.student.cs.uwaterloo.ca)
* n\_port:  port used by server (same as _NUM_ from above).
* query word: word that you are searching for

####Explanation:
Client executes the script and recieves the query results on standard output.
