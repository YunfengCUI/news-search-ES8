package com.ams.search;

import org.elasticsearch.client.ElasticsearchClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SSLtest {
    @Resource
    ElasticsearchClient elasticsearchClient;
    @Test
    void esConn(){
        elasticsearchClient.toString();
    }
}
