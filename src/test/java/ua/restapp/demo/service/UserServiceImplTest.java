package ua.restapp.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.restapp.demo.model.dto.UserDTO;
import ua.restapp.demo.model.entity.User;
import ua.restapp.demo.model.mapper.UserMapper;
import ua.restapp.demo.repo.UserRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepo userRepo;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Test
    void createUser() throws ParseException {
        UserDTO fakeUserDTO = new UserDTO();
        fakeUserDTO.setEmail("john.dou@mail.com");
        fakeUserDTO.setFirstName("John");
        fakeUserDTO.setLastName("Dou");
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUserDTO.setPhoneNumber("0987654321");

        User fakeUser = new User();
        fakeUser.setId(1L);
        fakeUser.setEmail("john.dou@mail.com");
        fakeUser.setFirstName("John");
        fakeUser.setLastName("Dou");
        fakeUser.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUser.setPhoneNumber("0987654321");


        when(userMapper.userDTOToUser(fakeUserDTO)).thenReturn(fakeUser);
        when(userRepo.save(fakeUser)).thenReturn(fakeUser);
        when(userMapper.userToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        UserDTO result = userServiceImpl.createUser(fakeUserDTO);

        verify(userMapper, times(1)).userDTOToUser(fakeUserDTO);
        verify(userRepo, times(1)).save(any());
        verify(userMapper, times(1)).userDTOToUser(any());

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result, fakeUserDTO);
    }

    @Test
    void updateUser() {
    }

    @Test
    void partialUserUpdate() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserByBirthDateRange() {
    }
}