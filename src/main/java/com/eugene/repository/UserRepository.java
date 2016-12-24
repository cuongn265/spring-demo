package com.eugene.repository;

import com.eugene.domain.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Eugene on 11/26/2016.
 */
public interface UserRepository extends CrudRepository<User, Long>{
  public User findFirstByUsername(String username);

  @Transactional
  @Modifying
  @Query("update User u set u.email = ?1, u.fullName = ?2, u.phoneNumber = ?3, u.bio= ?4, u.userImageUrl= ?5 where u.userId = ?6")
  void setUserInfoById(String email, String fullName, String phoneNumber, String bio, String imageUrl, Long userId);

  @Transactional
  @Modifying
  @Query("update User u set u.password = ?1 where u.userId = ?2")
  void changeUserPassword(String password, Long userId);

  List<User> findAllByEnabledAndUsernameNot(boolean isEnable, String username);

  @Transactional
  @Modifying
  @Query("update User u set u.enabled = ?1 where u.userId = ?2")
  void updateState(Boolean enabled, Long userId);

  @Transactional
  @Modifying
  @Query("update User u set u.password = ?1 where u.userId = ?2")
  void updatePassword(String password, Long userId);
}
