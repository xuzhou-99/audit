package com.qingyan.audit.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qingyan.audit.aspect.AuditLogAspect;

/**
 * AuditLogConfiguration
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/3/4 14:45
 */
@Configuration
public class AuditLogConfiguration {

    @Bean
    public AuditLogAspect auditLogAspect(){
        return new AuditLogAspect();
    }

}
