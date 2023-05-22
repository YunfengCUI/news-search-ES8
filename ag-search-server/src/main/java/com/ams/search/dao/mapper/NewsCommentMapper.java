package com.ams.search.dao.mapper;

import com.ams.search.model.NewsComment;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.toolkit.Constants;  // Constants 引用
import java.util.List;
import java.util.Map;

/**
* @author CYF02
* @description 针对表【news_comment】的数据库操作Mapper
* @createDate 2022-10-26 11:06:35
* @Entity com.ams.search.model.NewsComment
*/
public interface NewsCommentMapper extends BaseMapper<NewsComment> {

    List<NewsComment> getTopComment(@Param("obj_uuid") String obj_uuid,@Param("start") int start,@Param("end") int end);
    List<NewsComment> getTop(Page<NewsComment> page,@Param(Constants.WRAPPER) QueryWrapper<NewsComment> queryWrapper);

}




