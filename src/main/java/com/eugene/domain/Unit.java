package com.eugene.domain;

import javax.persistence.*;

/**
 * Created by Eugene on 11/28/2016.
 */
@Entity
@Table(name = "units")
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "unit_id")
  private Long Id;
  @Column(name =  "name", nullable = false, unique = true)
  private String unitName;
  @Column(name = "summary")
  private String unitSummary;
  @Column(name = "position", nullable = false)
  private Integer unitPosition;

  @ManyToOne(optional = false)
  @JoinColumn(name = "courseId", referencedColumnName = "course_id", insertable = false, updatable = false)
  private Course course;

  public Unit() {
  }

  public Unit(String unitName, String unitSummary, Integer unitPosition, Course course) {
    this.unitName = unitName;
    this.unitSummary = unitSummary;
    this.unitPosition = unitPosition;
    this.course = course;
  }

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
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
}
