package com.ams.search.service.impl;

import com.ams.search.model.WordHistory;
import com.ams.search.dao.mapper.WordHistoryMapper;
import com.ams.search.service.WordHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author CYF02
* @description 针对表【word_history】的数据库操作Service实现
* @createDate 2022-10-15 18:20:06
*/
@Service
public class WordHistoryServiceImpl extends ServiceImpl<WordHistoryMapper, WordHistory>
    implements WordHistoryService {
    @Resource
    WordHistoryMapper mapper;
    @Override
    public List<WordHistory> getTopWord(String user,int top) {
        return mapper.get_lately_top(user,top);
    }
}




