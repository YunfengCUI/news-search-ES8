package com.ams.search.model.personnelManagement;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GG_MENU")
public class Menu {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单主键
     */
    @TableId("MENU_ID")
    @ApiModelProperty(value = "菜单主键")
    private String menuId;
    /**
     * 用户关联菜单主键
     */
    @TableField("USER_MENU_REL_ID")
    @ApiModelProperty(value = "用户关联菜单主键")
    private String userMenuRelId;
    /**
     * 菜单名称
     */
    @TableField("MENU_NAME")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    /**
     * 菜单URL
     */
    @TableField("MENU_URL")
    @ApiModelProperty(value = "菜单URL")
    private String menuUrl;
    /**
     * 父级菜单编号
     */
    @TableField("MENU_PARENT_ID")
    @ApiModelProperty(value = "父级菜单编号")
    private String menuParentId;
    /**
     * 菜单排序号
     */
    @TableField("MENU_SORT")
    @ApiModelProperty(value = "菜单排序号")
    private BigDecimal menuSort;
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
    private Integer isDelete;

    /**
     * 图标路径
     */
    @TableField("ICON")
    @ApiModelProperty(value = "菜单图标路径")
    private String icon;

}
