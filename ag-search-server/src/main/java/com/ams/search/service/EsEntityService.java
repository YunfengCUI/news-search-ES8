package com.ams.search.service;


import com.ams.search.model.ESDocs;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;

import java.util.ArrayList;
import java.util.Map;

public interface EsEntityService {
    //    创建索引
    boolean creatIndex(String indexName);

    //获取索引
    boolean getIndex(String indexName);

    //    删除索引
    String deleteIndex(String indexName);

    //    添加 更新 单条数据
    //    取决于 实体id

    String addData(String indexName, ESDocs esDocs);
    //    获取单条数据
    Map<String, Object> getData(String indexName, String id);
    //    单条数据 存在 查询
    boolean existsData(String indexName, String id);
    ////修改单条信息
    //    boolean updateDocument(String indexName,EsAuditBasy esAuditBasy);
    //删除单条信息
    DocWriteResponse.Result deleteDocument(String indexName, String id);
    //批量导入数据
    RestStatus bulkAdd_Update_EsOutstandingCase_Document(String indexName, ArrayList<ESDocs> esAuditBasyArrayList);

    //    查询数据 优秀案例
    Object search(String indexName, ESDocs esDocs, Integer formNo, Integer formSize);

//    //    单词查询 严格查询
//    void queryTerm(String indexName,EsAuditBasy esAuditBasy);
//    //    多词查询 严格查询1
//    void queryTerms(String indexName,EsAuditBasy esAuditBasy);
//
//    //    前缀查询
//    void prefixQuery(String indexName,EsAuditBasy esAuditBasy);
//
////    /    通配符查询
//    void wildcardQuery(String indexName,EsAuditBasy esAuditBasy);
}