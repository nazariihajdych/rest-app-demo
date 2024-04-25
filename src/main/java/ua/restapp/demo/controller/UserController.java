package ua.restapp.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.restapp.demo.model.dto.UserDTO;
import ua.restapp.demo.service.UserService;

import java.util.ArrayList;
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

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUserPartly(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserByBirthDateRange(
            @RequestParam(name = "from") Integer from,
            @RequestParam(name = "to") Integer to){

        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Hello");
        userDTOS.add(userDTO);
        return ResponseEntity.ok(userDTOS);
    }


}
