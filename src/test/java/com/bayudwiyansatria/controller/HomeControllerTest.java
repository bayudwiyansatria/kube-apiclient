package com.bayudwiyansatria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bayudwiyansatria.spring.controller.HomeController;
import com.bayudwiyansatria.spring.service.HomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(
    addFilters = false
)
@EnableAutoConfiguration
@ComponentScan
@ContextConfiguration(classes = {
    HomeController.class,
    HomeService.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    HomeService homeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHome() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }
}
