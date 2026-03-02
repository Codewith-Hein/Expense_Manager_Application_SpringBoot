package com.talent.expense_manager.expense_manager.service;


import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Permission;

import java.util.List;

public interface PermissoinService {

    Permission createPermission(Permission permission);


    List<Permission> getAllPermissions();

    Permission getById(Long id);

    void deletePermission(Long id);

//    public boolean hasPermission(Account account, String entityType, String action);


}
