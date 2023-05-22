package com.ams.search.model.personnelManagement;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)

@Accessors(chain = true)
@TableName("GG_ROLE")
public class Role {

    private static final long serialVersionUID = 1L;

    /**
     * 角色主键
     */
    @TableId("ROLE_ID")
    @ApiModelProperty(value = "角色主键")
    private String roleId;
    /**
     * 角色编码
     */
    @TableField("ROLE_NUMBER")
    @ApiModelProperty(value = "角色编码")
    private String roleNumber;
    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /**
     * 创建人名称
     */
    @TableField(value = "CREATE_USER_NAME", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人名称")
    private String createUserName;
    /**
     * 创建人编号
     */
    @TableField("CREATE_USER_ID")
    @ApiModelProperty(value = "创建人编号")
    private String createUserId;
    /**
     * 修改人名称
     */
    @TableField(value = "UPDATE_USER_NAME", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改人名称")
    private String updateUserName;
    /**
     * 修改人编号
     */
    @TableField("UPDATE_USER_ID")
    @ApiModelProperty(value = "修改人编号")
    private String updateUserId;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 是否删除：0、否  1、是
     */
    @TableField("IS_DELETE")
    @ApiModelProperty(value = "是否删除：0、否  1、是")
    @TableLogic
    private Integer isDelete;
    /**
     * 角色描述
     */
    @TableField("ROLE_DES")
    @ApiModelProperty(value = "角色描述")
    private String roleDes;
    /**
     * 角色排序
     */
    @TableField("ROLE_SORT")
    @ApiModelProperty(value = "角色排序")
    private Integer roleSort;


}
