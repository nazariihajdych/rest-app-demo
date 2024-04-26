package ua.restapp.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ua.restapp.demo.exception.UserNotFoundException;
import ua.restapp.demo.model.dto.UserDTO;
import ua.restapp.demo.model.entity.User;
import ua.restapp.demo.model.mapper.UserMapper;
import ua.restapp.demo.repo.UserRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private User fakeUser;
    private UserDTO fakeUserDTO;

    @BeforeEach
    void setUp() throws ParseException {
        ReflectionTestUtils.setField(userServiceImpl, "registerAgeLimit", 18);

        fakeUser = new User();
        fakeUser.setId(1L);
        fakeUser.setEmail("john.dou@mail.com");
        fakeUser.setFirstName("John");
        fakeUser.setLastName("Dou");
        fakeUser.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUser.setPhoneNumber("0987654321");

        fakeUserDTO = new UserDTO();
        fakeUserDTO.setEmail("john.dou@mail.com");
        fakeUserDTO.setFirstName("John");
        fakeUserDTO.setLastName("Dou");
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
        fakeUserDTO.setPhoneNumber("0987654321");
    }

    @Test
    void createUser_successful() {

        when(userMapper.userDTOToUser(fakeUserDTO)).thenReturn(fakeUser);
        when(userRepo.save(fakeUser)).thenReturn(fakeUser);
        when(userMapper.userToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        UserDTO result = userServiceImpl.createUser(fakeUserDTO);

        verify(userMapper, times(1)).userDTOToUser(fakeUserDTO);
        verify(userRepo, times(1)).save(any());
        verify(userMapper, times(1)).userDTOToUser(any());

        assertNotNull(result);
        assertEquals(result, fakeUserDTO);
    }

    @Test
    void createUser_unsuccessful_LessThanYearsLimit() throws ParseException {
        fakeUserDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01"));
        String expectedMessage = "User must be at least 18 years old.";

        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.createUser(fakeUserDTO), expectedMessage);
    }

    @Test
    void updateUser_successful() {
        Long testId = 1L;

        when(userRepo.findById(testId)).thenReturn(Optional.ofNullable(fakeUser));
        when(userRepo.save(fakeUser)).thenReturn(fakeUser);
        when(userMapper.userToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        UserDTO updatedUser = userServiceImpl.updateUser(testId, fakeUserDTO);

        verify(userRepo, times(1)).findById(testId);
        verify(userMapper, times(1)).updateUserFromDTO(fakeUserDTO, fakeUser);
        verify(userRepo, times(1)).save(fakeUser);
        verify(userMapper, times(1)).userToUserDTO(fakeUser);

        assertNotNull(updatedUser);
        assertEquals(updatedUser, fakeUserDTO);
    }

    @Test
    void partialUserUpdate_successful() {
        Long testId = 1L;

        when(userRepo.findById(testId)).thenReturn(Optional.ofNullable(fakeUser));
        when(userRepo.save(fakeUser)).thenReturn(fakeUser);
        when(userMapper.userToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        UserDTO updatedUser = userServiceImpl.partialUserUpdate(testId, fakeUserDTO);

        verify(userRepo, times(1)).findById(testId);
        verify(userMapper, times(1)).partlyUpdateUserFromDTO(fakeUserDTO, fakeUser);
        verify(userRepo, times(1)).save(fakeUser);
        verify(userMapper, times(1)).userToUserDTO(fakeUser);

        assertNotNull(updatedUser);
        assertEquals(updatedUser, fakeUserDTO);
    }

    @Test
    void deleteUser_successful() {
        Long testId = 1L;

        when(userRepo.findById(testId)).thenReturn(Optional.ofNullable(fakeUser));
        when(userMapper.userToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        UserDTO deletedUser = userServiceImpl.deleteUser(testId);

        verify(userRepo, times(1)).findById(testId);
        verify(userRepo, times(1)).delete(fakeUser);
        verify(userMapper, times(1)).userToUserDTO(fakeUser);

        assertNotNull(deletedUser);
        assertEquals(deletedUser, fakeUserDTO);
    }

    @Test
    void getUserByBirthDateRange_successful() throws ParseException {
        List<User> users = new ArrayList<>();
        users.add(fakeUser);

        Date testFrom = new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01");
        Date testTo = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");


        when(userRepo.findByDateOfBirthBetween(testFrom, testTo)).thenReturn(users);
        when(userMapper.userToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        List<UserDTO> userByBirthDateRange = userServiceImpl.getUserByBirthDateRange(testFrom, testTo);

        verify(userRepo, times(1)).findByDateOfBirthBetween(testFrom, testTo);
        verify(userMapper, times(1)).userToUserDTO(fakeUser);

        assertNotNull(userByBirthDateRange);
        assertEquals(1, userByBirthDateRange.size());
    }

    @Test
    void getUserByBirthDateRange_unsuccessful_ToLessThanFrom() throws ParseException {

        Date testFrom = new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01");
        Date testTo = new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01");
        String expectedMessage = "'From' date must be before 'To' date";

        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.getUserByBirthDateRange(testFrom, testTo), expectedMessage);
    }

    @Test
    void userNotFound_thrown() {
        Long testId = 99L;
        String expectedMessage = "User with id = 99 not found";

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUser(testId), expectedMessage);
    }
}