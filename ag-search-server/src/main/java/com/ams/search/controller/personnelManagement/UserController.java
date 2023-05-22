package com.ams.search.controller.personnelManagement;

import com.ams.search.model.personnelManagement.UserMenuRel;
import com.ams.search.model.vo.UserVo;
import com.ams.search.service.personnelManagement.UserMenuRelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.icss.dataanalysis.MD5Utils;
import com.ams.search.MD5Util;
import com.ams.search.model.personnelManagement.User;
//import com.ams.search.model.personnelManagement.UserMenuRel;
import com.ams.search.model.vo.Result;
//import com.ams.search.model.vo.UserVo;
//import com.ams.search.service.personnelManagement.UserMenuRelService;
import com.ams.search.service.personnelManagement.UserService;
import com.ams.search.utils.LoginUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.DateUtil;
import utils.MD5Utils;
import utils.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */

@Api(tags = "服务")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMenuRelService userMenuRelService;

    @ApiOperation("创建单个User")
    @PostMapping("/add")
    public Result<User> insert(@RequestBody User user) {
        Result<User> result=new Result<User>();
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq(StringUtil.isNotEmpty(user.getUserLoginName()),"USER_LOGIN_NAME",user.getUserLoginName());
        queryWrapper.eq("IS_DELETE",0);
        List<User> userList = userService.list(queryWrapper);
        if(userList.size()>0){//用户名重复
            result.setSuccess(false);
            result.setMessage("添加的用户名称："+user.getUserLoginName()+"已存在，请重新添加");
            return result;
        } else{
            user.setUserId(StringUtil.getGuuid());
//            user.setCreateUserId(LoginUserInfo.getLoginUserId());
//            user.setCreateUserName(LoginUserInfo.getLoginUserName());
//            user.setUpdateUserId(LoginUserInfo.getLoginUserId());
//            user.setUpdateUserName(LoginUserInfo.getLoginUserName());
            user.setCreateUserId(user.getUpdateUserId());
            user.setCreateUserName(user.getCreateUserName());
            user.setUpdateUserId(user.getUpdateUserName());
            user.setUpdateUserName(user.getUpdateUserName());
            user.setCreateTime(DateUtil.getCurrentTime());
            user.setUpdateTime(DateUtil.getCurrentTime());
            //md5加密，先转md5码值再进行一次加密。
            user.setUserPassword(MD5Utils.convertMD5(MD5Utils.string2MD5(user.getUserPassword())));
            user.setIsDelete(0);
            Boolean save = userService.save(user);

            if(StringUtil.isNotEmpty(user.getMenuIds())){
                String[] split = user.getMenuIds().split(",");
                List<UserMenuRel> userMenuRelList = new ArrayList();
                for (String menuId : split ){
                    UserMenuRel userMenuRel = new UserMenuRel();
                    userMenuRel.setUserMenuRelId(StringUtil.getGuuid());
                    userMenuRel.setMenuId(menuId);
                    userMenuRel.setUserId(user.getUserId());
                    userMenuRelList.add(userMenuRel);
                }
                userMenuRelService.saveBatch(userMenuRelList);
            }
        }
        return result.success("新增成功");
    }


    @ApiOperation("删除单个User")
    @PostMapping("/delete/{uuid}")
    public Result<Boolean> deleteById(@PathVariable("uuid") String uuid) {
        return Result.OK(userService.removeById(uuid));
    }


    @ApiOperation("编辑单个User")
    @PostMapping("/updateByUuId")
    public Result<Boolean> updateByUuId(@RequestBody User user) {
        user.setUpdateTime(DateUtil.getCurrentTime());
//        user.setUpdateUserId(LoginUserInfo.getLoginUserId());
//        user.setUpdateUserName(LoginUserInfo.getLoginUserName());

//        //md5加密，先转md5码值再进行一次加密。
//        if(StringUtil.isNotEmpty(user.getUserPassword())){
//            user.setUserPassword(MD5Utils.convertMD5(MD5Utils.string2MD5(user.getUserPassword())));
//        }else {//为空的时候传null值这样就可以不修改密码
//            user.setUserPassword(null);
//        }
//        修改用户授权，先删除用户关联菜单，再进行修改
        QueryWrapper<UserMenuRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID",user.getUserId());
        userMenuRelService.remove(queryWrapper);
        if(StringUtil.isNotEmpty(user.getMenuIds())){
            String[] split = user.getMenuIds().split(",");
            List<UserMenuRel> userMenuRelList = new ArrayList();
            for (String menuId : split ){
                UserMenuRel userMenuRel = new UserMenuRel();
                userMenuRel.setUserMenuRelId(StringUtil.getGuuid());
                userMenuRel.setMenuId(menuId);
                userMenuRel.setUserId(user.getUserId());
                userMenuRelList.add(userMenuRel);
            }
            userMenuRelService.saveBatch(userMenuRelList);
        }
        return Result.OK(userService.updateById(user));
    }

    @ApiOperation("查询单个User")
    @GetMapping("/selectByUuid/{uuid}")
    public Result<User> selectByUuid(@PathVariable("uuid") String uuid) {
        User user = userService.getById(uuid);
        QueryWrapper<UserMenuRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID",user.getUserId());
        List<UserMenuRel> list = userMenuRelService.list(queryWrapper);
        List<String> idList = new ArrayList<>();
        for (UserMenuRel userMenuRel :list) {
            idList.add(userMenuRel.getMenuId());
        }
        String menuIds = StringUtils.join(idList,",");
        user.setMenuIds(menuIds);
        return Result.OK(user);
    }

    @ApiOperation("查询分页User")
    @GetMapping("/selectPage")
    public Result<IPage<User>> selectPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                          User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtil.isNotEmpty(user.getUserLoginName()),"USER_LOGIN_NAME",user.getUserLoginName());
        queryWrapper.like(StringUtil.isNotEmpty(user.getUserName()),"USER_NAME",user.getUserName());
        queryWrapper.like(user.getUserSex() != null,"USER_SEX",user.getUserSex());
        queryWrapper.like(StringUtil.isNotEmpty(user.getUserNumber()),"USER_NUMBER",user.getUserNumber());
        queryWrapper.like(StringUtil.isNotEmpty(user.getUserPhone()),"USER_PHONE",user.getUserPhone());
        queryWrapper.ne("USER_LOGIN_NAME","admin");
        queryWrapper.orderByDesc("CREATE_TIME");
        IPage<User> page = userService.page(new Page<>(pageNo,pageSize), queryWrapper);
        return Result.OK(page);
    }

    @ApiOperation("获取当前系统内所有用户")
    @GetMapping("/getUser")
    public Result<List<User>> getUser() {
        Result<List<User>> result = new Result<>();
        try{
            QueryWrapper<User> queryWrapper = new QueryWrapper();
            queryWrapper.eq("IS_DELETE",0);
            List<User> userList = userService.list(queryWrapper);
            result.setResult(userList);
            return result.success("获取成功");
        }
        catch (Exception e){
            return result.error500("获取失败");
        }
    }

    @ApiOperation("下载人员管理模板")
    @GetMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) throws IOException {
//        String relativePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String fileAbsolutePath = relativePath + File.separator + "excel_templates" + File.separator + "userTemplate.xlsx";
//        InputStream inStream = new FileInputStream(fileAbsolutePath);// 文件的存放路径
        InputStream inStream =this.getClass().getClassLoader().getResourceAsStream("excel_templates/userTemplate.xlsx" );
        String displayName = "人员管理导入模版.xls";
        // 设置输出的格式
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(displayName, "utf-8"));
        response.setHeader("FileName", URLEncoder.encode(displayName, "utf-8"));
        response.setHeader("Access-Control-Expose-Headers", "FileName");
        // 循环取出流中的数据
        byte[] b = new byte[4096];
        int len;
        OutputStream out = response.getOutputStream();
        try {
            while ((len = inStream.read(b)) > 0)
                out.write(b, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return;
    }

   /**
   *@Description 人员信息数据导入接口
   *@Param:[file] 文件
   *@return:com.ams.search.model.vo.Result<com.ams.search.model.personnelManagement.User>
   *@Author:weiyujie
   *@Date:2022/3/15
   */
    @ApiOperation("人员信息数据导入")
    @PostMapping("/dataImport")
    public Result<User> dataImport(@RequestParam("file") MultipartFile file) {
        Result<User> userResult = new Result<User>();
        try {
            userResult = userService.importData(file);
        } catch (Exception e) {
            userResult.setSuccess(false);
            userResult.setMessage(e.getMessage());
            e.printStackTrace();
            return userResult;
        }
        return userResult;
}


    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    public Result<User> updatePassword(@RequestBody UserVo userParam) {
        Result<User> result=new Result<User>();
        User user = userService.getById(userParam.getUserId());
        if (!MD5Utils.convertMD5(user.getUserPassword()).equals(MD5Utils.string2MD5(userParam.getUserPassword()))) {
            result.error500("输入的原始密码错误，请输入正确的密码");
            return result;
        }
        if(StringUtil.isNotEmpty(userParam.getNewPassword())){
            //md5加密，先转md5码值再进行一次加密。
            user.setUserPassword(MD5Utils.convertMD5(MD5Utils.string2MD5(userParam.getNewPassword())));//设置新密码
        }
        user.setUpdateUserId(LoginUserInfo.getLoginUserId());
        user.setUpdateUserName(LoginUserInfo.getLoginUserName());
        user.setUpdateTime(DateUtil.getCurrentTime());
        Boolean updateById = userService.updateById(user);
        result.setSuccess(updateById);
        return result.success("修改成功");
    }

    /**
     * 批量删除用户
     * @param userIds 用户id拼接的数组
     * @return Result<Boolean> 返回是否删除成功
     */
    @ApiOperation("批量删除用户")
    @DeleteMapping("/deleteByIds")
    public Result<Boolean> deleteByIds(@RequestBody String[] userIds) {
        return Result.OK(userService.removeByIds(Arrays.asList(userIds)));
    }
}
