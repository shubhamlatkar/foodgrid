package com.foodgrid.common.security.filter;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.TokenData;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Setter
public class RequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtility jwtTokenUtility;
    private final UserDetailsServiceImplementation userDetailsService;
    private final UserRepository userRepository;
    private final UserSession userSession;

    @Value("${endpoint.authentication.logout}")
    private String logout;

    @Value("${endpoint.authentication.logoutAll}")
    private String logoutAll;

    @Value("${endpoint.service}")
    private String serviceType;

    @Value("${endpoint.version}")
    private String version;

    @Autowired
    public RequestFilter(JwtTokenUtility jwtTokenUtil, UserDetailsServiceImplementation userDetailsService, UserRepository userRepository, UserSession userSession) {
        this.jwtTokenUtility = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain) throws ServletException, IOException {

        final String authorization = httpServletRequest.getHeader("Authorization");
        var flag = false;

        String username = null;
        String jwt = null;
        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                jwt = authorization.substring(7);
                username = jwtTokenUtility.getUsernameFromToken(jwt);
            }
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            flag = true;
        }

        User user = null;
        if (username != null)
            user = userRepository.findByUsername(username).orElse(null);
        String finalJwt = jwt;
        if (user != null) {
            List<String> activeTokens = user.getActiveTokens()
                    .stream()
                    .map(TokenData::getToken)
                    .collect(Collectors.toList());

            if (!activeTokens.contains(finalJwt)) {
                flag = true;
            }

            userSession.setUserId(user.getId());

            if (httpServletRequest.getRequestURL().toString().contains(logout)) {
                userRepository.save(user.removeToken(finalJwt));
            }
            if (httpServletRequest.getRequestURL().toString().contains(logoutAll))
                userRepository.save(user.setActiveTokens(new ArrayList<>()));

            userSession.setToken(jwt);
            userSession.setUsername(user.getUsername());
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetailsImplementation userDetails = (UserDetailsImplementation) userDetailsService.loadUserByUsername(username);
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            usernamePasswordAuthenticationToken
                    .setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        if (Boolean.TRUE.equals(flag)) {
            httpServletResponse.sendRedirect("/" + serviceType + "/" + version + "/exception");
        } else
            filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
