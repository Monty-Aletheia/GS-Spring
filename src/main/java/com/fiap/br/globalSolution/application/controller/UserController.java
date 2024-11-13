package com.fiap.br.globalSolution.application.controller;

import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management API")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Fetches a list of all users.")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<EntityModel<UserResponseDTO>> users = userService.getAllUsers().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).getAllUsers()).withRel("allUsers")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get a user by ID", description = "Fetches a single user by their unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        UserResponseDTO user = userService.getUserById(id);
        EntityModel<UserResponseDTO> userModel = EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("allUsers"));

        return ResponseEntity.ok(userModel);
    }

    @Operation(summary = "Update a user", description = "Updates the details of an existing user.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequestDTO dto, @PathVariable UUID id) {
        UserResponseDTO updatedUser = userService.updateUser(id, dto);
        EntityModel<UserResponseDTO> userModel = EntityModel.of(updatedUser,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("allUsers"));

        return ResponseEntity.ok(userModel);
    }

    @Operation(summary = "Delete a user by ID", description = "Deletes a user by their unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
