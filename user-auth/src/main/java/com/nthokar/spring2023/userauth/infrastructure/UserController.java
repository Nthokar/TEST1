package com.nthokar.spring2023.userauth.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nthokar.spring2023.userauth.app.entities.User;
import com.nthokar.spring2023.userauth.app.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class UserController {
    @Autowired
    MyUserDetailsService userService;
    ObjectMapper mapper = new ObjectMapper();

    @PutMapping("/setImage")
    public ResponseEntity<String> setImage(String base64, Authentication authentication) {
        var user = userService.getUser(authentication.getName());
        userService.setImage(user, base64);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/setAge")
    public ResponseEntity<String> setAge(@RequestBody ageDTO ageDTO, Authentication authentication) {
        try {
            Integer ageInt = Integer.parseInt(ageDTO.age);
            var user = userService.getUser(authentication.getName());
            userService.setAge(user, ageInt);
            user.setAge(ageInt);
            return ResponseEntity.ok().body(mapper.writeValueAsString(ageDTO));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/image")
    public ResponseEntity<String> getImage(@RequestBody emailDTO emailDTO) {
        try {
            var user = userService.getUser(emailDTO.email);
            return ResponseEntity.ok().body(user.getImage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<User> getCurrent(Authentication authentication) {
        try {
            var user = userService.getUser(authentication.getName());
            user.setPassword("");
            user.setId(null);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            throw new RuntimeException();
            //return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getId")
    public ResponseEntity<Long> getCurrentId(Authentication authentication) {
        try {
            var user = userService.getUser(authentication.getName());
            return ResponseEntity.ok().body(user.getId());
        } catch (Exception e) {
            throw new RuntimeException();
            //return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    record educationDTO (String education) {};
    record ageDTO (String age) {};
    record emailDTO (String email) {};
    record interestsDTO (List<String> interests) {};
}
