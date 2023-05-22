package com.ams.search.controller;


import com.ams.search.model.ESDocs;
import com.ams.search.service.EsEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Api(tags = "优秀案例控制层")
@RequestMapping("/esOutstandingCase")
@RestController
@Slf4j
public class ESDocsController {
    private final String IndexName ="my_1012";
    @Resource
    @Qualifier("EsOutstandingCaseImpl")
    EsEntityService esEntityService;

    @PostMapping("/AddOrUpdataIndexData")
    @ApiOperation(value = "添加/更新单条索引", notes = " 成功 | 失败")
    public String addOrUpdataIndexData(@ApiParam(value = "my_1012",readOnly = true)String index, ESDocs esDocs ){
        return esEntityService.addData(IndexName,esDocs);
    }

    @PostMapping("/existIndexData")
    @ApiOperation(value = "单条数据存在查询", notes = " 存在 | 不存在")
    public boolean existIndexData(@ApiParam(name = "索引_doc的id") String id ){
        return esEntityService.existsData(IndexName,id);
    }
    @PostMapping("/getIndexData")
    @ApiOperation(value = "单条数据查询", notes = " 返回数据体")
    public Map<String, Object> getIndexData(String id ){
        return esEntityService.getData(IndexName,id);
    }
    @PostMapping("/deleteIndexData")
    @ApiOperation(value = "删除单条数据", notes = "返回数据体")
    public DocWriteResponse.Result deleteIndexData(String id ){

        return esEntityService.deleteDocument(IndexName,id);
    }
    @PostMapping("/bulkAdd_UpdateDocument")
    @ApiOperation(value = "批量添加|修改数据", notes = "返回数据体")
    public RestStatus bulkAdd_UpdateDocument(String indexName, ArrayList<ESDocs> esOCArrayList ){

        return esEntityService.bulkAdd_Update_EsOutstandingCase_Document(IndexName,esOCArrayList);
    }
    @PostMapping("/search")
    @ApiOperation(value = "查询数据", notes = "返回数据体")
    public Object search(@RequestBody ESDocs esDocs ){
        return esEntityService.search(IndexName,esDocs,esDocs.getFormNo(), esDocs.getFormSize());
    }


}
