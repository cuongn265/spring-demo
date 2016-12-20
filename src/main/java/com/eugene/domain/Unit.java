package com.eugene.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Eugene on 11/28/2016.
 */
@Entity
@Table(name = "units")
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "unit_id")
  private Long unitId;
  @NotEmpty
  @Column(name =  "name", nullable = false, unique = true)
  private String unitName;
  @Column(name = "summary")
  private String unitSummary;
  @NotNull
  @Min(1)
  @Column(name = "position", nullable = false)
  private Integer unitPosition;

  @ManyToOne()
  @JoinColumn(name = "courseId", referencedColumnName = "course_id")
  private Course course;

  @Column(name = "detail", columnDefinition = "LONGTEXT")
  private String unitDetail;


  public Unit() {
  }

  public Unit(String unitName, String unitSummary, Integer unitPosition, Course course) {
    this.unitName = unitName;
    this.unitSummary = unitSummary;
    this.unitPosition = unitPosition;
    this.course = course;
  }

  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public String getUnitSummary() {
    return unitSummary;
  }

  public void setUnitSummary(String unitSummary) {
    this.unitSummary = unitSummary;
  }

  public Integer getUnitPosition() {
    return unitPosition;
  }

  public void setUnitPosition(Integer unitPosition) {
    this.unitPosition = unitPosition;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public String getUnitDetail() {
    return unitDetail;
  }

  public void setUnitDetail(String unitDetail) {
    this.unitDetail = unitDetail;
  }
}
