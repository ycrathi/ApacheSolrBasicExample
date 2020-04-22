package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApacheSolrDemoApplication {
	
	private final String collectionName = "gettingstarted";

	public static void main(String[] args) {
		SpringApplication.run(ApacheSolrDemoApplication.class, args);
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String welcomeMsg() {
		return "Welcome to corona vacation";
	}

	@RequestMapping(path = "/solr", method = RequestMethod.GET)
	public String welcomeToSolrMsg() {
		return "Welcome to solr";
	}

	@RequestMapping(path = "solr/list", method = RequestMethod.GET)
	public String httpSolrQueryParamExample() {
		StringBuilder text = new StringBuilder();
		SolrClient client = getSolrClient();
		final Map<String, String> queryParamMap = new HashMap<String, String>();
		queryParamMap.put("q", "*:*");
		// queryParamMap.put("fl", "id, name");
		queryParamMap.put("sort", "id asc");
		queryParamMap.put("rows", "1000");
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);

		QueryResponse response;
		try {
			response = client.query(collectionName, queryParams);
			final SolrDocumentList documents = response.getResults();

			// text.append("Number of documents : " + documents.getNumFound());
			// text.append("<br><br>");
			text.append(documents.toString() + "<br>");
			// for (SolrDocument document : documents) {
			// final String id = (String) document.getFirstValue("id");
			// final String name = (String) document.getFirstValue("name");
			// text.append(document.toString() + "<br>");

			// }
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text.toString();
	}

	@RequestMapping(path = "solr/cluster", method = RequestMethod.GET)
	public String httpSolrClusterExample() {
		final SolrClient client = getSolrClient();

		final SolrRequest<CollectionAdminResponse> request = new CollectionAdminRequest.ClusterStatus();

		NamedList<Object> response;
		try {
			response = client.request(request);
			final NamedList<Object> cluster = (NamedList<Object>) response.get("cluster");
			final List<String> liveNodes = (List<String>) cluster.get("live_nodes");
			return liveNodes.toString();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error";
	}

	@RequestMapping(path = "solr/list/{count}", method = RequestMethod.GET)
	public String httpSolrQueryExample(@PathVariable int count) {
		StringBuilder text = new StringBuilder();
		SolrClient client = getSolrClient();
		final SolrQuery query = new SolrQuery("*:*");
		query.addField("id");
		query.addField("name");
		query.setSort("id", ORDER.asc);
		query.setRows(count);

		QueryResponse response;
		try {
			response = client.query(collectionName, query);
			final SolrDocumentList documents = response.getResults();
			text.append(documents.toString() + "<br>");
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text.toString();
	}

	@RequestMapping(path = "/solr/add/{text}", method = RequestMethod.GET)
	public String httpSolrAddDocument(@PathVariable String text) {
		final SolrClient client = getSolrClient();

		final SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", UUID.randomUUID().toString());
		doc.addField("name", text + " Time = " + System.currentTimeMillis());

		try {
			// UpdateResponse updateResponse = client.add(collectionName, doc);
			client.add(collectionName, doc);
			// Indexed documents must be committed
			client.commit(collectionName);

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Document is added<br>" + doc.toString();
	}

	@RequestMapping(path = "/solr/delete/{id}", method = RequestMethod.GET)
	public String httpSolrDeleteByID(@PathVariable("id") String id) {
		final SolrClient client = getSolrClient();
		try {
			client.deleteById(collectionName, id);
			// Saving the document
			client.commit(collectionName);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id + " ID deleted";
	}

	@RequestMapping(path = "/solr/search/{text}", method = RequestMethod.GET)
	public String httpSolrSearchDocument(@PathVariable String text) {
		StringBuilder builder = new StringBuilder();
		SolrClient client = getSolrClient();
		final SolrQuery query = new SolrQuery("name:" + text + "*");
		// query.addField("id");
		// query.addField("name");
		// query.setSort("id", ORDER.asc);
		query.setRows(10);

		QueryResponse response;
		try {
			response = client.query(collectionName, query);
			final SolrDocumentList documents = response.getResults();
			builder.append(documents.toString() + "<br>");
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}

	public SolrClient getSolrClient() {
		final String solrUrl = "http://localhost:8983/solr";
		return new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000).withSocketTimeout(60000).build();
	}

}
