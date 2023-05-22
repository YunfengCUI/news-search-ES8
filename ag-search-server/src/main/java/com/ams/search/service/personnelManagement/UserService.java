package com.ams.search.service.personnelManagement;

import com.ams.search.model.personnelManagement.User;
import com.ams.search.model.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;
//import com.ams.search.model.personnelManagement.User;
//import com.ams.search.model.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
public interface UserService extends IService<User> {

   /**
   *@Description 人员管理导入
   *@Param:[file] 文件
   *@return:com.ams.search.model.vo.Result<com.ams.search.model.personnelManagement.User>
   *@Author:weiyujie
   *@Date:2022/3/15
   */
    Result<User> importData(MultipartFile file) throws Exception;

    User queryUserByIdNotIsDelete(String userId);

    void updateUserByIdNotIsDelete(User user);
}
