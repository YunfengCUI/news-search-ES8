package com.ams.search.model.personnelManagement;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GG_USER")
public class User {

    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    @TableId("USER_ID")
    @ApiModelProperty(value = "用户主键")
    private String userId;
    /**
     * 用户关联菜单主键
     */
    @TableField("USER_MENU_REL_ID")
    @ApiModelProperty(value = "用户关联菜单主键")
    private String userMenuRelId;
    /**
     * 用户工号
     */
    @TableField("USER_NUMBER")
    @ApiModelProperty(value = "部门及职务")
    @Excel(name = "部门及职务")
    private String userNumber;
    /**
     * 用户姓名
     */
    @TableField("USER_NAME")
    @ApiModelProperty(value = "用户姓名")
    @Excel(name = "用户姓名")
    private String userName;
    /**
     * 登录名
     */
    @TableField("USER_LOGIN_NAME")
    @ApiModelProperty(value = "登录名")
    @Excel(name = "用户账号")
    private String userLoginName;
    /**
     * 密码
     */
    @TableField("USER_PASSWORD")
    @ApiModelProperty(value = "密码")
    private String userPassword;
    /**
     * 手机号
     */
    @TableField("USER_PHONE")
    @ApiModelProperty(value = "手机号")
    @Excel(name = "手机号")
    private String userPhone;
    /**
     * 性别 :1、男、2、女   单选按钮选择
     */
    @TableField("USER_SEX")
    @ApiModelProperty(value = "性别 :1、男、2、女   单选按钮选择")
    @Excel(name = "性别",replace = {"男_1", "女_2"} )
    private BigDecimal userSex;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 是否删除:0、否  1、是
     */
    @TableField("IS_DELETE")
    @ApiModelProperty(value = "是否删除:0、否  1、是")
    @TableLogic
    private Integer isDelete;

    private transient UserToken userToken;

    /**
     * 菜单id
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "菜单id")
    private String menuIds;
}
