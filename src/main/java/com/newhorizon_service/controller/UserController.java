package com.newhorizon_service.controller;

import com.newhorizon_service.dto.user.CreateUserDto;
import com.newhorizon_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@Valid @RequestBody CreateUserDto dto) {
        userService.create(dto);
    }

    @GetMapping
    public void getAllUsers() {
        userService.getAll();
    }

    @GetMapping("/{id}")
    public void getUserById(@PathVariable String id) {
        userService.getById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @Valid @RequestBody CreateUserDto dto) {
        userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
