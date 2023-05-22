/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.ams.search.service;


import com.ams.search.model.personnelManagement.User;
import com.ams.search.model.personnelManagement.UserToken;

/**
 * shiro相关接口
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ShiroService {

    /**
     * 查询当前用户的token信息
     * @param token　tokenid
     * @return
     */
    UserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId 用户id
     */
    User queryUser(String userId);
}
