package ua.restapp.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.restapp.demo.model.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User deleteUserById(Long id);
}
