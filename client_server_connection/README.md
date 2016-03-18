# es_basic_encryption
Elasticsearch version 2.1.1 with config/elasticsearch.yml containing the following info:
index.compound_format: 0
index.compound_on_flush: 0
index.merge.policy.use_compound_file: 0

This is done in order to limit the amount of compound files are the size of data increases.
