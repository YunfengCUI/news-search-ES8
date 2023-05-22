package com.ams.search.controller;

import com.ams.search.model.HotWord;
import com.ams.search.model.vo.Result;
import com.ams.search.service.HotWordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/HotWord")
@Api(tags = "热词控制")
public class HotWordController {
    @Resource
    HotWordService service;
    @PostMapping("/add")
    @ApiOperation(value = "添加热词")
    String AddHotWord(@RequestBody HotWord hotWord){
        return service.save(hotWord)? "添加成功":"添加失败";
    }
    @PostMapping("/getAll")
    @ApiOperation(value = "获取热词")
    Result<Object> GetAll(){
        return Result.OK(service.getHotWord());
    }
}
