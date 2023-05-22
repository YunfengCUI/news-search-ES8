package com.ams.search.controller.personnelManagement;

import com.ams.search.model.personnelManagement.Menu;
import com.ams.search.service.personnelManagement.MenuService;
import com.ams.search.utils.LoginUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.ams.search.model.personnelManagement.Menu;
import com.ams.search.model.vo.Result;
//import com.ams.search.service.personnelManagement.MenuService;
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
 * 菜单表 前端控制器
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */

@Api(tags = "菜单表 服务")
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("创建单个Menu")
    @PostMapping("/add")
    public Result<Boolean> insert(@RequestBody Menu menu) {
        menu.setMenuId(StringUtil.getGuuid());
        menu.setCreateUserId(LoginUserInfo.getLoginUserId());
        menu.setCreateUserName(LoginUserInfo.getLoginUserName());
        menu.setUpdateUserId(LoginUserInfo.getLoginUserId());
        menu.setUpdateUserName(LoginUserInfo.getLoginUserName());
        menu.setCreateTime(DateUtil.getCurrentTime());
        menu.setUpdateTime(DateUtil.getCurrentTime());
        menu.setIsDelete(0);
        return Result.OK(menuService.save(menu));
    }

    @ApiOperation("删除Menu")
    @PostMapping("/deleteMenu/{uuid}")
    public Result<Boolean> deleteMenu(@PathVariable("uuid") String uuid) {
        return Result.OK(menuService.deleteById(uuid));
    }

    @ApiOperation("删除单个Menu")
    @PostMapping("/delete/{uuid}")
    public Result<Boolean> deleteById(@PathVariable("uuid") String uuid) {
        Menu menu = new Menu();
        menu.setUpdateUserId(LoginUserInfo.getLoginUserId());
        menu.setUpdateUserName(LoginUserInfo.getLoginUserName());
        menu.setUpdateTime(DateUtil.getCurrentTime());
        menu.setMenuId(uuid);
        menu.setIsDelete(1);
        return Result.OK(menuService.updateById(menu));
    }

    @ApiOperation("编辑单个Menu")
    @PostMapping("/updateByUuId")
    public Result<Boolean> updateByUuId(@RequestBody Menu menu) {
        menu.setUpdateUserId(LoginUserInfo.getLoginUserId());
        menu.setUpdateUserName(LoginUserInfo.getLoginUserName());
        menu.setUpdateTime(DateUtil.getCurrentTime());
        return Result.OK(menuService.updateById(menu));
    }

    @ApiOperation("查询单个Menu")
    @GetMapping("/selectByUuid/{uuid}")
    public Result<Menu> selectByUuid(@PathVariable("uuid") String uuid) {
        return Result.OK(menuService.getById(uuid));
    }

    @ApiOperation("查询分页Menu")
    @GetMapping("/selectPage")
    public Result<IPage<Menu>> selectPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                          Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper();
        queryWrapper.like(menu.getMenuName() != null,"MENU_NAME",menu.getMenuName());
        IPage<Menu> page = menuService.page(new Page<>(pageNo,pageSize), queryWrapper);
        return Result.OK(page);
    }

    @ApiOperation("获取菜单列表")
    @GetMapping("/getMenuList")
    public Result<List<Menu>> getMenuList(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper();
        queryWrapper.like(menu.getMenuName() != null,"MENU_NAME",menu.getMenuName());
        queryWrapper.eq("IS_DELETE",0);
        queryWrapper.orderByAsc("MENU_SORT");
        List<Menu> menuList = menuService.list(queryWrapper);
        return Result.OK(menuList);
    }

    @ApiOperation("根据用户编号获取用户所有用权限的菜单列表")
    @GetMapping("/getUserMenuList")
    public Result<List<Menu>> getUserMenuList(String userId){
        Result<List<Menu>> result = new Result<>();
        try{
            List<Menu> menuList = menuService.getUserMenuList(userId);
            result.success("获取成功");
            result.setResult(menuList);
        }
        catch(Exception e){
            result.error500("获取失败");
        }
        return result;
    }

    @ApiOperation("获取当前登录人所拥有的菜单权限")
    @GetMapping("/getCurrentUserMenuList")
    public Result<List<Menu>> getCurrentUserMenuList(){
        Result<List<Menu>> result = new Result<>();
        try{
            List<Menu> menuList = menuService.getUserMenuList(LoginUserInfo.getLoginUserId());
            result.success("获取成功");
            result.setResult(menuList);
        }
        catch(Exception e){
            result.error500("获取失败");
        }
        return result;
    }

}
