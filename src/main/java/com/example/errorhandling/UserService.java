package com.example.errorhandling;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class UserService {

    // user account
    private Users users = new Users("user","cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY",true, Arrays.asList(Roles.ROLE_USERS));

    // admin account
    private Users admin = new Users("admin","SuBeVOjqNDfRlhrYjg57ofupVe4bs//m0dNAbQNgZA==",true,Arrays.asList(Roles.ROLE_ADMIN));

    public Mono<Users>  findUser(String username){
        if (this.users.getUsername().equals(username)){
            return Mono.just(users);
        }else if (this.admin.getUsername().equals(username)){
            return Mono.just(admin);
        }else {
            return Mono.empty();

        }
    }
}
