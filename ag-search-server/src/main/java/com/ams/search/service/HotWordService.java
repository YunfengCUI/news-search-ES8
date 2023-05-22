package com.ams.search.service;


import com.ams.search.model.HotWord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author CYF02
* @description 针对表【gg_hot_word】的数据库操作Service
* @createDate 2022-10-27 11:16:01
*/
public interface HotWordService extends IService<HotWord> {
//    获取热词
    Map<String, Object> getHotWord();

}
