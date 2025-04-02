package com.bayudwiyansatria.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bayudwiyansatria.spring.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc(
    addFilters = false
)
@ComponentScan("com.bayudwiyansatria.spring")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
class SecretControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();
    }

    @Test
    public void getSecret() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/secret");

        this.mvc
            .perform(requestBuilder)
            .andExpect(
                status().isOk()
            );
    }
}
