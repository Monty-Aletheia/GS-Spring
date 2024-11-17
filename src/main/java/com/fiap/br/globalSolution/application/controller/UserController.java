package com.fiap.br.globalSolution.application.controller;

import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Users", description = "User management API")
public class UserController {

    private final UserService userService;

    // ===========================================
    // =                USERS                    =
    // ===========================================

    @Operation(summary = "Get all users", description = "Fetches a paginated list of all users.")
    @GetMapping
    public ResponseEntity<EntityModel<Page<UserResponseDTO>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<UserResponseDTO> userPage = userService.getAllUsers(pageable);

        EntityModel<Page<UserResponseDTO>> entityModel = EntityModel.of(userPage,
                linkTo(methodOn(UserController.class).getAllUsers(page, size)).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }


    @Operation(summary = "Get a user by ID", description = "Fetches a single user by their unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> getUserById(@PathVariable UUID id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(createUserEntityModel(user));
    }

    @Operation(summary = "Update a user", description = "Updates the details of an existing user.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> updateUser(@RequestBody @Valid UserRequestDTO dto, @PathVariable UUID id) {
        UserResponseDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(createUserEntityModel(updatedUser));
    }

    @Operation(summary = "Delete a user by ID", description = "Deletes a user by their unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ===========================================
    // =             HELPER METHODS              =
    // ===========================================

    private EntityModel<UserResponseDTO> createUserEntityModel(UserResponseDTO user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers(1,10)).withRel("allUsers"));
    }

}
