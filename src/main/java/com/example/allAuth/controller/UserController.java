package com.example.allAuth.controller;



import com.example.allAuth.DTOs.UserDTO;
import com.example.allAuth.entity.Role;
import com.example.allAuth.entity.User;
import com.example.allAuth.response.MessageResponse;
import com.example.allAuth.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/userController")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserByEmail/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','ROLE_USER')")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long userId) {
        try {
            userService.updateUser(userId, user);
            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be updated " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be deleted " + e.getMessage()));
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<?> updateUserRoles(@PathVariable Long userId, @RequestBody Set<Role> roles) {
        return userService.updateUserRole(userId, roles);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }
}
