package com.foodgrid.common.security.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    private String userId;
    private String token;
    private String username;
}
