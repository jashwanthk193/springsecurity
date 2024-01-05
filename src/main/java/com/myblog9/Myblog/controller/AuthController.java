package com.myblog9.Myblog.controller;

import com.myblog9.Myblog.entity.Role;
import com.myblog9.Myblog.entity.user;
import com.myblog9.Myblog.payload.JWTAuthResponse;
import com.myblog9.Myblog.payload.LoginDto;
import com.myblog9.Myblog.payload.SignUpDto;
import com.myblog9.Myblog.repository.roleRepository;
import com.myblog9.Myblog.repository.userRepository;
import com.myblog9.Myblog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private userRepository userrepository;
    @Autowired
    private roleRepository rolerepo;
    @Autowired
    private AuthenticationManager authenticationmanager;
    @Autowired
    private PasswordEncoder passwordencoder;
    @Autowired

    private JwtTokenProvider tokenProvider;


    @PostMapping()
    public ResponseEntity<?> registeruser(@RequestBody SignUpDto signupdto) {
        if (userrepository.existsByEmail(signupdto.getEmail())) {
            new ResponseEntity<>("Email already exists" + signupdto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userrepository.existsByUsername(signupdto.getUsername())) {
            new ResponseEntity<>("Username already exists" + signupdto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user users = new user();
        users.setName(signupdto.getName());
        users.setEmail(signupdto.getEmail());
        users.setUsername(signupdto.getUsername());
        users.setPassword(passwordencoder.encode(signupdto.getPassword()));
        Role roles=rolerepo.findByName("ROLE_ADMIN").get();
        Set<Role> role =new HashSet<>();
        role.add(roles);
        users.setRoles(role);
        userrepository.save(users);
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);
    }
    @PostMapping ("/signin")
    public ResponseEntity<JWTAuthResponse> authenticationUser(@RequestBody LoginDto logindto){
        Authentication authentication = authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(
                logindto.getUsernameOrEmail(), logindto.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);


// get token form tokenProvider

        String token = tokenProvider.generateToken(authentication);


        return ResponseEntity.ok(new JWTAuthResponse(token));

    }


}
