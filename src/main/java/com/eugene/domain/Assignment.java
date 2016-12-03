package com.eugene.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Eugene on 11/28/2016.
 */
@Entity
@Table(name = "assignments")
public class Assignment {
  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "assignment_id")
  private Long assignmentId;
  @NotEmpty
  @Column(name =  "name", nullable = false, unique = true)
  private String assignmentName;
  @NotEmpty
  @Column(name = "start_date", nullable = false)
  private Date assignmentStartDate;
  @NotEmpty
  @Column(name = "end_date", nullable = false)
  private Date assignmentEndDate;

  @ManyToOne(optional = false)
  @JoinColumn(name = "unitId", referencedColumnName = "unit_id", insertable = false, updatable = false)
  private Unit unit;

  public Assignment() {
  }

  public Assignment(String assignmentName, Date assignmentStartDate, Date assignmentEndDate, Unit unit) {
    this.assignmentName = assignmentName;
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

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }
}
