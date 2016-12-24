package com.eugene.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ngô Mạnh Cường on 11/28/2016.
 */

/**Entity cho khóa học
 * có các thuộc tính như dưới, có liên kết nhiều 1 với user
 * có liên kết 1 nhiều với bài học
 * có chức năng kiểm tra hợp lệ
 */

@Entity
@Table(name = "units")
public class NgoManhCuong_05_Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "unit_id")
  private Long unitId;
  @NotEmpty
  @Column(name =  "name", nullable = false)
  private String unitName;
  @Column(name = "summary")
  private String unitSummary;
  @NotNull
  @Min(1)
  @Column(name = "week", nullable = false)
  private Integer unitWeek;

  @ManyToOne()
  @JoinColumn(name = "courseId", referencedColumnName = "course_id")
  private NgoManhCuong_05_Course course;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit", cascade = CascadeType.ALL)
  private Set<NgoManhCuong_05_Assignment> assignments = new HashSet<>();

  @Column(name = "detail", columnDefinition = "LONGTEXT")
  private String unitDetail;


  public NgoManhCuong_05_Unit() {
  }

  public NgoManhCuong_05_Unit(String unitName, String unitSummary, Integer unitWeek, NgoManhCuong_05_Course course, Set<NgoManhCuong_05_Assignment> assignments) {
    this.unitName = unitName;
    this.unitSummary = unitSummary;
    this.unitWeek = unitWeek;
    this.course = course;
    this.assignments = assignments;
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

  public Integer getUnitWeek() {
    return unitWeek;
  }

  public void setUnitWeek(Integer unitWeek) {
    this.unitWeek = unitWeek;
  }

  public NgoManhCuong_05_Course getCourse() {
    return course;
  }

  public void setCourse(NgoManhCuong_05_Course course) {
    this.course = course;
  }

  public String getUnitDetail() {
    return unitDetail;
  }

  public void setUnitDetail(String unitDetail) {
    this.unitDetail = unitDetail;
  }

  public Set<NgoManhCuong_05_Assignment> getAssignments() {
    return assignments;
  }

  public void setAssignments(Set<NgoManhCuong_05_Assignment> assignments) {
    this.assignments = assignments;
  }
}
