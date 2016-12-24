package com.eugene.domain;

import javax.persistence.*;

/**
 * Created by Ngô Mạnh Cường on 11/26/2016.
 */

/**Entity cho quyền người dùng
 * có các thuộc tính như dưới, có liên kết với user
 * có chức năng kiểm tra hợp lệ
 */
@Entity
@Table(name = "roles")
public class NgoManhCuong_05_UserRole {
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
