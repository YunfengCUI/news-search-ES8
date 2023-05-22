package com.ams.search.dao.personnelManagement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ams.search.model.personnelManagement.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User queryUserByIdNotIsDelete(@Param("userId") String userId);

    void updateUserByIdNotIsDelete(User user);
}
