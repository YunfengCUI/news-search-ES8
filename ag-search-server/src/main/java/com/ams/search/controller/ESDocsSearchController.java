package com.ams.search.controller;
import com.ams.search.model.Docs;
import com.ams.search.service.ESDocsService;
import com.ams.search.service.EsEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.io.IOException;

@Api(tags = "新闻控制层")
@RequestMapping("/esdocs")
@RestController
@Slf4j
public class ESDocsSearchController {
    private final String IndexName ="docs";
    @Resource
    @Qualifier("ESDocsServiceImpl")
    ESDocsService docsService;

    @Resource
    EsEntityService esEntityService;

    @PostMapping("/search")
    @ApiOperation(value = "查询数据", notes = "返回数据体")
    public Object search(@RequestBody Docs docs ){
        return docsService.search(IndexName,docs,docs.getFormNo(), docs.getFormSize());
    }
    @GetMapping("/createIndex")
    @ApiOperation(value = "创建索引", notes = "创建索引")
    public Object createIndex(String indexName){
        return esEntityService.creatIndex(indexName);
    }

    @PostMapping("/get/uuid")
    @ApiOperation(value = "根据uuid获取单条新闻")
    @SneakyThrows(IOException.class)
    public Object getUuid(@RequestBody Docs docs){
        return docsService.getById(IndexName,docs.getUuid());
    }
}
