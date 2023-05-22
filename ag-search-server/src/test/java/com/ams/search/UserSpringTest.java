package com.ams.search;

import com.ams.search.model.NewsComment;
import com.ams.search.model.personnelManagement.User;
import com.ams.search.service.ESDocsService;
import com.ams.search.service.HotWordService;
import com.ams.search.service.NewsCommentService;
import com.ams.search.service.personnelManagement.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserSpringTest {
    @Resource
    UserService service;
    @Test
    void useradd(){
//        添加用户
        User user = new User();
        user.setUserId("cui1");
        user.setUserName("崔云峰");
        user.setUserLoginName("cyf");
        user.setUserPassword("11111");
        user.setUserPhone("17742162921");
        user.setUserSex(new BigDecimal(0));
        user.setCreateUserName("崔云峰");
        user.setCreateUserId("2");
        user.setCreateTime(new Date(System.currentTimeMillis()));
        user.setIsDelete(0);


    }
    @Resource
    HotWordService hotWordService;
    @Test
    /**
     * 获取热词top10
     */
    void testmapp(){
hotWordService.getHotWord();
    }
    @Resource
    ESDocsService esDocsService;
    @Test
    void EsgetById() throws IOException {
        String indedx="docs";
        String uuid="1";
    }
    @Resource
    NewsCommentService newsCommentService;
    @Test
    void newComment(){
        Page<NewsComment> page = new Page<NewsComment>(0,4);
        QueryWrapper<NewsComment> queryWrapper = new QueryWrapper<NewsComment>();
        queryWrapper.eq("obj_uuid","8");
        queryWrapper.orderByDesc("created_time");
        List<NewsComment> list= newsCommentService.getTop(page,queryWrapper);
        for (NewsComment n:list
             ) {

        }
    }
}
