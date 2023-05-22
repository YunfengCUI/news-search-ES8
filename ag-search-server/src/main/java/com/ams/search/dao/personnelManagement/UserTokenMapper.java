package com.ams.search.dao.personnelManagement;

import com.ams.search.model.personnelManagement.UserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.ams.search.model.personnelManagement.UserToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-25
 */
@Repository
public interface UserTokenMapper extends BaseMapper<UserToken> {

    UserToken queryByToken(@Param("token") String token);
}
