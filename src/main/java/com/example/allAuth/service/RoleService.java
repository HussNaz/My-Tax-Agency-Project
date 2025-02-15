package com.example.allAuth.service;

import com.example.allAuth.Repositories.RoleRepository;
import com.example.allAuth.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    // Save a new role
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Get role by name
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    // Update existing role
    public Role updateRole(Long roleId, String newName) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) {
            throw new RuntimeException("Role not found");
        }
        Role role = roleOptional.get();
        role.setName(newName);
        return roleRepository.save(role);
    }

    public List<Role> findAllRoles() {
    	return roleRepository.findAll();
    }
}
