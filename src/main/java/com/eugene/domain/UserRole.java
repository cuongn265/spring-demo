package com.eugene.domain;

import javax.persistence.*;

/**
 * Created by Eugene on 11/26/2016.
 */
@Entity
@Table(name = "roles")
public class UserRole {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id")
  private Long roleId;

  @Column(name = "role", nullable = false, unique = true)
  private String role = "ROLE_USER";

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
