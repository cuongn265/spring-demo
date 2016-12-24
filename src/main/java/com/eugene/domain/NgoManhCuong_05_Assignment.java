package com.eugene.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Ngô Mạnh Cường on 11/28/2016.
 */

/**Entity cho bài tập
 * có các thuộc tính như dưới, có liên kết nhiều 1 với bài học
 * có chức năng kiểm tra hợp lệ
 */
@Entity
@Table(name = "assignments")
public class NgoManhCuong_05_Assignment {
  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "assignment_id")
  private Long assignmentId;
  @NotEmpty(message = "Assignment name must not be empty")
  @Column(name =  "name", nullable = false)
  private String assignmentName;
  @Column(name =  "detail")
  private String assignmentDetail;
  @Future(message = "Start date must be in the future")
  @NotNull(message = "Start date must not be null")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "start_date", nullable = false)
  private Date assignmentStartDate;
  @Future(message = "End date must be in the future")
  @NotNull(message = "End date must not be null")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "end_date", nullable = false)
  private Date assignmentEndDate;

  @ManyToOne()
  @JoinColumn(name = "unitId", referencedColumnName = "unit_id")
  private NgoManhCuong_05_Unit unit;

  public NgoManhCuong_05_Assignment() {
  }

  public NgoManhCuong_05_Assignment(String assignmentName, String assignmentDetail, Date assignmentStartDate, Date assignmentEndDate, NgoManhCuong_05_Unit unit) {
    this.assignmentName = assignmentName;
    this.assignmentDetail = assignmentDetail;
    this.assignmentStartDate = assignmentStartDate;
    this.assignmentEndDate = assignmentEndDate;
    this.unit = unit;
  }

  public Long getAssignmentId() {
    return assignmentId;
  }

  public void setAssignmentId(Long assignmentId) {
    this.assignmentId = assignmentId;
  }

  public String getAssignmentName() {
    return assignmentName;
  }

  public void setAssignmentName(String assignmentName) {
    this.assignmentName = assignmentName;
  }

  public Date getAssignmentStartDate() {
    return assignmentStartDate;
  }

  public void setAssignmentStartDate(Date assignmentStartDate) {
    this.assignmentStartDate = assignmentStartDate;
  }

  public Date getAssignmentEndDate() {
    return assignmentEndDate;
  }

  public void setAssignmentEndDate(Date assignmentEndDate) {
    this.assignmentEndDate = assignmentEndDate;
  }

  public String getAssignmentDetail() {
    return assignmentDetail;
  }

  public void setAssignmentDetail(String assignmentDetail) {
    this.assignmentDetail = assignmentDetail;
  }

  public NgoManhCuong_05_Unit getUnit() {
    return unit;
  }

  public void setUnit(NgoManhCuong_05_Unit unit) {
    this.unit = unit;
  }
}
