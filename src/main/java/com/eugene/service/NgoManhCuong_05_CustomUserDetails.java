package com.eugene.service;

import java.util.Collection;
import java.util.List;

import com.eugene.domain.NgoManhCuong_05_User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * Custom UserDetail có sẵn của Spring
 * Thêm 1 số thuộc tính cần thiết để truy xuất
 * getAuthorities và isEnabled là các hàm cần thiết trong project này
 */
public class NgoManhCuong_05_CustomUserDetails extends NgoManhCuong_05_User implements UserDetails {

  private static final long serialVersionUID = 1L;
  private List<String> userRoles;
  private Long userId;
  private String userImageUrl;
  private boolean enabled;

  NgoManhCuong_05_CustomUserDetails(NgoManhCuong_05_User user, List<String> userRoles, Long userId, String userImageUrl, boolean enabled) {
    super(user);
    this.userRoles = userRoles;
    this.userId = userId;
    this.userImageUrl = userImageUrl;
    this.enabled = enabled;
  }

  /*Lấy Collection các role của người dùng*/
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

  /*Lấy trạng thái (có bị ban hay không)*/
  @Override
  public boolean isEnabled() {
    return super.getEnabled();
  }

  /*Lấy username*/
  @Override
  public String getUsername() {
    return super.getUsername();
  }

  /*Lấy userId*/
  @Override
  public Long getUserId() {
    return super.getUserId();
  }

  @Override
  public String getUserImageUrl() {
    return super.getUserImageUrl();
  }
}
