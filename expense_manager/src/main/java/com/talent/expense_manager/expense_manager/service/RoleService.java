package com.talent.expense_manager.expense_manager.service;

import com.talent.expense_manager.expense_manager.model.Role;

import java.util.List;

public interface RoleService {


    Role createRole(Role role);

    List<Role> getAllRoles();

    Role getById(Long id);

    void deleteRole(Long id);

    Role addPermissionToRole(Long roleId, Long permissionId);
}
