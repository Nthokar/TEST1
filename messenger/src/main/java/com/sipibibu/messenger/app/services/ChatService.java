package com.sipibibu.messenger.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sipibibu.messenger.app.entity.ChatEntity;
import com.sipibibu.messenger.app.entity.UserEntity;
import com.sipibibu.messenger.app.repositories.ChatRepository;
import com.sipibibu.messenger.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;


    /** Получение чата по ID
     * @param chatId Id чата
     * @return Чат
     * @throws RuntimeException
     */
    public  ChatEntity get(Long chatId) throws RuntimeException {
        return  chatRepository.findById(chatId).orElseThrow(()->new RuntimeException("No chat with id: "+chatId));
    }
    /** Создает чат
     * @param name Имя чата
     * @param ownerId Создатель чата
     * @return Чат
     */
    public ChatEntity create(String name, Long ownerId) throws RuntimeException{
        UserEntity owner=userRepository.findById(ownerId)
                .orElse(userRepository.save(new UserEntity(ownerId)));
        ChatEntity newChat=new ChatEntity(name,owner);
        chatRepository.save(newChat);
        return  newChat;
    }


    /**
     * Добавляет пользователей в чат
     *
     * @param chatId   Id чата
     * @param usersIds ID пользователей
     * @throws RuntimeException
     */
    public void addUsers(Long chatId, List<Long> usersIds)throws RuntimeException{
        Optional<ChatEntity> chat=chatRepository.findById(chatId);
        List<UserEntity> users=usersIds.stream().map(x->userRepository.findById(x)
                .orElse(userRepository.save(new UserEntity(x)))).toList();
        chat.get().addUsers(users);
        chatRepository.save(chat.get());
    }

    /**Удаляет пользователя из чата
     * @param chatId Id чата
     * @param userId Id Пользователя
     * @throws RuntimeException
     *
     */
    public  void deleteUser(Long chatId, Long userId) throws RuntimeException{
        ChatEntity chat=chatRepository.findById(chatId)
                .orElseThrow(()->new RuntimeException("No such chat with id: "+chatId));
        UserEntity user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("No such user with id: "+userId));
        if(chat.getOwner().getId()==userId){
            chat.deleteUser(userId);
        }
        if(!chat.inUsers(user))
            throw  new RuntimeException("No such user with id: "+userId+" in this chat");


        chatRepository.save(chat);
    }

    /**
     * Меняет имя чата
     *
     * @param chatId Id чата
     * @param newName Новое имя чата
     * @return Старое и новое имя чата
     * @throws RuntimeException
     */
    public  String changeName(Long chatId,String newName) throws RuntimeException{
        ChatEntity chat=chatRepository.findById(chatId)
                .orElseThrow(()->new RuntimeException("No chat with id: "+chatId));
        chat.setName(newName);
        chatRepository.save(chat);
        return "OldName:"+chat.getName()+"\nNewName: "+chat.getName();
    }
    public void deleteOwner(Long chatId,Long curUserId){
        ChatEntity chat=chatRepository.findById(chatId)
                .orElseThrow(()->new RuntimeException("No chat with id: "+chatId));
        if(chat.getOwner().getId()!=curUserId)
            throw new RuntimeException("CurUser is not the owner");
        if(!chat.deleteOwner()){
            deleteChat(chatId,curUserId);
        }
    }
    public  void changeOwner(Long chatId,Long newOwnerId,Long curUserId){
        ChatEntity chat=chatRepository.findById(chatId)
                .orElseThrow(()->new RuntimeException("No chat with id: "+chatId));
        if(chat.getOwner().getId()!=curUserId)
            throw new RuntimeException("CurUser is not the owner");
        chat.changeOwner(newOwnerId);
        chatRepository.save(chat);
    }
    public void deleteChat(Long chatId,Long curUserId){

    }
}
