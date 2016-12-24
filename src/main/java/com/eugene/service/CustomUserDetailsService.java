package com.eugene.service;

/**
 * Created by Eugene on 11/26/2016.
 */

import java.util.List;

import com.eugene.domain.User;
import com.eugene.repository.UserRepository;
import com.eugene.repository.UserRolesRepository;
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
    } else if (!user.getEnabled()) {
      throw new UsernameNotFoundException("User was deactive: " + username);
    } else {
      List<String> userRoles = userRolesRepository.findRoleByUserName(username);
      return new CustomUserDetails(user, userRoles, user.getUserId(), user.getUserImageUrl(), user.getEnabled());
    }
  }

}
