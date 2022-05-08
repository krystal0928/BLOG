package com.krystal.blog.common.model.vo;

import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
// 将父类中的属性也算到 ToString 中
@ToString(callSuper = true)
// 子类对象之间进行比较的时候,equals和hashcode将父类对象属性算进去，根据父类和子类共同的属性去比较。
@EqualsAndHashCode(callSuper = true)
public class UserVo extends User {

    private Integer focusCount;
    private Integer likeCount;
    private Integer fansCount;
    private Integer collectCount;
    private Integer focused;
}
