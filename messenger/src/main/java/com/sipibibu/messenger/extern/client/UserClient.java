package com.sipibibu.messenger.extern.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="userClient", url = "${services.gateway}")
public interface UserClient {

    ResponseEntity<String> addChat(@RequestParam Long chatId, Authentication auth);
}
