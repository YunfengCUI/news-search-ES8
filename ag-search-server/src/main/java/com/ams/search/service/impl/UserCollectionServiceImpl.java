package com.ams.search.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ams.search.model.UserCollection;
import com.ams.search.service.UserCollectionService;
import com.ams.search.dao.mapper.UserCollectionMapper;
import org.springframework.stereotype.Service;

/**
* @author CYF02
* @description 针对表【gg_user_collection】的数据库操作Service实现
* @createDate 2022-11-02 15:56:26
*/
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection>
    implements UserCollectionService{

}




