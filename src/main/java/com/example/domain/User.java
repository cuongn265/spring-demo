package com.example.domain;

/**
 * Created by Eugene on 11/26/2016.
 */

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable {
  private static final long serialVersionUID = 1;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Integer userId;
  @Column(name = "username", nullable = false, unique = true)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "enabled", nullable = false)
  private int enabled = 1;
  @Column(name = "image_url")
  private String image_url;

  @ManyToOne(optional = false)
  @JoinColumn(name = "roleId", referencedColumnName = "role_id", insertable = false, updatable = false)
  private UserRole userRole;

  public User() {
  }

  public User(User user) {
    this.userId = user.userId;
    this.username = user.username;
    this.email = user.email;
    this.password = user.password;
    this.enabled = user.enabled;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
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

  public String getImage_url() {
    return image_url;
  }

  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }
}
