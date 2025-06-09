package com.backend.timkiemdong.config;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.entity.Acount;
import com.backend.timkiemdong.entity.Permission;
import com.backend.timkiemdong.entity.Role;


import com.backend.timkiemdong.filter.JwtRequestFilter;
import com.backend.timkiemdong.repository.AcountRepository;
import com.backend.timkiemdong.repository.PermissionRepository;
import com.backend.timkiemdong.repository.RoleRepository;
import com.backend.timkiemdong.service.AppUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lớp cấu hình bảo mật Spring Security cho ứng dụng.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private AppUserDetailService userDetailService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;
    @Autowired
    private AcountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;


    /**
     * Constructor để inject cấu hình CORS.
     *
     * @param corsConfigurationSource nguồn cấu hình CORS
     */
    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    /**
     * Cấu hình chuỗi filter bảo mật cho HTTP requests.
     *
     * @param http               đối tượng HttpSecurity để cấu hình
     * @param jwtRequestFilter   filter dùng để kiểm tra JWT trong mỗi request
     * @return SecurityFilterChain đã cấu hình
     * @throws Exception nếu có lỗi cấu hình
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
//        http.cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/login", "/auth/register")
//                        .permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .logout(AbstractHttpConfigurer::disable)
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        .requestMatchers("/admin/**","/Country/**").hasAnyRole("ADMIN","STAFF")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN","STAFF")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Tạo bean để mã hoá mật khẩu bằng thuật toán BCrypt.
     *x`x`
     * @return đối tượng PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Tạo bean cho CORS filter.
     *
     * @return CorsFilter cấu hình sẵn
     */
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    /**
     * Cấu hình nguồn CORS cho phép truy cập từ localhost:4200.
     *
     * @return CorsConfigurationSource đã cấu hình
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true); // Cho phép gửi cookie

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Cấu hình AuthenticationManager để xác thực người dùng với UserDetailsService.
     *
     * @return AuthenticationManager đã cấu hình
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            // Tạo permission ADMIN nếu chưa có
            Permission adminPermission = permissionRepository.findByName("ADMIN")
                    .orElseGet(() -> {
                        Permission p = Permission.builder()
                                .name("ADMIN")
                                .description("Quản trị viên")
                                .build();
                        return permissionRepository.save(p);
                    });
            // Tạo ROLE_ADMIN nếu chưa có
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role r = Role.builder()
                                .name("ROLE_ADMIN")
                                .description("Quản trị viên")
                                .build();
                        return roleRepository.save(r);
                    });

            // Gán quyền ADMIN cho role ADMIN nếu chưa gán
            if (roleAdmin.getPermissions().isEmpty()) {
                roleAdmin.getPermissions().add(adminPermission);
                roleRepository.save(roleAdmin);
            }

            // Tạo tài khoản admin nếu chưa có
            String email = "admin@admin.com";
            if (accountRepository.findByEmail(email).isEmpty()) {
                Acount account = Acount.builder()
                        .name("ADMIN")
                        .email(email)
                        .password(passwordEncoder().encode("123456"))
                        .roles(Set.of(roleAdmin))
                        .build();
                accountRepository.save(account);
            }
        };
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"ACCESS_DENIED\"}");
        };
    }

}
