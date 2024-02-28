package com.nthokar.spring2023.userauth.app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
public class User {
    public User(String email, String firstName, String lastName, String password, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = new HashSet<>();
        this.roles.add(role);
    }
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="firstName")
    protected String firstName;

    @Column(name="lastName")
    protected String lastName;

    @Column(name = "age")
    protected Integer age;

    @Column(name="email")
    protected String email;

    @Column(name="password")
    protected String password;

    //BASE64
    @Column(name="image")
    protected String image;

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role> roles;
}

