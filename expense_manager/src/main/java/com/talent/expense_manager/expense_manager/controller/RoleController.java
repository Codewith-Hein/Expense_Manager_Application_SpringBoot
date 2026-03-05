package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.model.Role;
import com.talent.expense_manager.expense_manager.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name="Access Control",description = "More descriptive than \"Role,\" focusing on permissions and security.")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAllRoles();
    }

    @PostMapping("/{roleId}/permissions/{permissionId}")
    public Role addPermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {

        return roleService
                .addPermissionToRole(roleId, permissionId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.deleteRole(id);
    }


}
