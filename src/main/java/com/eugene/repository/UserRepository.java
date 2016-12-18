package com.eugene.repository;

import com.eugene.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Eugene on 11/26/2016.
 */
public interface UserRepository extends CrudRepository<User, Long>{
  public User findFirstByUsername(String username);

  @Transactional
  @Modifying
  @Query("update User u set u.email = ?1, u.fullName = ?2, u.phoneNumber = ?3, u.bio= ?4 where u.userId = ?5")
  void setUserInfoById(String email, String fullName, String phoneNumber, String bio ,Long userId);
}
