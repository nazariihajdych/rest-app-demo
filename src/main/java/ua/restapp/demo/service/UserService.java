package ua.restapp.demo.service;

import ua.restapp.demo.model.dto.UserDTO;

import java.util.Date;
import java.util.List;

public interface  UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    UserDTO partialUserUpdate(Long id, UserDTO userDTO);

    UserDTO deleteUser(Long id);

    List<UserDTO> getUserByBirthDateRange(Date from, Date to);
}
