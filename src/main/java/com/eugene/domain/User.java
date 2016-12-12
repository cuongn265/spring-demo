package com.eugene.domain;

/**
 * Created by Eugene on 11/26/2016.
 */

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable {
  private static final long serialVersionUID = 1;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long userId;
  @NotEmpty
  @Column(name = "username", nullable = false, unique = true)
  private String username;
  @NotEmpty
  @Column(name = "password", nullable = false)
  private String password;
  @NotEmpty
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "enabled", nullable = false)
  private int enabled = 1;
  @Column(name = "image_url")
  private String userImageUrl;
  @ManyToOne(optional = false)
  @JoinColumn(name = "roleId", referencedColumnName = "role_id", insertable = false, updatable = false)
  private UserRole userRole;
  @Column(name = "full_name")
  private String fullName;
  @Column(name = "phone_number")
  private String phoneNumber;
  @Column(name = "bio")
  private String bio;

  public User() {
  }

  public User(User user) {
    this.userId = user.userId;
    this.username = user.username;
    this.email = user.email;
    this.password = user.password;
    this.enabled = user.enabled;
    this.userImageUrl = user.userImageUrl;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(int enabled) {
    this.enabled = enabled;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public String getUserImageUrl() {
    return userImageUrl;
  }

  public void setUserImageUrl(String userImageUrl) {
    this.userImageUrl = userImageUrl;
  }
}
