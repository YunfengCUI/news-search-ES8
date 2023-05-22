package com.ams.search.controller.personnelManagement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ams.search.model.personnelManagement.UserMenuRel;
import com.ams.search.model.vo.Result;
import com.ams.search.service.personnelManagement.UserMenuRelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户菜单关联表 前端控制器
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */

@Api(tags = "用户菜单关联表 服务")
@RequestMapping("/userMenuRel")
@RestController
public class UserMenuRelController {

    @Autowired
    private UserMenuRelService userMenuRelService;

    @ApiOperation("创建单个UserMenuRel")
    @PostMapping("/add")
    public Result<Boolean> insert(@RequestBody UserMenuRel userMenuRel) {
        return Result.OK(userMenuRelService.save(userMenuRel));
    }


    @ApiOperation("删除单个UserMenuRel")
    @PostMapping("/delete/{uuid}")
    public Result<Boolean> deleteById(@PathVariable("uuid") String uuid) {
        return Result.OK(userMenuRelService.removeById(uuid));
    }

    @ApiOperation("编辑单个UserMenuRel")
    @PostMapping("/updateByUuId")
    public Result<Boolean> updateByUuId(@RequestBody UserMenuRel userMenuRel) {
        return Result.OK(userMenuRelService.updateById(userMenuRel));
    }

    @ApiOperation("查询单个UserMenuRel")
    @GetMapping("/selectByUuid/{uuid}")
    public Result<UserMenuRel> selectByUuid(@PathVariable("uuid") String uuid) {
        return Result.OK(userMenuRelService.getById(uuid));
    }

    @ApiOperation("查询分页UserMenuRel")
    @GetMapping("/selectPage")
    public Result<IPage<UserMenuRel>> selectPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 UserMenuRel userMenuRel) {
        QueryWrapper<UserMenuRel> queryWrapper = new QueryWrapper();
        IPage<UserMenuRel> page = userMenuRelService.page(new Page<>(pageNo,pageSize), queryWrapper);
        return Result.OK(page);
    }

    @ApiOperation("菜单授权")
    @PostMapping("/menuEmpower")
    public Result<String> menuEmpower(@RequestBody String userMenuListStr){
        Result<String> result = new Result<>();
        try{
            if(userMenuListStr == null){
                result.error500("授权失败,参数不正确");
            }
            JSONObject obj = (JSONObject) JSON.parse(userMenuListStr);
            List<UserMenuRel> userMenuList =  JSON.parseArray(obj.get("userMenuListStr").toString(),UserMenuRel.class);
           // List<UserMenuRel> userMenuList = JSON.parseArray(userMenuListStr,UserMenuRel.class);
            //存储已经删除过的用户编号，已经删除过不需要在次删除
            List<String> userIdList = new ArrayList<>();
            //添加之前优先删除要授权人已经有的菜单权限
            //开始循环添加菜单权限
            for (UserMenuRel userMenuRel : userMenuList) {
                //判断该用户的菜单是不是已经清除。如果没有清除则清除之后再次添加
                if(userIdList.indexOf(userMenuRel.getUserId()) == -1){
                    userIdList.add(userMenuRel.getUserId());
                    QueryWrapper<UserMenuRel> queryWrapper = new QueryWrapper();
                    queryWrapper.eq(userMenuRel.getUserId() != null,"USER_ID",userMenuRel.getUserId());
                    userMenuRelService.remove(queryWrapper);
                }
                userMenuRel.setUserMenuRelId(StringUtil.getGuuid());
                userMenuRelService.save(userMenuRel);
            }
            result.success("授权成功");
        }
        catch (Exception e){
            result.error500("授权失败");
        }
        return result;
    }
}
