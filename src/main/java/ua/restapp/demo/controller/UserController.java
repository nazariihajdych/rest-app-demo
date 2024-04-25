package ua.restapp.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.restapp.demo.model.dto.UserDTO;
import ua.restapp.demo.service.UserService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserPartly(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.partialUserUpdate(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserByBirthDateRange(
            @RequestParam(name = "from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
            @RequestParam(name = "to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to){
        return ResponseEntity.ok(userService.getUserByBirthDateRange(from, to));
    }


}
