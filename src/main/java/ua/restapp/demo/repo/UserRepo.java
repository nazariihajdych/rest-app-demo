package ua.restapp.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.restapp.demo.model.entity.User;

import java.util.Date;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByDateOfBirthBetween(Date fromDate, Date toDate);
}
