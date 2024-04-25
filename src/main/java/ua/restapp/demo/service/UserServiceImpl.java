package ua.restapp.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.restapp.demo.model.dto.UserDTO;
import ua.restapp.demo.model.entity.User;
import ua.restapp.demo.model.mapper.UserMapper;
import ua.restapp.demo.repo.UserRepo;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Value("#{new Integer('${user.register.age.limitation}')}")
    private Integer registerAgeLimit;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        LocalDate userBirthDate = userDTO.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int userAge = Period.between(userBirthDate, LocalDate.now()).getYears();

        if (userAge < registerAgeLimit) {
            throw new IllegalArgumentException("User must be at least " + registerAgeLimit + " years old.");
        }

        User user = userMapper.userDTOToUser(userDTO);
        User saved = userRepo.save(user);
        return userMapper.userToUserDTO(saved);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO partialUserUpdate(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO deleteUser(Long id) {
        User deletedUser = userRepo.deleteUserById(id);
        return userMapper.userToUserDTO(deletedUser);
    }

    @Override
    public List<UserDTO> getUserByBirthDateRange(Integer from, Integer to) {
        return null;
    }
}
