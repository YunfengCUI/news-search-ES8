package com.ams.search.controller;

import com.ams.search.model.WordHistory;
import com.ams.search.service.WordHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wordHostory")
@Api(tags = "搜索记录")
public class WordHistoryController {
    @Resource
    WordHistoryService wordHistoryService;
    @PostMapping("/add")
    @ApiOperation(value = "添加搜索记录")
    String add(@RequestBody WordHistory wordHistory){

//        wordHistory.setCreatedTime(new Date());
        return wordHistoryService.save(wordHistory)? "添加记录成功":"添加失败";
    }
//    查询最近五条搜索记录
    @PostMapping("/get")
    @ApiOperation(value = "获取5条搜索记录")
    List<WordHistory> get(String user){
        int top =5;//返回五条记录
        List<WordHistory> list = wordHistoryService.getTopWord(user,top);
        return wordHistoryService.getTopWord(user,top);
    }
}
