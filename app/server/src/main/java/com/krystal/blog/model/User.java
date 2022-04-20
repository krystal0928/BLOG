package com.krystal.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Builder
@Data
// 将父类中的属性也算到 ToString 中
@ToString(callSuper = true)
// 子类对象之间进行比较的时候,equals和hashcode将父类对象属性算进去，根据父类和子类共同的属性去比较。
@EqualsAndHashCode(callSuper = true)
//指定表名
@TableName(value = "user")
public class User extends BaseModel {

    @TableId(type = IdType.INPUT)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String secret;
    private String email;
    private Integer status;

}
