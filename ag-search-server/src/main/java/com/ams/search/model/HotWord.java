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
 * @TableName gg_hot_word
 */
@TableName(value ="gg_hot_word")
@Data
public class HotWord implements Serializable {
    /**
     * 
     */
    @TableField(value = "words")
    private String words;

    /**
     * 
     */
    @TableField(value = "search_time")
    private Date searchTime;

    /**
     * 
     */
    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}