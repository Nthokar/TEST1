package com.nthokar.spring2023.userauth.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "formClient", url="${services.gateway}"+"/forms")
public interface FormClient {

    @GetMapping("/getAvailableById")
    ResponseEntity<Long[]> get(@RequestBody List<Long> ids);
}
