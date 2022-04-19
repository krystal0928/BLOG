package com.krystal.blog.common;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnowFlakeTemplate {
    private Long workerId;
    private Long dataCenterId;

    public String getIdStr() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
    }

    public Long getIdLong() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextId();
    }
}
