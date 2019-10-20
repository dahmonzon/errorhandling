package com.example.errorhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.concurrent.Delayed;

@RestController
public class TestController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IdentityEncoder passwordEncoder;

    @Autowired
    private UserService userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
        System.out.println(ar.toString());
        return userRepository.findUser(ar.getUsername()).map((userDetails) -> {
            System.out.println(userDetails.getPassword());
            System.out.println(passwordEncoder.encode(ar.getPassword()));
            if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @RequestMapping(value = "/resource/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USERS')")
    public Mono<ResponseEntity<?>> user() {
        return Mono.just(ResponseEntity.ok(new AuthMessage("Content for user")));
    }

    @RequestMapping(value = "/resource/admin", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','USERS')")
    public Mono<ResponseEntity<?>> admin() {
        return Mono.just(ResponseEntity.ok(new AuthMessage("Content for admin")));
    }

    @RequestMapping(value = "/resource/user-or-admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USERS') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> userOrAdmin() {
        return Mono.just(ResponseEntity.ok(new AuthMessage("Content for user or admin")));
    }
}
