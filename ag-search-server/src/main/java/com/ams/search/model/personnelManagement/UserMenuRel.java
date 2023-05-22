package com.ams.search.model.personnelManagement;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户菜单关联表
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GG_USER_MENU_REL")
public class UserMenuRel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户关联菜单主键
     */
    @TableId("USER_MENU_REL_ID")
    @ApiModelProperty(value = "用户关联菜单主键")
    private String userMenuRelId;
    /**
     * 用户主键
     */
    @TableField("USER_ID")
    @ApiModelProperty(value = "用户主键")
    private String userId;
    /**
     * 菜单主键
     */
    @TableField("MENU_ID")
    @ApiModelProperty(value = "菜单主键")
    private String menuId;


}
