package com.eugene.repository;

import com.eugene.domain.NgoManhCuong_05_UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Ngô Mạnh Cường on 11/26/2016.
 */
public interface NgoManhCuong_05_UserRolesRepository extends CrudRepository<NgoManhCuong_05_UserRole, Long> {
  /*Tìm quyền theo username*/
  @Query("select a.role from NgoManhCuong_05_UserRole a, NgoManhCuong_05_User b where b.username=?1 and a.roleId=b.userRole")
  List<String> findRoleByUserName(String username);
  /*Tìm tất cả các quyền*/
  List<NgoManhCuong_05_UserRole> findAll();
}
