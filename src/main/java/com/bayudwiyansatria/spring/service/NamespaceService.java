package com.bayudwiyansatria.spring.service;

import com.bayudwiyansatria.spring.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface NamespaceService {

    Response<?> list();
}
