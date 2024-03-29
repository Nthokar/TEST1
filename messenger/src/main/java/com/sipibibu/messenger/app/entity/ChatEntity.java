package com.sipibibu.messenger.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {
    @Id
    @Column(name = "Id",nullable = false)
    @GeneratedValue
    private Long id;
    @Column(name="name",nullable = false)
    private String name;
    @JoinColumn(name="users")
    @ManyToMany
    private List<UserEntity> users;
    @JoinColumn(name="owner")
    @ManyToOne
    private UserEntity owner;
    @OneToMany
    @Setter
    @JoinColumn(name="messages")
    private List<MessageEntity> messages=new ArrayList<>();

    /**
     * @param name Имя чата
     * @param users Список UserEntity пользователей в чате
     * @param owner UserEntity создателя чата
     */
    public ChatEntity(String name, List<UserEntity> users, UserEntity owner){
        this.name=name;
        this.users=users;
        this.owner=owner;
        this.messages=new ArrayList<>();
    }
    public ChatEntity(String name, UserEntity owner){
        this.name=name;
        users=new ArrayList<>();
        this.owner=owner;
        this.messages=new ArrayList<>();
    }
    /**
     * Изменяет имя чата.
     *
     * @param newName Новое имя чата
     * @return BaseResponse
     */
    public void setName(@NotNull String newName) throws RuntimeException {
        if (!newName.replaceAll("\\s+", "").isEmpty())
            name = newName.strip();
        else
            throw new RuntimeException("Incorrect name");
    }

    /**
     * Добавляет пользователей
     *
     * @param users Список UseEntity пользователей
     */
    public void addUsers(List<UserEntity> users) {
        this.users.addAll(users.stream().filter(x-> this.users.stream().noneMatch(y->y.getId()==x.getId())).toList());
    }

    /**
     * Проверяет есть ли пользователь в чате.
     *
     * @param user UserEntity пользователь
     * @return true или false
     */
    public boolean inUsers(@NotNull UserEntity user) {
        return users.stream().anyMatch(x -> x.getId()== user.getId());
    }
    /**
     * Проверяет есть ли пользователь в чате.
     *
     * @param id id пользователя.
     * @return true или false
     */
    public boolean inUsers(Long id) {
        return users.stream().anyMatch(x -> x.getId()== id);
    }
    /**
     * Удаляет пользователя из чата.
     *
     * @param userId Id пользователя.
     */
    public void deleteUser(Long userId) {
        users.removeIf(x -> x.getId()==(userId));
    }

    /**
     * Изменяет владельца на нового из списка пользователей
     *
     * @param newOwner Новый владелец
     */
    public void changeOwner(Long newOwner){
        if(newOwner==owner.getId()){
            throw new RuntimeException("New owner is a Old Owner");
        }
        UserEntity tmpOwner=users.stream().filter(x->x.getId()==newOwner)
                .findFirst().orElseThrow( ()-> new RuntimeException("New owner not in members"));
        this.users.remove(tmpOwner);
        owner=tmpOwner;
    }
    /**
     * Удаляет владельца и заменяет на первого человека в списке
     * */

    public  boolean deleteOwner(){
        if(users.isEmpty())
            return false;
        UserEntity tmp=users.get(0);
        owner=tmp;
        users.remove(tmp);
        return true;
    }
}
