package com.krystal.blog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseModel implements Serializable {

    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}
