package com.backend.timkiemdong.filter;

import com.backend.timkiemdong.service.AppUserDetailService;
import com.backend.timkiemdong.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component

public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AppUserDetailService appUserDetailService;

    private static List<String> PUBLIC_URLS = List.of("/auth/login", "/auth/register");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if(PUBLIC_URLS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = null;
        String email = null;
        final String authHeader = request.getHeader("Authorization");
        if(authHeader !=null && authHeader.startsWith("Bearer ")) {
           jwt = authHeader.substring(7);

        }

        if(jwt == null) {
            Cookie [] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies) {
                    if("jwt".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if(jwt != null) {
            email = jwtUtil.extractEmail(jwt);
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               UserDetails userDetails = appUserDetailService.loadUserByUsername(email);
               if (jwtUtil.validateToken(jwt, userDetails)) {
                   UsernamePasswordAuthenticationToken authentication =
                           new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authentication);

               }

            }
        }

        filterChain.doFilter(request, response);
    }
}
