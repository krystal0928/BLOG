package com.krystal.blog.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseModel implements Serializable {

    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}
