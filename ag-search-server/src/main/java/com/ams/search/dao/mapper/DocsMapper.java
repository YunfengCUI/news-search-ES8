package com.ams.search.dao.mapper;


import com.ams.search.model.Docs;
import com.ams.search.model.NewsComment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
* @author CYF02
* @description 针对表【】的数据库操作Mapper
* @createDate 2022-10-14 15:44:13
* @Entity search.entity.Docs
*/
public interface DocsMapper extends BaseMapper<Docs> {


    /**
     * 获取用户的评论
     * 个人中心展示
     */
    @MapKey("title")
    List<Map<String,Object>>  getUserComment(Page<Object> page, @Param(Constants.WRAPPER) QueryWrapper<Object> queryWrapper);

}




