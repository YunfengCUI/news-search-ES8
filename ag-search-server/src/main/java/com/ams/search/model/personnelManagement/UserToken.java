package com.ams.search.model.personnelManagement;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GG_USER_TOKEN")
public class UserToken {

    private static final long serialVersionUID = 1L;

    @TableId("USER_ID")
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @TableField("TOKEN")
    @ApiModelProperty(value = "TOKEN信息")
    private String token;
    @TableField("EXPIRE_TIME")
    @ApiModelProperty(value = "失效时间")
    private Date expireTime;
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "最后更新时间")
    private Date updateTime;

    private transient Integer expire;


}
