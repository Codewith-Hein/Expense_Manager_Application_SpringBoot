package com.talent.expense_manager.expense_manager.repository;

import com.talent.expense_manager.expense_manager.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
