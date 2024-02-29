package com.sipibibu.messenger.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity{
    @Id
    @Column(name = "Id", nullable = false)
    private Long id;

    public UserEntity(Long id) {this.id=id;}
}
