package com.ams.search.service.impl;

import com.alibaba.fastjson.JSON;

import com.ams.search.model.ESDocs;
import com.ams.search.service.EsEntityService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("EsOutstandingCaseImpl")
public class EsOutstandingCaseImpl implements EsEntityService {
    @Resource
    RestHighLevelClient restHighLevelClient;


    //    创建索引
    @Override
    @SneakyThrows({IOException.class})
    public boolean creatIndex(String indexName) {
        //1.创建索引请求
        CreateIndexRequest my_index = new CreateIndexRequest(indexName);
        //2.执行创建请求 indicesClient,请求后获得相应
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(my_index, RequestOptions.DEFAULT);

        return createIndexResponse.isAcknowledged();
    }
//验证索引存在
    @Override
    @SneakyThrows({IOException.class})
    public boolean getIndex(String indexName) {
//        验证索引是否存在
        GetIndexRequest request = new GetIndexRequest(indexName);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }
    /**
     * 删除索引
     * @param indexName
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public String deleteIndex(String indexName) {
//        不存在 返回 ，存在 执行删除 返回结果
        if (!getIndex(indexName))
            return "索引不存在";
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(indexName);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteRequest, RequestOptions.DEFAULT);
        return delete.isAcknowledged()?"删除成功":"删除失败";
    }
    /**
     * 添加或者更新 新索引  根据uuid判定
     * @param indexName
     * @param esDocs
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public String addData(String indexName, ESDocs esDocs) {
        //创建对象
        //创建请求
        IndexRequest request = new IndexRequest(indexName);
        //规则 put /jacob_index/_doc/1 文件 id 作为 索引数据id
        request.id(esDocs.getUuid());
        request.timeout(TimeValue.timeValueSeconds(1));
        //将数据放入请求,是Json格式的
        IndexRequest source = request.source(JSON.toJSONString(esDocs), XContentType.JSON);
        //客户端发送请求,获取相应结果
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return index.status().toString();
    }
    /**
     * 获取单条数据
     * @param indexName
     * @param id
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public Map<String, Object> getData(String indexName, String id) {
        GetRequest getRequest = new GetRequest(indexName, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);
        return getResponse.getSource();
    }
    /**
     * 返回单条数据 是否存在
     * @param indexName
     * @param id
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public boolean existsData(String indexName, String id) {
        GetRequest getRequest = new GetRequest(indexName, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);
        return getResponse.isExists();
    }
    /**
     * 删除单条数据
     * @param indexName
     * @param id
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public DocWriteResponse.Result deleteDocument(String indexName, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
        deleteRequest.timeout("1s");
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return delete.getResult();
    }

    /**
     * 批量添加|修改数据
     * @param indexName
     * @param esAuditBasyArrayList
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public RestStatus bulkAdd_Update_EsOutstandingCase_Document(String indexName, ArrayList<ESDocs> esAuditBasyArrayList) {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        //批处理请求
        //批量删除和批量更新都在这里操作
//        for (int i = 0; i < esAuditBasyArrayList.size(); i++) {
//            //不用id会随机生成id
//            bulkRequest.add(
//                    new IndexRequest(indexName)
//                            .id(""+(i+1))
//                            .source(JSON.toJSONString(esAuditBasyArrayList.get(i)),XContentType.JSON));
//        }
        for (ESDocs Es:
             esAuditBasyArrayList) {
            bulkRequest.add(new IndexRequest(indexName)
                    .id(Es.getUuid())
                    .source(JSON.toJSONString(Es),XContentType.JSON)
                    );

        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulk.status();
    }

    /**
     * 优秀案例的查询方法
     * @param indexName
     * @param esOutstandingCase
     * @param formNo
     * @param formSize
     * @return
     */
    @Override
    @SneakyThrows({IOException.class})
    public Object search(String indexName, ESDocs esDocs, Integer formNo, Integer formSize) {
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
        boolQueryBuilder.must(QueryBuilders.matchQuery("esContent",esDocs.getContent()));

//        添加资料标题
        if (esDocs.getTitle()!=null && !esDocs.getTitle().equals(""))
            boolQueryBuilder.must(QueryBuilders.matchQuery("title",esDocs.getTitle()));
        //        分页
        searchSourceBuilder.size(formSize).from(formNo);
//        高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("esContent").preTags("<span style='color:red'>").postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
//        整理请求
        searchRequest.source(searchSourceBuilder);
//         发送请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        // 解析结果
        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit documentFields : hits.getHits()) {
            // 使用新的字段值（高亮），覆盖旧的字段值
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            // 高亮字段
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField name = highlightFields.get("esContent");
            // 替换
            if (name != null){
                Text[] fragments = name.fragments();
                StringBuilder new_name = new StringBuilder();
                for (Text text : fragments) {
                    new_name.append(text);
                }
                sourceAsMap.put("esContent",new_name.toString());
            }
            results.add(sourceAsMap);
        }
        return results;
    }


}
