package com.backend.timkiemdong.controller;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.dto.AcountResponse;
import com.backend.timkiemdong.dto.AuthResponse;
import com.backend.timkiemdong.dto.LoginRequest;
import com.backend.timkiemdong.entity.Acount;
import com.backend.timkiemdong.repository.AcountRepository;
import com.backend.timkiemdong.service.AcountService;
import com.backend.timkiemdong.service.AppUserDetailService;
import com.backend.timkiemdong.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")

public class AuthController {

    @Autowired
    private AcountService acountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailService appUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AcountRepository acountRepository;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AcountResponse register(@RequestBody AcountRequest accountRequest) {
        AcountResponse create = acountService.createAcount(accountRequest);
        return create;
    }

    //    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try{
//            authenticate(loginRequest.getEmail(), loginRequest.getPassword());
//            UserDetails userDetails = appUserDetailService.loadUserByUsername(loginRequest.getEmail());
//            String jwtToken = jwtUtil.generateToken(userDetails);
//            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
//                    .httpOnly(true)
//                    .path("/")
//                    .maxAge(Duration.ofDays(1))
//                    .sameSite("Strict")
//                    .build();
//            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new AuthResponse(loginRequest.getEmail(), jwtToken));// không trả token về body sẽ ẩn token
//        }catch (BadCredentialsException e){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//
//
//    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            // Xác thực email + password
//            authenticate(loginRequest.getEmail(), loginRequest.getPassword());
//
//            // Load thông tin từ UserDetails
//            UserDetails userDetails = appUserDetailService.loadUserByUsername(loginRequest.getEmail());
//            String jwtToken = jwtUtil.generateToken(userDetails);
//
//            // Lấy role
//            String role = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .filter(auth -> auth.startsWith("ROLE_"))
//                    .findFirst()
//                    .orElse("UNKNOWN");
//
//            // Lấy permission
//            String permission = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .filter(auth -> !auth.startsWith("ROLE_"))
//                    .collect(Collectors.joining(","));
//
//
//            Optional<Acount> optionalUser = acountRepository.findByEmail(loginRequest.getEmail());
//            String name = optionalUser.map(Acount::getName).orElse("");
//
//            // Set cookie
//            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
//                    .httpOnly(true)
//                    .path("/")
//                    .maxAge(Duration.ofDays(1))
//                    .sameSite("Strict")
//                    .build();
//
//            // Trả về response
//            AuthResponse authResponse = new AuthResponse(name, loginRequest.getEmail(), jwtToken, role, permission);
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
//                    .body(authResponse);
//
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = acountService.login(loginRequest);

            // Tạo cookie
            ResponseCookie cookie = ResponseCookie.from("jwt", authResponse.getToken())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(authResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }


}
