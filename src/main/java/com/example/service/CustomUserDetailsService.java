package com.example.service;

/**
 * Created by Eugene on 11/26/2016.
 */

import java.util.List;

import com.example.domain.User;
import com.example.repository.UserRepository;
import com.example.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserRolesRepository userRolesRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
    this.userRepository = userRepository;
    this.userRolesRepository = userRolesRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findFirstByUsername(username);
    if (null == user) {
      throw new UsernameNotFoundException("No user present with username: " + username);
    } else {
      List<String> userRoles = userRolesRepository.findRoleByUserName(username);
      return new CustomUserDetails(user, userRoles);
    }
  }

}
