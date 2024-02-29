package com.sipibibu.messenger.extern.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="AuthClient", url = "${services.gateway}"+"/account")
public interface AuthClient {

    @GetMapping("/getId")
    ResponseEntity<Long> getCurrentId();

}
