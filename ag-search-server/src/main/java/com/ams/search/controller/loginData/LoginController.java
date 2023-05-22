package com.ams.search.controller.loginData;

import cn.hutool.core.util.RandomUtil;
import com.ams.search.MD5Util;
import com.ams.search.common.AbstractController;
import com.ams.search.constant.CommonConstant;
import com.ams.search.model.vo.SysLoginModel;
import com.ams.search.service.personnelManagement.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ams.search.model.personnelManagement.User;
import com.ams.search.model.personnelManagement.UserToken;
import com.ams.search.model.vo.Result;
import com.ams.search.service.personnelManagement.UserTokenService;
import com.ams.search.utils.LoginUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.MD5Utils;
import utils.RandImageUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * @Author scott
 * @since 2018-12-17
 */
@RestController
@Api(tags = "用户登录")
@Slf4j
public class LoginController extends AbstractController {
//    @Autowired
    @Resource
    private UserService userService;
    @Resource
    private UserTokenService userTokenService;

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @RequestMapping("/getTest")
    public String getTest() {
        return "123";
    }

    @ApiOperation("登录接口")
    @PostMapping(value = "/sys/login")
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestBody SysLoginModel sysLoginModel) {

        Result<User> result = new Result<User>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();
        //update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题
        //前端密码加密，后端进行密码解密
        //password = AesEncryptUtil.desEncrypt(sysLoginModel.getPassword().replaceAll("%2B", "\\+")).trim();//密码解密
        //update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题

        //update-begin-author:taoyan date:20190828 for:校验验证码
        if (sysLoginModel == null || StringUtils.isEmpty(sysLoginModel.getUsername()) || StringUtils.isEmpty(sysLoginModel.getUsername())) {
            result.error500("用户名或密码不能为空");
            return result;
        }
//        String captcha = sysLoginModel.getCaptcha();
//        if (captcha == null) {
//            result.error500("验证码无效");
//            return result;
//        }
//        String lowerCaseCaptcha = captcha.toLowerCase();
//        String realKey = MD5Util.MD5Encode(lowerCaseCaptcha + sysLoginModel.getCheckKey(), "utf-8");
//
//        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession();
//        if (!realKey.equals(session.getAttribute("verifycode"))) {
//            result.error500("验证码错误");
//            return result;
//        }
        //1.校验用户是否存在
        User user = new User();
        user.setUserLoginName(username);
        user.setIsDelete(CommonConstant.IS_DELETE_0);

        User userObj = userService.getOne(new QueryWrapper<>(user));
        if (userObj == null) {
            result.error500("该用户不存在，请注册");
            return result;
        }

        //2. 校验用户名或密码是否正确
        /*
        * //        若是前端MD5加密后传给后端则修改此段代码
        */

//       if (!MD5Utils.convertMD5(userObj.getUserPassword()).equals(password )) {
       if (!MD5Utils.convertMD5(userObj.getUserPassword()).equals(MD5Utils.string2MD5(password) )) {
            result.error500("密码错误！");
            return result;
        }
        //生成token，并保存到数据库
        UserToken r = userTokenService.createToken(userObj.getUserId());
        userObj.setUserToken(r);
        return Result.OK(r);
    }

//    /**
//     * 退出
//     */
//    @PostMapping("/sys/logout")
//    public Result logout() {
//        userTokenService.logout(getUserId());
//        return Result.ok();
//    }
    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public Result logout(String userId) {
        userTokenService.logout(userId);
        return Result.ok();
    }

    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    public Result list(){
        //只有超级管理员，才能查看所有管理员列表
//        if(getUserId().equals(Constant.SUPER_ADMIN)){
//            params.put("createUserId", getUserId());
//        }
        String i= LoginUserInfo.getLoginUserId();
        User user=LoginUserInfo.getLoginUser();
        return Result.ok(user);
    }

    /**
     * 获取当前登录人信息
     * @return
     */
    @GetMapping("/sys/currentUser")
    public Result currentUser(){
        User user=LoginUserInfo.getLoginUser();
        return Result.ok(user);
    }
    /**
     * 获取当前用户信息
     */

    /**
     * 后台生成图形验证码 ：有效
     *
     * @param response
     * @param key
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage/{key}")
    public Result<String> randomImage(HttpServletRequest request, HttpServletResponse response, @PathVariable String key) {
        Result<String> res = new Result<String>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
//            Subject subject = SecurityUtils.getSubject().getPrincipal();
//            Session session = subject.getSession();
//            session.setAttribute("CAPTCHA", realKey);
//            session.setAttribute("verifyCode", realKey);
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            res.error500("获取验证码出错" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    //解析加密后的密码
    private String decode(String str) {
        String result;
        str = str.substring(0, str.length() - 1);
        StringBuffer sbu = new StringBuffer();
        String[] chars = str.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        result = sbu.toString();
        //解密时间
        String fromDate = result.substring(0, 10);
        //得到时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = formatter.format(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())).substring(0, 10);
        //两个时间作比较
        if (dataStr.equals(fromDate)) {
            //最终密码
            result = result.substring(10);
        } else {
            result = "";
        }
        return result;
    }
}