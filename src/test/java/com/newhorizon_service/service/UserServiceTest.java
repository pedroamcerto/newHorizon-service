package com.newhorizon_service.service;

import com.newhorizon_service.dto.user.CreateUserDto;
import com.newhorizon_service.dto.user.GetUserDto;
import com.newhorizon_service.model.User;
import com.newhorizon_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private CreateUserDto createUserDto;
    private GetUserDto getUserDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setName("João Silva");
        user.setEmail("joao.silva@example.com");

        createUserDto = new CreateUserDto();
        createUserDto.setName("João Silva");
        createUserDto.setEmail("joao.silva@example.com");

        getUserDto = new GetUserDto();
        getUserDto.setId("1");
        getUserDto.setName("João Silva");
        getUserDto.setEmail("joao.silva@example.com");
    }

    @Test
    void create_DevecriarUsuarioComSucesso() {
        // Arrange
        when(modelMapper.map(createUserDto, User.class)).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);

        // Act
        userService.create(createUserDto);

        // Assert
        verify(modelMapper).map(createUserDto, User.class);
        verify(repository).save(user);
    }

    @Test
    void update_DeveAtualizarUsuarioComSucesso() {
        // Arrange
        String userId = "1";
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(createUserDto, User.class)).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);

        // Act
        userService.update(userId, createUserDto);

        // Assert
        verify(repository).findById(userId);
        verify(modelMapper).map(createUserDto, User.class);
        verify(repository).save(user);
    }

    @Test
    void update_DeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        String userId = "999";
        when(repository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.update(userId, createUserDto));
        verify(repository).findById(userId);
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void getAll_DeveRetornarListaDeUsuarios() {
        // Arrange
        List<User> users = Arrays.asList(user);
        when(repository.findAll()).thenReturn(users);
        when(modelMapper.map(user, GetUserDto.class)).thenReturn(getUserDto);

        // Act
        List<GetUserDto> result = userService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getUserDto.getId(), result.get(0).getId());
        verify(repository).findAll();
    }

    @Test
    void getById_DeveRetornarUsuarioPorId() {
        // Arrange
        String userId = "1";
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, GetUserDto.class)).thenReturn(getUserDto);

        // Act
        GetUserDto result = userService.getById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(getUserDto.getId(), result.getId());
        assertEquals(getUserDto.getName(), result.getName());
        verify(repository).findById(userId);
    }

    @Test
    void getById_DeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        String userId = "999";
        when(repository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.getById(userId));
        verify(repository).findById(userId);
    }

    @Test
    void delete_DeveDeletarUsuarioComSucesso() {
        // Arrange
        String userId = "1";
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(repository).delete(user);

        // Act
        userService.delete(userId);

        // Assert
        verify(repository).findById(userId);
        verify(repository).delete(user);
    }

    @Test
    void delete_DeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        String userId = "999";
        when(repository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.delete(userId));
        verify(repository).findById(userId);
        verify(repository, never()).delete(any(User.class));
    }
}
