package com.sipibibu.messenger.app.repositories;


import com.sipibibu.messenger.app.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {


}
