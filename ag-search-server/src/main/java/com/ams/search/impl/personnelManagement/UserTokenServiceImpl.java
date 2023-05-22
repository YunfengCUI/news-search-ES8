package com.ams.search.impl.personnelManagement;

import com.ams.search.dao.personnelManagement.UserTokenMapper;
import com.ams.search.oauth2.TokenGenerator;
import com.ams.search.service.personnelManagement.UserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.ams.search.dao.personnelManagement.UserTokenMapper;
import com.ams.search.model.personnelManagement.UserToken;
//import com.icss.dataanalysis.oauth2.TokenGenerator;
//import com.ams.search.service.personnelManagement.UserTokenService;
import org.springframework.stereotype.Service;
import utils.DateUtil;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-25
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Override
    public UserToken createToken(String userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        UserToken tokenEntity = this.getById(userId);
        if(tokenEntity == null){
            tokenEntity = new UserToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            this.save(tokenEntity);
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            this.updateById(tokenEntity);
        }
        tokenEntity.setExpire(EXPIRE);
        return tokenEntity;
    }

    @Override
    public void logout(String userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        //修改token
        UserToken tokenEntity = new UserToken();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        tokenEntity.setUpdateTime(DateUtil.getCurrentTime());
        this.updateById(tokenEntity);
    }
}
