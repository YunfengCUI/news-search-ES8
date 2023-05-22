package com.ams.search.service;

import com.ams.search.dao.mapper.WordHistoryMapper;
import com.ams.search.model.WordHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.annotation.Resource;
import java.util.List;

/**
* @author CYF02
* @description 针对表【word_history】的数据库操作Service
* @createDate 2022-10-15 18:20:06
*/
public interface WordHistoryService extends IService<WordHistory> {

//    倒序查询记录 根据创建者
    List<WordHistory> getTopWord(String user,int top);
}
