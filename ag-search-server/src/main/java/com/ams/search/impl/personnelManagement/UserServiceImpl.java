package com.ams.search.impl.personnelManagement;

import com.ams.search.dao.personnelManagement.UserMapper;
import com.ams.search.utils.EasyPoiUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.icss.dataanalysis.MD5Utils;
import com.ams.search.MD5Util;
import com.ams.search.constant.CommonConstant;
import com.ams.search.dao.personnelManagement.MenuMapper;
import com.ams.search.model.personnelManagement.User;
import com.ams.search.model.vo.Result;
import com.ams.search.service.personnelManagement.UserService;
//import com.ams.search.utils.EasyPoiUtils;
import com.ams.search.utils.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.DateUtil;
import utils.MD5Utils;
import utils.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 菜单dao接口
     */
    @Resource
    private UserMapper userMapper;

    /**
    *@Description 人员信息导入
    *@Param:[file] 文件
    *@return:com.ams.search.model.vo.Result<com.ams.search.model.personnelManagement.User>
    *@Author:weiyujie
    *@Date:2022/3/15
    */
    @Override
    public Result<User> importData(MultipartFile file) throws Exception {
        Result<User> resultMsg = new Result<User>();
        List<User> userList = new ArrayList<>();
        //获取Execl数据转成List<User>对象
        List<User> resultList = EasyPoiUtils.importExcel(file, 0, 1, User.class);
        //提示信息
        List<String> msgList = new ArrayList<>();

        List<String> userNameList=resultList.stream().map(User::getUserLoginName).collect(Collectors.toList());
        List<String> repeatList = userNameList.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey).collect(Collectors.toList());
        for (String s : repeatList) {
            msgList.add( "execl中用户账号存在重复数据："+s+"用户账号重复");
        }

        if (msgList.size() > 0) {//存在提示信息不插入数据，提示前台哪个数据有问题
            resultMsg.setSuccess(false);
            resultMsg.setMessage(msgList.toString());
            return resultMsg;
        }

        for (int i = 0; i < resultList.size(); i++) {
            User user = resultList.get(i);
            QueryWrapper<User> queryWrapper = new QueryWrapper();
            queryWrapper.eq(StringUtil.isNotEmpty(user.getUserLoginName())," USER_LOGIN_NAME", user.getUserLoginName());
            queryWrapper.eq("IS_DELETE", 0);
            List<User> queryList = list(queryWrapper);
            if (queryList.size() > 0) {//角色不唯一
                //用户账号重复
                msgList.add( user.getUserLoginName()+"用户账号已存在");
                continue;
            }
            user.setUserId(StringUtil.getGuuid());
            user.setCreateUserId(LoginUserInfo.getLoginUserId());
            user.setCreateUserName(LoginUserInfo.getLoginUserName());
            user.setUpdateUserId(LoginUserInfo.getLoginUserId());
            user.setUpdateUserName(LoginUserInfo.getLoginUserName());
            user.setCreateTime(DateUtil.getCurrentTime());
            user.setUpdateTime(DateUtil.getCurrentTime());
            user.setUserPassword(MD5Utils.convertMD5(MD5Utils.string2MD5(CommonConstant.USER_PASSWORD)));
            user.setIsDelete(0);
            userList.add(user);
        }

        if (msgList.size() > 0) {//存在提示信息不插入数据，提示前台哪个数据有问题
            resultMsg.setSuccess(false);
            resultMsg.setMessage(msgList.toString());
        } else {
            boolean result = saveBatch(userList);
            if (result) {
                resultMsg.setSuccess(true);
                resultMsg.setMessage("导入成功！导入用户密码默认是"+CommonConstant.USER_PASSWORD+"，请登陆账号根据提示修改密码！");
                resultMsg.setCode(200);
            }
        }
        return resultMsg;
    }

    @Override
    public User queryUserByIdNotIsDelete(String userId) {
        return userMapper.queryUserByIdNotIsDelete(userId);
    }

    @Override
    public void updateUserByIdNotIsDelete(User user) {
        userMapper.updateUserByIdNotIsDelete(user);
    }
}
