Testing of approaches provided in es\_basic\_encryption.

Data was generated using the code from text\_generator (./a.out mywords.txt 2000 100000) to give about 1.8G of generated data (du -sh * -c). The mywords.txt file was /usr/share/dict/words without the words that contain apostrophes was used as a word bank for random word selection when generating 2000 text files with 100000 random words per file.

~~~~~~~~~~~~~~~~~~~~~
regular_encrypt:
~~~~~~~~~~~~~~~~~~~~~

In addition to storing the original text files on the client side, the client must generate the encrypted text files to store on the server. The text encryption was timed to the following:


real	24m7.172s
user	20m20.564s
sys	0m17.848s

and expanded the data to 8.1G (6.3G additional). The expansion is due to the fact that most English words are short compared to a single block aes encryption that is stored in hex format (for example 'the' could map to '0x9b1f6db63bb16c6c7cddd82b0c2358e'). The decision to store the data in hex was made because of potential byte chunks in aes encryption that would not translate well when attempting to upload to elasticsearch.

The upload of the encrypted data took about
real	44m44.151s
user	5m48.472s
sys	1m56.124s
in time and caused the data directory in elasticsearch to inflate to a total 11G.

In the regular\_encrypt/searchDocuments the encrypt then search was put to the test. Since we were storing the encrypted data on the server, to be able to search for a keyword one has to first encrypt the word and then send it off to elasticsearch to find. All the words in text\_generator/mywords.txt that were initially used to generate the random text files were also used to do the search on the files once again. One by one the first 11333 words in the mywords.txt were encrypted and searched for with a total run time of:

real	62m14.157s
user	2m53.328s
sys	0m56.692s

~~~~~~~~~~~~~~~~~~~~~
no_encrypt:
~~~~~~~~~~~~~~~~~~~~~

The same data that was used in regular_encrypt testing was now also being used for no encryption testing. There was no encryption, the data was uploaded immediately and searched immediately as well.

The upload time:


Search time:


