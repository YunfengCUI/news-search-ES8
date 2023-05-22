package com.ams.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ams.search.model.Docs;
import com.ams.search.service.ESDocsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

@Slf4j
@Service("ESDocsServiceImpl")
public class ESDocsServiceImpl implements ESDocsService {
    @Resource
    RestHighLevelClient restHighLevelClient;
    /**
     * 优秀案例的查询方法
     * @param indexName
     * @param docs
     * @param formNo
     * @param formSize
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public Object search(String indexName, Docs docs, Integer formNo, Integer formSize) {
//分页判断
        formNo= (formNo==null||formNo <=1)? 0:formNo;
        formSize= (formSize==null||formSize<=0)? 10:formSize;
        //根据索引创建查询请求
        SearchRequest searchRequest = new SearchRequest(indexName);
        //构建搜索条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//     构建查询语句
//        添加关键字
//        boolQueryBuilder.filter(QueryBuilders.matchQuery("uuid","1"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("content",docs.getContent()));
//        添加查询字典
        //        前端传个字符串 用空格隔开 下面解析    字典名称
        if (docs.getType()!=null) {
            String[] split = docs.getType().split("\\s+");
            ArrayList<String> sites = new ArrayList<String>();
            Collections.addAll(sites, split);
            boolQueryBuilder.filter(QueryBuilders.termsQuery("type", sites));
        }
        //        分页
        searchSourceBuilder.size(formSize).from(formNo);
//        高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("content").preTags("<span style='color:red'>").postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
//        整理请求
        searchRequest.source(searchSourceBuilder);
//         发送请求restHighLevelClient
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        // 解析结果
        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit documentFields : hits.getHits()) {
            // 使用新的字段值（高亮），覆盖旧的字段值
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            // 高亮字段
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField name = highlightFields.get("content");
            // 替换
            if (name != null){
                Text[] fragments = name.fragments();
                StringBuilder new_name = new StringBuilder();
                for (Text text : fragments) {
                    new_name.append(text);
                }
                sourceAsMap.put("content",new_name.toString());
            }
            results.add(sourceAsMap);
        }
        return results;
    }

    @Override
    @SneakyThrows(IOException.class)
    public Object getById(String indexName, String uuid)  {
        SearchRequest searchRequest = new SearchRequest(indexName);
        //构建搜索条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //查询条件，使用QueryBuilders快速匹配
        //termQuery精确
//        QueryBuilders.matchAllQuery(); //匹配所有
        TermQueryBuilder termQuery = QueryBuilders.termQuery("uuid", uuid);
        builder.query(termQuery);
        //分页from、size
//        builder.from(0);
//        builder.size(2);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(builder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //循环遍历查询结果 两层hit才能到数据体
        return searchResponse.getHits().getHits()[0].getSourceAsMap();
    }


}
