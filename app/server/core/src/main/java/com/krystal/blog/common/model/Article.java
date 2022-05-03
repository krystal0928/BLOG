package com.krystal.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
// 将父类中的属性也算到 ToString 中
@ToString(callSuper = true)
// 子类对象之间进行比较的时候,equals和hashcode将父类对象属性算进去，根据父类和子类共同的属性去比较。
@EqualsAndHashCode(callSuper = true)
//指定表名
@TableName(value = "article")
public class Article extends BaseModel {

    @TableId(type = IdType.INPUT)
    private Long id;
    private Long userId;
    private Integer status;
    private String title;
    private String description;
    private String pubDescription;
    private byte[] content;
    private String filepath;
    private String coverImg;
    private Integer permission;

    @TableField(exist = false, typeHandler = org.apache.ibatis.type.BlobTypeHandler.class)
    private String contentStr;
}
