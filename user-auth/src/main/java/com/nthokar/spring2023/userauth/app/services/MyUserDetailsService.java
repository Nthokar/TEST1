package com.nthokar.spring2023.userauth.app.services;

import com.nthokar.spring2023.userauth.app.UserDetails;
import com.nthokar.spring2023.userauth.app.entities.*;
import com.nthokar.spring2023.userauth.app.repos.*;
import com.nthokar.spring2023.userauth.infrastructure.clients.FormClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    FormClient formClient;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email = " + email + " not exist!"));
        return new UserDetails(user);
    }

    public void register(User user) {
        if (userRepo.findByEmail(user.getEmail()).isEmpty())
            userRepo.save(user);
        else
            throw new RuntimeException("user with that email already exists");
    }

    public User getUser(String email) {
        var user = userRepo.findByEmail(email);
        if (user.isPresent()) return user.get();
        throw new RuntimeException("no user");
    }

    public void setImage(User user, String image) {
        user.setImage(image);
        userRepo.save(user);
    }

    public void setAge(User user, Integer age) {
        user.setAge(age);
        userRepo.save(user);
    }
}