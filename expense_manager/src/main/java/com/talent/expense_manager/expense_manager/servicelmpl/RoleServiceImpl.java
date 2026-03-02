package com.talent.expense_manager.expense_manager.servicelmpl;


import com.talent.expense_manager.expense_manager.model.Permission;
import com.talent.expense_manager.expense_manager.model.Role;
import com.talent.expense_manager.expense_manager.repository.PermissionRepository;
import com.talent.expense_manager.expense_manager.repository.RoleRepository;
import com.talent.expense_manager.expense_manager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found"));
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role addPermissionToRole(Long roleId, Long permissionId) {
        Role role = getById(roleId);

        Permission permission = permissionRepository
                .findById(permissionId)
                .orElseThrow(() ->
                        new RuntimeException("Permission not found"));

        role.getPermissions().add(permission);

        return roleRepository.save(role);
    }
}
