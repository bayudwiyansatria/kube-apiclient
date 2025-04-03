package com.bayudwiyansatria.spring.controller;

import com.bayudwiyansatria.spring.service.NamespaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/namespace")
public class NamespaceController {

    private final NamespaceService namespaceService;


    public NamespaceController(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @GetMapping("")
    public ResponseEntity<?> getNamespaceService() {
        return ResponseEntity
            .ok(this.namespaceService.list());
    }
}
