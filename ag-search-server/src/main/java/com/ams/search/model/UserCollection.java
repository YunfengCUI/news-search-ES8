package com.ams.search.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName gg_user_collection
 */
@TableName(value ="gg_user_collection")
@Data
public class UserCollection implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "collection_id", type = IdType.AUTO)
    private Integer collectionId;

    /**
     * 文章id
     */
    @TableField(value = "arrricle_id")
    private String arrricleId;

    /**
     * 用户id
     */
    @TableField(value = "user_uid")
    private String userUid;

    /**
     * 文章标题
     */
    @TableField(value = "arricle_title")
    private String arricleTitle;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}