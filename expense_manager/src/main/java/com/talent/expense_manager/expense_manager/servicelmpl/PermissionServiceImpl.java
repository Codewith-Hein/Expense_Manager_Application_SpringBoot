package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Permission;
import com.talent.expense_manager.expense_manager.repository.PermissionRepository;
import com.talent.expense_manager.expense_manager.service.PermissoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissoinService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }



    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Permission not found"));
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

//    @Override
//    public boolean hasPermission(Account account, String entityType, String action) {
//        return account.getRole().getPermissions().stream()
//                .anyMatch(p ->
//                        p.getEntityType().equalsIgnoreCase(entityType) &&
//                                p.getAction().equalsIgnoreCase(action)
//                );
//    }
}
