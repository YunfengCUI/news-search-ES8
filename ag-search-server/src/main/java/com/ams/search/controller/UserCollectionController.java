package com.ams.search.controller;

import com.ams.search.model.UserCollection;
import com.ams.search.model.vo.Result;
import com.ams.search.service.UserCollectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/collection")
@Api(tags = "收藏")
public class UserCollectionController {
    @Resource
    UserCollectionService service;
//    添加收藏
    @ApiOperation("添加收藏")
    @PostMapping("/add")
    Result<Boolean> add(@RequestBody UserCollection userCollection){
        return Result.OK(service.save(userCollection));
    }
//    删除收藏
    @ApiOperation("删除收藏")
    @PostMapping("/remove")
    Result<Boolean> remove(@RequestBody UserCollection userCollection){
        return Result.OK(service.removeById(userCollection));
    }
//    根据用户查找收藏文章
    @ApiOperation(value = "查找用户的收藏",notes = "分页查询")
    @PostMapping("/selectByPage")
    Page<UserCollection> selectByPage(@RequestBody Map<String,Object> params){
        String userid = (String) params.get("user_uid");
        Integer formNo = params.get("formNo")==null? 1: (Integer) params.get("formNo");
        Integer formSize =params.get("formSize")==null? 10:  (Integer) params.get("formSize");
//        if (formNo>=0 && formSize>=0){}
//        else {formNo=1;formSize=10;}
        Page<UserCollection> page = new Page<>(formNo,formSize);
        QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_uid",userid);
        queryWrapper.orderByDesc("collection_id");
        return service.page(page,queryWrapper);
    }
// 根据用户id和新闻id查寻 本新闻是否被收藏
    @ApiOperation(value = "查询新闻是否被收藏")
    @PostMapping("/news_status")
    boolean selectnewsStatus(@RequestBody UserCollection userCollection){
        if (userCollection.getUserUid()==null || userCollection.getArrricleId()==null){
            return false;
        }
            QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_uid",userCollection.getUserUid()).eq("arrricle_id",userCollection.getArrricleId());
        return !(service.getOne(queryWrapper)==null);
    }

}
