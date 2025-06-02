package com.backend.timkiemdong.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Lớp tiện ích dùng để xử lý JWT (JSON Web Token) trong ứng dụng.
 * Cung cấp các phương thức để tạo, kiểm tra và trích xuất thông tin từ token.
 */
@Component
public class JwtUtil {

    /**
     * Khóa bí mật dùng để ký và xác thực token JWT.
     * Được cấu hình trong file application.properties với key: jwt.secret.key
     */
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    /**
     * Tạo JWT token cho người dùng sau khi đăng nhập thành công.
     *
     * @param userDetails Thông tin người dùng (do Spring Security cung cấp)
     * @return Chuỗi token JWT đã được tạo
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Tạo JWT token với claims tùy chọn và thông tin email (hoặc username).
     *
     * @param claims Thông tin bổ sung (có thể rỗng)
     * @param email  Email hoặc username sẽ được dùng làm chủ thể (subject) của token
     * @return Chuỗi JWT token đã được ký
     */
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thời gian tạo token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Hết hạn sau 10 giờ
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // Ký token
                .compact();
    }

    /**
     * Trích xuất toàn bộ claims (thông tin) từ JWT token.
     *
     * @param token Token JWT cần giải mã
     * @return Đối tượng Claims chứa thông tin bên trong token
     */
    private Claims extracAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Trích xuất một thông tin cụ thể từ token bằng hàm xử lý.
     *
     * @param token           Token JWT cần trích xuất
     * @param claimsResolver  Hàm xử lý để lấy thông tin cụ thể từ Claims
     * @param <T>             Kiểu dữ liệu trả về
     * @return Giá trị được trích xuất từ token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extracAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Trích xuất email (hoặc username) từ token.
     *
     * @param token Token JWT
     * @return Email hoặc username lưu trong token
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Trích xuất ngày hết hạn của token.
     *
     * @param token Token JWT
     * @return Thời điểm hết hạn của token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Kiểm tra token đã hết hạn hay chưa.
     *
     * @param token Token JWT
     * @return true nếu token đã hết hạn, ngược lại false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Kiểm tra tính hợp lệ của token:
     * - Token có đúng username không?
     * - Token có còn hạn không?
     *
     * @param token       Token JWT
     * @param userDetails Thông tin người dùng để đối chiếu
     * @return true nếu token hợp lệ, ngược lại false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
