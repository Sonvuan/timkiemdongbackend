package com.backend.timkiemdong.filter;

import com.backend.timkiemdong.service.AppUserDetailService;
import com.backend.timkiemdong.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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

/**
 * Lớp filter dùng để xử lý và xác thực token JWT trong mỗi HTTP request.
 * Lớp này được chạy một lần cho mỗi request thông qua `OncePerRequestFilter`.
 * Nếu request chứa token hợp lệ, nó sẽ xác thực và thiết lập người dùng vào `SecurityContext`.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /** Tiện ích để tạo và kiểm tra JWT */
    @Autowired
    private JwtUtil jwtUtil;

    /** Service để load thông tin người dùng từ cơ sở dữ liệu */
    @Autowired
    private AppUserDetailService appUserDetailService;

    /** Danh sách các URL công khai không cần xác thực */
    private static final List<String> PUBLIC_URLS = List.of("/auth/login", "/auth/register");

    /**
     * Thực thi filter cho mỗi request:
     * <ul>
     *     <li>Kiểm tra xem request có chứa JWT không (từ header hoặc cookie)</li>
     *     <li>Giải mã JWT để lấy email người dùng</li>
     *     <li>Xác thực token và thiết lập thông tin người dùng vào SecurityContext</li>
     *     <li>Nếu là đường dẫn công khai, bỏ qua xác thực</li>
     * </ul>
     *
     * @param request      HTTP request
     * @param response     HTTP response
     * @param filterChain  chuỗi các filter tiếp theo
     * @throws ServletException lỗi servlet
     * @throws IOException      lỗi IO
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (PUBLIC_URLS.contains(path)) {
            // Nếu là URL công khai (login, register) thì bỏ qua filter
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = null;
        String email = null;

        // Lấy JWT từ header Authorization
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }

        // Nếu không có trong header, thử lấy từ cookie
        if (jwt == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
        }

        // Nếu có token, tiến hành xác thực
        if (jwt != null) {
            email = jwtUtil.extractEmail(jwt);

            // Chỉ xác thực nếu chưa có user trong SecurityContext
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = appUserDetailService.loadUserByUsername(email);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // Tiếp tục chuỗi filter
        filterChain.doFilter(request, response);
    }
}
