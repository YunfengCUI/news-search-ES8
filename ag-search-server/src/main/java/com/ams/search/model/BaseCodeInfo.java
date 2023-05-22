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
 * @TableName gg_base_code_info
 */
@TableName(value ="gg_base_code_info")
@Data
public class BaseCodeInfo implements Serializable {
    /**
     * 通用代码表主键ID
     */
    @TableField(value = "code_id")
    private String codeId;

    /**
     * 代码类别ID
     */
    @TableField(value = "data_sort_id")
    private String dataSortId;

    /**
     * 通用代码编码
     */
    @TableField(value = "code_value")
    private String codeValue;

    /**
     * 通用代码名称
     */
    @TableField(value = "code_name")
    private String codeName;

    /**
     * 通用代码描述
     */
    @TableField(value = "code_desc")
    private String codeDesc;

    /**
     * 通用代码状态
     */
    @TableField(value = "code_state")
    private Integer codeState;

    /**
     * 通用代码排序好
     */
    @TableField(value = "code_index")
    private Integer codeIndex;

    /**
     * 删除标识
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 父节点ID
     */
    @TableField(value = "parent_code_id")
    private String parentCodeId;

    /**
     * 创建者id
     */
    @TableField(value = "create_user_id")
    private Integer createUserId;

    /**
     * 创建者姓名
     */
    @TableField(value = "create_user_name")
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_user_id")
    private String updateUserId;

    /**
     * 更新者姓名
     */
    @TableField(value = "update_user_name")
    private String updateUserName;

    /**
     * 更新者时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 拓展字段 树形拓展使用
     */
    @TableField(value = "code_info_ext1")
    private String codeInfoExt1;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}