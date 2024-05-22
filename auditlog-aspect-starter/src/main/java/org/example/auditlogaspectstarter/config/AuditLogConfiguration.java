package org.example.auditlogaspectstarter.config;

import org.example.auditlogaspectstarter.AuditlogAspectStarterApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(AuditlogAspectStarterApplication.class)
public class AuditLogConfiguration {
}
