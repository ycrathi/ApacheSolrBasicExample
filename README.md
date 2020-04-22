# ApacheSolrBasicExample
Basic example of solr implementation with Springboot Rest application

Download Apache solr from
-> https://lucene.apache.org/solr/downloads.html
Add Maven Dependecy
-> https://mvnrepository.com/artifact/org.apache.solr/solr-solrj/8.5.0


How to configure solr ?
-> Follow steps https://lucene.apache.org/solr/guide/8_5/solr-tutorial.html
Create collection with name of gettingstarted. After setup everything start apche solr server

Solr Admin UI console
http://localhost:8983

Springboot console
http://localhost:8080

Solr Admin Console
http://localhost:8983/solr/#/gettingstarted/query

Springboot Query's 
http://localhost:8080/solr/list
http://localhost:8080/solr/list/1
http://localhost:8080/solr/search/<search keyword>
http://localhost:8080/solr/add/<add text>
http://localhost:8080/solr/delete/<id>
