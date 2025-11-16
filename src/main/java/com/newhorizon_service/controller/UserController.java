package com.newhorizon_service.controller;

import com.newhorizon_service.dto.user.CreateUserDto;
import com.newhorizon_service.dto.user.GetUserDto;
import com.newhorizon_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDto dto) {
        userService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @Valid @RequestBody CreateUserDto dto) {
        userService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
