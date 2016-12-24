package com.eugene.service;

/**
 * Created by Ngô Mạnh Cường on 11/26/2016.
 */

import java.util.List;

import com.eugene.domain.NgoManhCuong_05_User;
import com.eugene.repository.NgoManhCuong_05_UserRepository;
import com.eugene.repository.NgoManhCuong_05_UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class NgoManhCuong_05_CustomUserDetailsService implements UserDetailsService {
  private final NgoManhCuong_05_UserRepository userRepository;
  private final NgoManhCuong_05_UserRolesRepository userRolesRepository;

  @Autowired
  public NgoManhCuong_05_CustomUserDetailsService(NgoManhCuong_05_UserRepository userRepository, NgoManhCuong_05_UserRolesRepository userRolesRepository) {
    this.userRepository = userRepository;
    this.userRolesRepository = userRolesRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    NgoManhCuong_05_User user = userRepository.findFirstByUsername(username);
    if (null == user) {
      throw new UsernameNotFoundException("No user present with username: " + username);
    } else if (!user.getEnabled()) {
      throw new UsernameNotFoundException("User was deactive: " + username);
    } else {
      List<String> userRoles = userRolesRepository.findRoleByUserName(username);
      return new NgoManhCuong_05_CustomUserDetails(user, userRoles, user.getUserId(), user.getUserImageUrl(), user.getEnabled());
    }
  }

}
