package com.severalabs.ias.controller;

import com.severalabs.ias.dto.AssignRoleRequest;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;
import com.severalabs.ias.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(adminService.createUser(userRequest));
    }

    @PostMapping("/{id}/roles")
    public void assignRole (
            @PathVariable Long id, @RequestBody AssignRoleRequest assignRoleRequest){
        adminService.assignRole(id, assignRoleRequest.role_name());
    }

    @DeleteMapping("/{id}/roles/{roleName}")
    public void removeRole(
            @PathVariable Long id, @PathVariable String roleName
    ) {
        adminService.removeRole(id, roleName);
    }

    @PutMapping("/{id}/disable")
    public void disableUser(@PathVariable Long id) {
        adminService.disableUser(id);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers()  {
        return ResponseEntity.ok(adminService.listAllUsers());
    }

}
