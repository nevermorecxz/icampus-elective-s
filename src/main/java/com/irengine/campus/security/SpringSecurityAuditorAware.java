package com.irengine.campus.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.irengine.campus.config.Constants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUsername();
        return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
    }
}
