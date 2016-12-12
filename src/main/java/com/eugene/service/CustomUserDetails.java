package com.eugene.service;

import java.util.Collection;
import java.util.List;

import com.eugene.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;


public class CustomUserDetails extends User implements UserDetails {

  private static final long serialVersionUID = 1L;
  private List<String> userRoles;
  private Long userId;
  private String userImageUrl;

  public CustomUserDetails(User user, List<String> userRoles, Long userId, String userImageUrl) {
    super(user);
    this.userRoles = userRoles;
    this.userId = userId;
    this.userImageUrl = userImageUrl;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
    return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


  @Override
  public String getUsername() {
    return super.getUsername();
  }

  @Override
  public Long getUserId() {
    return super.getUserId();
  }

  @Override
  public String getUserImageUrl() {
    return super.getUserImageUrl();
  }
}
