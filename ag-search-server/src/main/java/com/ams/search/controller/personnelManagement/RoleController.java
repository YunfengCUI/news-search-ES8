package com.ams.search.controller.personnelManagement;

import com.ams.search.model.personnelManagement.Role;
import com.ams.search.model.vo.Result;
import com.ams.search.service.personnelManagement.RoleService;
import com.ams.search.utils.LoginUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.ams.search.model.personnelManagement.Role;
//import com.ams.search.model.personnelManagement.User;
//import com.ams.search.model.vo.Result;
//import com.ams.search.service.personnelManagement.;
//import com.ams.search.utils.LoginUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.DateUtil;
import utils.StringUtil;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */

@Api(tags = "角色表 服务")
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("创建单个Role")
    @PostMapping("/add")
    public Result<Role> insert(@RequestBody Role role) {
        Result<Role> result=new Result<Role>();
        QueryWrapper<Role> queryWrapper = new QueryWrapper();
        queryWrapper.eq("ROLE_NUMBER",role.getRoleNumber());
        List<Role> roleList = roleService.list(queryWrapper);

        QueryWrapper<Role> queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("ROLE_SORT",role.getRoleSort());
        List<Role> roleList2 = roleService.list(queryWrapper2);
        if(roleList.size()>0 || roleList2.size()>0){//角色不唯一
            if (roleList.size()>0){
                result.error500("添加的角色编码已存在，请重新添加");
                return result;
            }else if (roleList2.size()>0){
                result.error500("添加的角色排序已存在，请重新添加");
                return result;
            }
        }else{
            role.setRoleId(StringUtil.getGuuid());
            role.setCreateUserId(LoginUserInfo.getLoginUserId());
            role.setCreateUserName(LoginUserInfo.getLoginUserName());
            role.setUpdateUserId(LoginUserInfo.getLoginUserId());
            role.setUpdateUserName(LoginUserInfo.getLoginUserName());
            role.setCreateTime(DateUtil.getCurrentTime());
            role.setUpdateTime(DateUtil.getCurrentTime());
            role.setIsDelete(0);
        }
        Boolean save = roleService.save(role);
        result.setSuccess(save);
        return result.success("新增成功");
    }


    @ApiOperation("删除单个Role")
    @PostMapping("/delete/{uuid}")
    public Result<Boolean> deleteById(@PathVariable("uuid") String uuid) {
        return Result.OK(roleService.removeById(uuid));
    }

    @ApiOperation("编辑单个Role")
    @PostMapping("/updateByUuId")
    public Result<Role> updateByUuId(@RequestBody Role role) {
        role.setUpdateTime(DateUtil.getCurrentTime());
        role.setUpdateUserId(LoginUserInfo.getLoginUserId());
        role.setUpdateUserName(LoginUserInfo.getLoginUserName());

        Result<Role> result = new Result<Role>();
        Role roleById = roleService.getById(role.getRoleId());
        if (!roleById.getRoleNumber().equals(role.getRoleNumber())){
            QueryWrapper<Role> queryWrapper = new QueryWrapper();
            queryWrapper.eq("ROLE_NUMBER",role.getRoleNumber());
            queryWrapper.eq("IS_DELETE",0);
            List<Role> roleList = roleService.list(queryWrapper);
            if(roleList.size()>0) {//角色编码重复
                result.setSuccess(false);
                result.setMessage("修改的角色编码：" + role.getRoleNumber() + "已存在，请重新修改");
                return result;
            }
        }
        if (!roleById.getRoleSort().equals(role.getRoleSort())){
            QueryWrapper<Role> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("ROLE_SORT",role.getRoleSort());
            queryWrapper2.eq("IS_DELETE",0);
            List<Role> roleList2 = roleService.list(queryWrapper2);
            if(roleList2.size()>0) {//角色编码重复
                result.setSuccess(false);
                result.setMessage("修改的角色排序：" + role.getRoleSort() + "已存在，请重新修改");
                return result;
            }
        }
        Boolean update = roleService.updateById(role);
        result.setSuccess(update);
        return result.success("修改成功");
    }

    @ApiOperation("查询单个Role")
    @GetMapping("/selectByUuid/{uuid}")
    public Result<Role> selectByUuid(@PathVariable("uuid") String uuid) {
        return Result.OK(roleService.getById(uuid));
    }

    @ApiOperation("查询分页Role")
    @GetMapping("/selectPage")
    public Result<IPage<Role>> selectPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                          Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper();
        queryWrapper.like(role.getRoleName() != null,"ROLE_NAME",role.getRoleName());
        queryWrapper.like(role.getRoleNumber() != null,"ROLE_NUMBER",role.getRoleNumber());
        queryWrapper.orderByAsc("ROLE_SORT");
        IPage<Role> page = roleService.page(new Page<>(pageNo,pageSize), queryWrapper);
        return Result.OK(page);
    }
}
