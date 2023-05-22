package com.ams.search.service;


import com.ams.search.model.Docs;

import java.io.IOException;

public interface ESDocsService {
    /**
     * 分页查询
     * @param indexName
     * @param docs
     * @param formNo
     * @param formSize
     * @return
     */
    Object search(String indexName, Docs docs, Integer formNo, Integer formSize);

    /**
     * 根据数据中的uuid 查询新闻体
     * @param indexName
     * @param uuid
     * @return
     * @throws IOException
     */
    Object getById(String indexName,String uuid) throws IOException;

}