package com.sipibibu.messenger.app.repositories;

import com.sipibibu.messenger.app.entity.ChatEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<ChatEntity, Long> {
}
