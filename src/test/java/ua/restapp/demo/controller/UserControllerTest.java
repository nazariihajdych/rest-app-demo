package ua.restapp.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ua.restapp.demo.exception.GlobalExceptionHandler;
import ua.restapp.demo.model.dto.UserDTO;
import ua.restapp.demo.service.UserService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @Order(1)
    void createUser_successfully() throws Exception {
        UserDTO fakeUserDTO = new UserDTO();
        fakeUserDTO.setEmail("john.dou@mail.com");
        fakeUserDTO.setFirstName("John");
        fakeUserDTO.setLastName("Dou");
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUserDTO.setPhoneNumber("0987654321");

        String jFakeUser = objectMapper.writeValueAsString(fakeUserDTO);

        mockMvc.perform(post("/users")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakeUser))
                .andExpect(status().isCreated())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.email").value("john.dou@mail.com"))
                .andReturn();
    }

    @Test
    @Order(2)
    void createUser_unsuccessful_passingNotValidFieldValues() throws Exception {

        HashMap<String, String> expectedValues = new HashMap<>();
        expectedValues.put("email", "not valid email");
        expectedValues.put("dateOfBirth", "should be in past");

        UserDTO fakeUserDTO = new UserDTO();
        fakeUserDTO.setEmail("@john.dou@mail.com");
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"));
        fakeUserDTO.setPhoneNumber("0987654321");

        String jsonUserDTO = objectMapper.writeValueAsString(fakeUserDTO);

        mockMvc.perform(post("/users")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserDTO))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(expectedValues,
                        objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class)))
                .andReturn();
    }

    @Test
    @Order(3)
    void updateUser_successful() throws Exception {
        UserDTO fakeUserDTO = new UserDTO();
        fakeUserDTO.setEmail("changed@mail.com");
        fakeUserDTO.setFirstName("Changed");
        fakeUserDTO.setLastName("Dou");
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUserDTO.setPhoneNumber("0987654321");

        String jFakeUserDTO = objectMapper.writeValueAsString(fakeUserDTO);

        mockMvc.perform(put("/users/{id}", 1L)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakeUserDTO))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.firstName").value("Changed"))
                .andExpect(jsonPath("$.email").value("changed@mail.com"))
                .andReturn();
    }

    @Test
    @Order(4)
    void updateUserPartly_successful() throws Exception {
        UserDTO fakeUserDTO = new UserDTO();
        fakeUserDTO.setEmail("changed.partly@mail.com");

        String jFakeUserDTO = objectMapper.writeValueAsString(fakeUserDTO);

        mockMvc.perform(patch("/users/{id}", 1L)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakeUserDTO))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.firstName").value("Changed"))
                .andExpect(jsonPath("$.email").value("changed.partly@mail.com"))
                .andReturn();
    }

    @Test
    @Order(5)
    void getUserByBirthDateRange_successful() throws Exception {
        mockMvc.perform(get("/users")
                        .param("from", "1998-01-01")
                        .param("to", "2022-01-01"))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(List.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), List.class)
                ))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("changed.partly@mail.com"))
                .andReturn();
    }

    @Test
    @Order(6)
    void deleteUser_successful() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.email").value("changed.partly@mail.com"))
                .andReturn();
    }
}