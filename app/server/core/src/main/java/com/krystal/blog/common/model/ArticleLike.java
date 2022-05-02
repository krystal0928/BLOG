package com.krystal.blog.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
// 将父类中的属性也算到 ToString 中
@ToString(callSuper = true)
// 子类对象之间进行比较的时候,equals和hashcode将父类对象属性算进去，根据父类和子类共同的属性去比较。
@EqualsAndHashCode(callSuper = true)
//指定表名
@TableName(value = "article_like")
public class ArticleLike extends BaseModel {
    private Long id;
    private Long userId;
    private Long articleId;
}
