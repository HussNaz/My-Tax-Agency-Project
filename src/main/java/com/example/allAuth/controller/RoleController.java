package com.example.allAuth.controller;

import com.example.allAuth.entity.Role;
import com.example.allAuth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PutMapping("/update/{roleId}")
    public Role updateRole(@PathVariable Long roleId, @RequestParam String newName) {
        return roleService.updateRole(roleId, newName);
    }

    @GetMapping("/find")
    public Role findRoleByName(@RequestParam(name = "name") String name) {
        return roleService.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
    }

}
