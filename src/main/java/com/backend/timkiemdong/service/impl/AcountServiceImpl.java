package com.backend.timkiemdong.service.impl;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.dto.AcountResponse;
import com.backend.timkiemdong.dto.AuthResponse;
import com.backend.timkiemdong.dto.LoginRequest;
import com.backend.timkiemdong.entity.Acount;
import com.backend.timkiemdong.entity.Permission;
import com.backend.timkiemdong.entity.Role;
import com.backend.timkiemdong.mapper.AcountMapper;
import com.backend.timkiemdong.repository.AcountRepository;
import com.backend.timkiemdong.repository.PermissionRepository;
import com.backend.timkiemdong.repository.RoleRepository;
import com.backend.timkiemdong.service.AcountService;
import com.backend.timkiemdong.service.AppUserDetailService;
import com.backend.timkiemdong.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AcountServiceImpl implements AcountService {
    @Autowired
    private AcountRepository acountRepository;
    @Autowired
    private AcountMapper acountMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailService appUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;


    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Xác thực email + password
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        // Load thông tin user
        UserDetails userDetails = appUserDetailService.loadUserByUsername(loginRequest.getEmail());

        // Tạo JWT
        String jwtToken = jwtUtil.generateToken(userDetails);

        // Lấy role
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .findFirst()
                .orElse("UNKNOWN");

        // Lấy permission
        String permission = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .collect(Collectors.joining(","));

        // Lấy tên người dùng
        Optional<Acount> optionalUser = acountRepository.findByEmail(loginRequest.getEmail());
        String name = optionalUser.map(Acount::getName).orElse("");

        // Trả về response
        return new AuthResponse(name, loginRequest.getEmail(), jwtToken, role, permission);
    }





    //    @Override
//    public AcountResponse createAcount(AcountRequest acountRequest){
//            Acount acount = acountMapper.toAcount(acountRequest);
//            acount.setPassword(passwordEncoder.encode(acountRequest.getPassword()));
//
//            if(!acountRepository.existsByEmail(acountRequest.getEmail())){
//                acountRepository.save(acount);
//                return acountMapper.toAcountResponse(acount);
//            }
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exist");
//
//    }
    @Override
    public AcountResponse createAcount(AcountRequest acountRequest) {
        if (acountRepository.existsByEmail(acountRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exist");
        }

        Acount acount = acountMapper.toAcount(acountRequest);
        acount.setPassword(passwordEncoder.encode(acountRequest.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found. Please initialize roles in DB"));

        acount.setRoles(Set.of(userRole));


        acountRepository.save(acount);
        return acountMapper.toAcountResponse(acount);
    }



    @Override
    public AcountResponse updateRole(AcountRequest acountRequest) {

        Acount acount = acountRepository.findById(acountRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(acountRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        acount.setRoles(roles);
        return acountMapper.toAcountResponse(acountRepository.save(acount));
    }

    @Override
    public AcountResponse updatePermission(AcountRequest acountRequest) {
        Acount acount = acountRepository.findById(acountRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Permission permission = permissionRepository.findByName(acountRequest.getPermission())
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        // Lấy roles của tài khoản
        Set<Role> roles = acount.getRoles();


        Role role = roles.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found in account"));

        // Thêm permission mới vào role (nếu chưa có)
        if (!role.getPermissions().contains(permission)) {
            role.getPermissions().add(permission);
            roleRepository.save(role);
        }


        acountRepository.save(acount);

        return acountMapper.toAcountResponse(acount);
    }

    @Override
    public AcountResponse removePermission(AcountRequest acountRequest) {
        Acount acount = acountRepository.findById(acountRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Permission permission = permissionRepository.findByName(acountRequest.getPermission())
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        // Lấy roles của tài khoản
        Set<Role> roles = acount.getRoles();


        Role role = roles.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found in account"));

        // Xoá permission nếu role đang có
        if (role.getPermissions().contains(permission)) {
            role.getPermissions().remove(permission);
            roleRepository.save(role);
        }

        // Cập nhật lại cho account nếu cần
        acount.setRoles(roles);
        acountRepository.save(acount);

        return acountMapper.toAcountResponse(acount);
    }

}
