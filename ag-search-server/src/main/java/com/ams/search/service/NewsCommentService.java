package com.ams.search.service;

import com.ams.search.model.Docs;
import com.ams.search.model.NewsComment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author CYF02
* @description 针对表【news_comment】的数据库操作Service
* @createDate 2022-10-26 11:06:35
*/
public interface NewsCommentService extends IService<NewsComment> {

    /**
     *     最新评论
     */
    List<NewsComment> getTopComment(String obj_uuid, int start,int end);

    List<NewsComment> getTop(Page<NewsComment> page,QueryWrapper<NewsComment> wrapper);

    List<Map<String, Object>> getUserComment(Page<Object> page, QueryWrapper<Object> wrapper);
}
