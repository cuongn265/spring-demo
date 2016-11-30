package com.eugene.repository;

import com.eugene.domain.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Eugene on 11/26/2016.
 */
public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
  @Query("select a.role from UserRole a, User b where b.username=?1 and a.roleId=b.userRole")
  public List<String> findRoleByUserName(String username);
  public List<UserRole> findAll();
}
