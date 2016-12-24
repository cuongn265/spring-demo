package com.eugene.repository;

import com.eugene.domain.NgoManhCuong_05_User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Ngô Mạnh Cường on 11/26/2016.
 */

/**Kho chứa các hàm cho entity người dùng
 * Các hàm có @Query là tự tạo
 * Các hàm không có @Query có thể dùng được nhờ vào kết hợp các hàm có sẵn của JPA
 */

public interface NgoManhCuong_05_UserRepository extends CrudRepository<NgoManhCuong_05_User, Long>{
  NgoManhCuong_05_User findFirstByUsername(String username);

  /*Sửa thông tin người dùng trừ mật khẩu*/
  @Transactional
  @Modifying
  @Query("update NgoManhCuong_05_User u set u.email = ?1, u.fullName = ?2, u.phoneNumber = ?3, u.bio= ?4, u.userImageUrl= ?5 where u.userId = ?6")
  void setUserInfoById(String email, String fullName, String phoneNumber, String bio, String imageUrl, Long userId);

  /*Sửa mật khẩu*/
  @Transactional
  @Modifying
  @Query("update NgoManhCuong_05_User u set u.password = ?1 where u.userId = ?2")
  void changeUserPassword(String password, Long userId);

  /*Tìm danh sách người dùng theo trạng thái*/
  @Query("select u from NgoManhCuong_05_User u where u.enabled=?1 and u.username<>?2 and u.userRole.roleId<>?3")
  List<NgoManhCuong_05_User> findAllByStatus(boolean isEnable, String username, Long roleId);

  /*Cập nhật trạng thái người dùng*/
  @Transactional
  @Modifying
  @Query("update NgoManhCuong_05_User u set u.enabled = ?1 where u.userId = ?2")
  void updateState(Boolean enabled, Long userId);

  /*Reset mật khẩu người dùng*/
  @Transactional
  @Modifying
  @Query("update NgoManhCuong_05_User u set u.password = ?1 where u.userId = ?2")
  void updatePassword(String password, Long userId);
}
