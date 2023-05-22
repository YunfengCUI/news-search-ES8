package com.ams.search.controller;

import com.ams.search.model.NewsComment;
import com.ams.search.model.vo.Result;
import com.ams.search.service.NewsCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/newsComment")
@Api(tags = "新闻评论功能")
public class NewsCommentController {
    @Resource
    NewsCommentService service;
    @ApiOperation("添加评论")
    @PostMapping("/add")
    Result<Object> add(@RequestBody NewsComment newsComment){
       return Result.OK(service.save(newsComment));
    }
    @ApiOperation("删除评论")
    @PostMapping("/delete")
    Result<Object> delete(@RequestBody NewsComment newsComment){
        return Result.OK(service.removeById(newsComment));
    }
    @ApiOperation("更新评论")
    @PostMapping("/update")
    Result<Object> update(@RequestBody NewsComment newsComment){
        return Result.OK(service.updateById(newsComment));
    }
    @ApiOperation("分页查询")
    @PostMapping("/page")
    Result<Object> page(@RequestBody Page<NewsComment> page){
        QueryWrapper<NewsComment> queryWrapper = new QueryWrapper<>();
        return Result.OK(service.page(page));
    }
    @ApiOperation("新闻最新评论")
    @PostMapping("/lastely")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "obj_uuid",value ="新闻uid",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "formNo",value ="页号",dataType ="Integer"),
            @ApiImplicitParam(paramType = "query",name = "formSize",value ="页大小",dataType ="Integer"),
    }
    )
    Serializable getlastely(@RequestBody Map<String,Object> params){
        if (params.get("obj_uuid")==null||params.get("formNo")==null||params.get("formNo")==null)
            return Result.error("参数错误");
        String obj_uuid = (String) params.get("obj_uuid");
        Integer formNo = (Integer) params.get("formNo");
        Integer formSize = (Integer) params.get("formSize");
        Page<NewsComment> page = new Page<NewsComment>(formNo,formSize);
        QueryWrapper<NewsComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("obj_uuid",obj_uuid);
        queryWrapper.orderByDesc("created_time");
        List<NewsComment> list= service.getTop(page,queryWrapper);
        return Result.OK(list);
    }
    @ApiOperation("我的评论(最新)")
    @PostMapping("/myComment")
    List<Map<String, Object>> getNewComment(@RequestBody Map<String,Object> params ){
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("n.created_by",params.get("uuid"));
        queryWrapper.select().orderByDesc("n.created_time");
        return service.getUserComment(new Page<Object>((Integer) params.get("formNo"),(Integer) params.get("formSize")),queryWrapper);
    }
    @ApiOperation("管理员管理评论")
    @PostMapping("/allComment")
        List<Map<String, Object>> getAll(@RequestBody Map<String,Object> params ){
            QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("n.created_by",params.get("uuid"));
            queryWrapper.select().orderByDesc("n.created_time");
            return service.getUserComment(new Page<Object>((Integer) params.get("formNo"),(Integer) params.get("formSize")),queryWrapper);
        }
}
