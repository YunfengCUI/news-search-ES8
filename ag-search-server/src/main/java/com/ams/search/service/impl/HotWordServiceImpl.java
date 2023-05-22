package com.ams.search.service.impl;


import com.ams.search.model.WordHistory;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ams.search.model.HotWord;
import com.ams.search.service.HotWordService;
import com.ams.search.dao.mapper.HotWordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author CYF02
* @description 针对表【gg_hot_word】的数据库操作Service实现
* @createDate 2022-10-27 11:16:01
*/
@Service
public class HotWordServiceImpl extends ServiceImpl<HotWordMapper, HotWord>
    implements HotWordService{
@Resource
HotWordMapper mapper;

    @Override
    public Map<String, Object> getHotWord() {
        return mapper.getTop();
    }
}




