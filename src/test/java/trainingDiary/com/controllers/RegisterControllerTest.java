package trainingDiary.com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import trainingDiary.com.config.TestBeans;
import trainingDiary.com.dto.LoginUserDto;
import trainingDiary.com.exceptions.NotUniqueUserNameException;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("RegisterController tests")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBeans.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
    }

    @DisplayName("Success registration")
    @Sql(value = "classpath:/data/users/deleteAll.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/data/users/insertData.sql", executionPhase = BEFORE_TEST_METHOD)
    @Test
    public void authorizeSuccess() throws Exception {
        LoginUserDto newUser = LoginUserDto.builder()
                .username("NewUser")
                .password("123123")
                .build();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated());
    }

    @Sql(value = "classpath:/data/users/deleteAll.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/data/users/insertData.sql", executionPhase = BEFORE_TEST_METHOD)
    @DisplayName("Fail while registration")
    @Test
    public void authorizeThrowsException() throws Exception {
        LoginUserDto newUser = LoginUserDto.builder()
                .username("Vlad")
                .password("123123")
                .build();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(result -> assertInstanceOf(NotUniqueUserNameException.class, result.getResolvedException()));
    }
}