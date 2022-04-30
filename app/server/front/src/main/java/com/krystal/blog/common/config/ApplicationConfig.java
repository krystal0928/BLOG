package com.krystal.blog.common.config;

import com.krystal.blog.common.beans.ApplicationTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {
    // 文件存储地址
    @Value("${application.baseDirectory}")
    private String baseDirectory;

    // 邮件配置
    @Value("${application.email.host}")
    private String emailHost;
    @Value("${application.email.port}")
    private Integer emailPort;
    @Value("${application.email.from}")
    private String emailFrom;
    @Value("${application.email.user}")
    private String emailUser;
    @Value("${application.email.pass}")
    private String emailPass;

    @Bean
    public ApplicationTemplate applicationTemplate() {
        return ApplicationTemplate.builder()
                .baseDirectory(baseDirectory)
                .emailHost(emailHost)
                .emailPort(emailPort)
                .emailFrom(emailFrom)
                .emailUser(emailUser)
                .emailPass(emailPass)
                .build();
    }
}