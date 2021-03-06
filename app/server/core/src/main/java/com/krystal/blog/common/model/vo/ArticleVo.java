package com.krystal.blog.common.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
// 将父类中的属性也算到 ToString 中
@ToString(callSuper = true)
// 子类对象之间进行比较的时候,equals和hashcode将父类对象属性算进去，根据父类和子类共同的属性去比较。
@EqualsAndHashCode(callSuper = true)
public class ArticleVo extends Article {

    private String typeName;
    private String userName;
    private Long userId;
    private String userImg;
    private Integer collectCount;
    private Integer commentCount;
    private Integer likeCount;
    private Integer liked;
    private Integer collected;
}
