# ApacheSolrBasicExample
Basic example of solr implementation with Springboot Rest application

Download Apache solr from<br>
-> https://lucene.apache.org/solr/downloads.html<br>
Add Maven Dependecy<br>
-> https://mvnrepository.com/artifact/org.apache.solr/solr-solrj/8.5.0<br>


How to configure solr ?<br>
-> Follow steps https://lucene.apache.org/solr/guide/8_5/solr-tutorial.html<br>
Create collection with name of gettingstarted. After setup everything start apche solr server<br>

Solr Admin UI console<br>
http://localhost:8983<br>

Springboot console<br>
http://localhost:8080<br>

Solr Admin Console<br>
http://localhost:8983/solr/#/gettingstarted/query<br>

Springboot Query's <br>
http://localhost:8080/solr/list<br>
http://localhost:8080/solr/list/1<br>
http://localhost:8080/solr/search/<search keyword><br>
http://localhost:8080/solr/add/<add text><br>
http://localhost:8080/solr/delete/<id><br>
