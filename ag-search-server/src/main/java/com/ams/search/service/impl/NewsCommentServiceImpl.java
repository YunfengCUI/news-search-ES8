package com.ams.search.service.impl;

import com.ams.search.dao.mapper.DocsMapper;
import com.ams.search.model.Docs;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ams.search.model.NewsComment;
import com.ams.search.service.NewsCommentService;
import com.ams.search.dao.mapper.NewsCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author CYF02
* @description 针对表【news_comment】的数据库操作Service实现
* @createDate 2022-10-26 11:06:35
*/
@Service
public class NewsCommentServiceImpl extends ServiceImpl<NewsCommentMapper, NewsComment>
    implements NewsCommentService{
@Resource
NewsCommentMapper mapper;
@Resource
    DocsMapper docsMapper;
    @Override
    public List<NewsComment> getTopComment(String obj_uuid, int start,int end) {
        return mapper.getTopComment(obj_uuid,start,end);
    }

    @Override
    public List<NewsComment> getTop(Page<NewsComment> page,QueryWrapper<NewsComment> wrapper) {
        return mapper.getTop(page,wrapper);
    }

    @Override
    public List<Map<String, Object>> getUserComment(Page<Object> page, QueryWrapper<Object> wrapper) {
        return docsMapper.getUserComment(page,wrapper);
    }


}




