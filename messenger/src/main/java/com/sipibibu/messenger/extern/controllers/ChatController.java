package com.sipibibu.messenger.extern.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sipibibu.messenger.app.services.ChatService;
import com.sipibibu.messenger.extern.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/chats",produces = "application/json")
public class ChatController {

    @Autowired
    private ChatService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthClient client;

    /**Получение чата по ID
     * @param id чата
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable Long id){
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(service.get(id)));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**Создает чат
     * @param name имя чата
     * @return BaseResponse
     */
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody String name){
        try {
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            var token=auth.getCredentials();
            Long ownerId= client.getCurrentId().getBody();

            return ResponseEntity.ok(objectMapper.writeValueAsString(service.create(name,ownerId)));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * Добавляет пользователей в чат
     *
     * @param chatId id чата
     * @param userIds id пользователей
     * @return BaseResponse
     */
    @PutMapping("/{chatId}/addUsers")
    public ResponseEntity<String> addUsers(@PathVariable Long chatId,@RequestParam List<Long> userIds){
        try{
            service.addUsers(chatId,userIds);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * Удаляет пользователя из чата
     *
     * @param chatId id чата
     * @param userId id пользователя
     * @return BaseResponse
     */
    @PutMapping("/{chatId}/deleteUsers")
    public ResponseEntity<String> deleteUsers(@PathVariable Long chatId,@RequestParam Long userId ){
        try{
            service.deleteUser(chatId,userId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * Изменяет название чата
     *
     * @param chatId id чата
     * @param newName Новое имя чата
     * @return
     */
    @PutMapping("/{chatId}/changeName")
    public ResponseEntity<String> changeName(@PathVariable Long chatId,@RequestParam String newName ){
        try{
           return ResponseEntity.ok(service.changeName(chatId,newName));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PutMapping("/{chatId}/changeOwner")
    public  ResponseEntity<String> changeOwner(@PathVariable Long chatId,Long newOwnerId,Long curUserId){
        try{
            service.changeOwner(chatId,newOwnerId,curUserId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
