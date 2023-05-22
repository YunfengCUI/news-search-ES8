package com.ams.search.service.personnelManagement;

import com.ams.search.model.personnelManagement.UserToken;
import com.baomidou.mybatisplus.extension.service.IService;
//import com.ams.search.model.personnelManagement.UserToken;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-25
 */
public interface UserTokenService extends IService<UserToken> {

    UserToken createToken(String userId);

    void logout(String userId);
}
