package com.ams.search.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "docs")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("资料")
public class ESDocs implements Serializable {
    /**
     * 主键
     */
    @JsonProperty(value = "uuid")
    private String uuid;

    /**
     * 来源
     */
    @JsonProperty(value = "source")
    private String source;

    /**
     * 标题
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * 内容
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     * 发布时间
     */
    @JsonProperty(value = "publish_time")
    private Date publishTime;

    /**
     * 类别
     */
    @JsonProperty(value = "type")
    private String type;

    /**
     * 创建人
     */
    @JsonProperty(value = "create_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分页实体")
    private Integer formNo;
    private Integer formSize;
}