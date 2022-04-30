package com.krystal.blog.common.beans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationTemplate {
    private String baseDirectory;
    private String emailHost;
    private Integer emailPort;
    private String emailFrom;
    private String emailUser;
    private String emailPass;
}
