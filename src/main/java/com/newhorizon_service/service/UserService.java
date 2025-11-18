package com.newhorizon_service.service;

import com.newhorizon_service.dto.user.CreateUserDto;
import com.newhorizon_service.dto.user.GetUserDto;
import com.newhorizon_service.model.User;
import com.newhorizon_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;

    public void create(CreateUserDto dto) {
        User user = modelMapper.map(dto, User.class);
        repository.save(user);
    }

    public void update(String id, CreateUserDto dto) {
        User existingUser = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
        );

        User updatedUser = modelMapper.map(dto, User.class);

        // Atualiza os campos do usuário existente com os valores do DTO
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setCompetences(updatedUser.getCompetences());
        existingUser.setTrails(updatedUser.getTrails());

        repository.save(existingUser);
    }

    public List<GetUserDto> getAll() {
        // Busca todos usuários no banco de dados
        List<User> users = repository.findAll();

        // Converte o objeto user em GetUserDto e retorna;
        return users.stream().map(
                user -> modelMapper.map(user, GetUserDto.class)
        ).toList();
    }
    public GetUserDto getById(String id) {
        User user =  this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
        );

        return modelMapper.map(user, GetUserDto.class);
    }

    public void delete(String id) {
        User user =  this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
        );

        this.repository.delete(user);
    }
}
