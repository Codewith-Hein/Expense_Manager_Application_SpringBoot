package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.model.Permission;
import com.talent.expense_manager.expense_manager.service.PermissoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissoinService permissionService;

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }


    @GetMapping
    public List<Permission> getAll() {
        return permissionService.getAllPermissions();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        permissionService.deletePermission(id);
    }


}
