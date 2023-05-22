package com.ams.search.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName news_comment
 */
@TableName(value ="news_comment")
@Data
public class NewsComment implements Serializable {
    /**
     * 评论主键
     */
    @TableId(value = "comment_uuid")
    private String commentUuid;

    /**
     * 评论主体UUID
     */
    @TableField(value = "obj_uuid")
    private String objUuid;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 置顶级次
     */
    @TableField(value = "top_level")
    private Integer topLevel;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}