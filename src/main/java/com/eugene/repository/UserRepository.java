package com.eugene.repository;

import com.eugene.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Eugene on 11/26/2016.
 */
public interface UserRepository extends CrudRepository<User, Long>{
  public User findFirstByUsername(String username);
}
