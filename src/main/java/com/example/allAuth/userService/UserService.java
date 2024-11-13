package com.example.allAuth.userService;

import com.example.allAuth.DTOs.UserDTO;
import com.example.allAuth.Repositories.RoleRepository;
import com.example.allAuth.Repositories.UserRepository;
import com.example.allAuth.entity.ERole;
import com.example.allAuth.entity.Role;
import com.example.allAuth.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found with email: " + email));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));
    }

    public void updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id: Hence Can't be updated " + userId));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("User Not Found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public ResponseEntity<?> updateUserRole(Long userId, Set<Role> newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));
        user.getRoles().clear();

        for (Role role : newRole) {
            Optional<Role> roleOptional = roleRepository.findByName(role.getName());
            if (roleOptional.isEmpty()) {
                throw new RuntimeException("Role Not Found with name: " + role.getName());
            }
            user.getRoles().add(roleOptional.get());
        }

        userRepository.save(user);
        return ResponseEntity.ok("User role updated successfully");
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        // Validate and check if user already exists
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with the same username already exists");
        }

        // Create new user instance
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Set roles
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDTO.getRoles()) {
            Optional<Role> roleOptional = roleRepository.findByName(roleName);
            if (roleOptional.isEmpty()) {
                throw new RuntimeException("Role not found: " + roleName);
            }
            roles.add(roleOptional.get());
        }
        user.setRoles(roles);

        // Save the new user

        return userRepository.save(user);
    }
}
