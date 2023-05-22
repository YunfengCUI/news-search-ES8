package com.ams.search.dao.mapper;

import com.ams.search.model.HotWord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author CYF02
* @description 针对表【gg_hot_word】的数据库操作Mapper
* @createDate 2022-10-27 11:16:01
* @Entity com.ams.search.model.HotWord
*/
public interface HotWordMapper extends BaseMapper<HotWord> {

    @MapKey("words")
    Map<String,Object> getTop();
}




