package com.example.sidepot.global.config.security;


import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.app.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    OwnerService ownerService;

    @MockBean
    StaffService staffService;

    @Test
    @WithMockUser(username = "user1")
    void 오너_직원_접근() throws Exception {
        mockMvc.perform(post("/rest/v1/staff/login"))
                .andExpect(status().isOk());

    }
}
