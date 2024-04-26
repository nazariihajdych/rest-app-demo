package ua.restapp.demo.model.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.restapp.demo.config.MapperTestConfig;
import ua.restapp.demo.model.dto.UserDTO;
import ua.restapp.demo.model.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MapperTestConfig.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    private User fakeUser;
    private UserDTO fakeUserDTO;

    @BeforeEach
    void setUp() throws ParseException {
        fakeUser = new User();
        fakeUser.setId(1L);
        fakeUser.setEmail("john.dou@mail.com");
        fakeUser.setFirstName("John");
        fakeUser.setLastName("Dou");
        fakeUser.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUser.setPhoneNumber("0987654321");

        fakeUserDTO = new UserDTO();
    }

    @Test
    void userToUserDTO() {

        UserDTO userDTO = userMapper.userToUserDTO(fakeUser);

        assertNotNull(userDTO);
        assertEquals(fakeUser.getEmail(), userDTO.getEmail());
        assertEquals(fakeUser.getFirstName(), userDTO.getFirstName());
    }

    @Test
    void userDTOToUser() throws ParseException {

        fakeUserDTO.setEmail("john.dou@mail.com");
        fakeUserDTO.setFirstName("John");
        fakeUserDTO.setLastName("Dou");
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUserDTO.setPhoneNumber("0987654321");

        User user = userMapper.userDTOToUser(fakeUserDTO);

        assertNotNull(user);
        assertEquals(user.getEmail(), fakeUserDTO.getEmail());
        assertEquals(user.getFirstName(), fakeUserDTO.getFirstName());
    }

    @Test
    void partlyUpdateUserFromDTO() {
        fakeUserDTO.setEmail("new.mail@mail.com");
        fakeUserDTO.setFirstName("NewName");

        userMapper.partlyUpdateUserFromDTO(fakeUserDTO, fakeUser);

        assertNotNull(fakeUser);
        assertEquals(fakeUser.getEmail(), fakeUserDTO.getEmail());
        assertEquals(fakeUser.getFirstName(), fakeUserDTO.getFirstName());
    }

    @Test
    void updateUserFromDTO() throws ParseException {
        fakeUserDTO.setEmail("Jane.dou@mail.com");
        fakeUserDTO.setFirstName("Jane");
        fakeUserDTO.setLastName("Dou");
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));

        userMapper.updateUserFromDTO(fakeUserDTO, fakeUser);

        assertNotNull(fakeUser);
        assertEquals(fakeUser.getEmail(), fakeUserDTO.getEmail());
        assertEquals(fakeUser.getFirstName(), fakeUserDTO.getFirstName());
        assertNull(fakeUser.getPhoneNumber());
    }
}