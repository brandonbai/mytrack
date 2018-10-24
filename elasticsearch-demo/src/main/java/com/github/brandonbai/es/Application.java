package com.github.brandonbai.es;

import static org.elasticsearch.index.query.QueryBuilders.*;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

/**
 *
 */
public class Application {

    private String index = "company";
    private String type = "employee";
    private Client client = null;

    @Before
    public void before() {
        try{
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.85.141"), 9300));
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void init() {
        try{
            IndicesExistsRequest request = new IndicesExistsRequest(index);
            IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
            if (!response.isExists()) {
                try {
                    CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(index);
                    XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("properties")
                            .startObject("last_name")
                                .field("type","keyword")
                            .endObject()
                            .startObject("first_name")
                                .field("type","keyword")
                            .endObject()
                            .startObject("age")
                                .field("type", "integer")
                            .endObject()
                            .startObject("about")
                                .field("type","text")
                                .field("store","true")
                                .field("index","true")
                                .field("analyzer","ik_max_word")//ik_max_word
                                .field("search_analyzer","ik_max_word")
                                .field("fielddata","true")
                            .endObject()
                            .startObject("interests")
                                .field("type","keyword")
                            .endObject()
                        .endObject()
                    .endObject();
                    cib.addMapping(type, mapping);
                    CreateIndexResponse res= cib.execute().actionGet();
                    System.out.println(res.isAcknowledged());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getOne() {
        GetResponse response = client.prepareGet(index, type, "1").get();
        System.out.println(response.getSourceAsString());
    }

    @Test
    public void searchAll() {
        QueryBuilder query = matchAllQuery();
        SearchResponse resp = client.prepareSearch(index).setQuery(query).setTypes(type).execute().actionGet();
        for (SearchHit searchHit : resp.getHits()) {
            String jsonStr = searchHit.getSourceAsString();
            System.out.println(jsonStr);
        }
    }

    @Test
    public void searchField() {
        QueryBuilder query = matchQuery("last_name", "Ji");
        SearchResponse resp = client.prepareSearch(index).setQuery(query).setTypes(type).execute().actionGet();
        for (SearchHit searchHit : resp.getHits()) {
            String jsonStr = searchHit.getSourceAsString();
            System.out.println(jsonStr);
        }
    }

    @Test
    public void searchFullText() {
        QueryBuilder query = termQuery("about", "喜欢台球");
        SearchResponse resp = client.prepareSearch(index).setQuery(query).setTypes(type).execute().actionGet();
        for (SearchHit searchHit : resp.getHits()) {
            String jsonStr = searchHit.getSourceAsString();
            System.out.println(jsonStr);
        }
    }

    @Test
    public void addIndexAndDocument() {

        try{
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("last_name", "王")
                    .field("first_name", "银轩")
                    .field("about", "宅男，喜欢做饭和台球")
                    .field("interests", new String[]{"做饭", "台球"})
                    .endObject();

            IndexResponse response = client.prepareIndex(index, type)
                    .setId("1")
                    .setSource(xContentBuilder)
                    .get();
            System.out.println(response.getId());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateDocument() throws Exception{
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index);
        updateRequest.type(type);
        updateRequest.id("2");
        updateRequest.doc(XContentFactory.jsonBuilder()
                .startObject()
                .field("first_name","简")
                .field("last_name","史密斯")
                .field("age","25")
                .field("about","喜欢冲浪")
                .field("interests", new String[]{"运动","音乐"})
                .endObject());

        UpdateResponse response;
        try{
            response = client.update(updateRequest).get();
            System.out.println(response.getResult().name());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        client.close();
    }
}
