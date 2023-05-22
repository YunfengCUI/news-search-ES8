package com.ams.search.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ams.search.model.WordHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author CYF02
* @description 针对表【word_history】的数据库操作Mapper
* @createDate 2022-10-15 18:20:06
* @Entity demoes7.entity.WordHistory
*/
public interface WordHistoryMapper extends BaseMapper<WordHistory> {
    /**
     * 查询某个用户的最近的搜索记录
     * @param user 创建者
     * @param top   返回个数
     * @return
     */
    List<WordHistory> get_lately_top(@Param("user") String user,@Param("top") int top);
}




