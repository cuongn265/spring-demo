package com.eugene.domain;

import com.eugene.inter.CreateCourse;
import com.eugene.validator.NotExistingCourseName;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Eugene on 11/28/2016.
 */
@Entity
@Table(name = "courses")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id")
  private Long courseId;

  @Column(name = "name", nullable = false, unique = true)
  @NotExistingCourseName(groups = CreateCourse.class)
  @NotEmpty(message = "Course name should not be empty")
  private String courseName;

  @Column(name = "summary")
  private String courseSummary;

  @Min(value = 1, message = "Week count should not be less than 1")
  @NotNull(message = "Week count should not be null")
  @Column(name = "week_count", nullable = false)
  private Integer courseWeekCount = 1;
  @Column(name = "image_url")
  private String courseImageUrl;
  @ManyToOne(optional = false)
  @JoinColumn(name = "userId", referencedColumnName = "user_id")
  private User user;

  @Column(name = "outline", columnDefinition = "LONGTEXT")
  private String courseOutline;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
  private Set<Unit> unitList = new HashSet<>();

  public Course() {
  }

  public Course(String courseName, String courseSummary, Integer courseWeekCount, User user, Set<Unit> unitList, String courseOutline, String courseImageUrl) {
    this.courseName = courseName;
    this.courseSummary = courseSummary;
    this.courseWeekCount = courseWeekCount;
    this.user = user;
    this.unitList = unitList;
    this.courseOutline = courseOutline;
    this.courseImageUrl = courseImageUrl;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getCourseSummary() {
    return courseSummary;
  }

  public void setCourseSummary(String courseSummary) {
    this.courseSummary = courseSummary;
  }

  public Integer getCourseWeekCount() {
    return courseWeekCount;
  }

  public void setCourseWeekCount(Integer courseWeekCount) {
    this.courseWeekCount = courseWeekCount;
  }

  public String getCourseImageUrl() {
    return courseImageUrl;
  }

  public void setCourseImageUrl(String courseImageUrl) {
    this.courseImageUrl = courseImageUrl;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Set<Unit> getUnitList() {
    return unitList;
  }

  public void setUnitList(Set<Unit> unitList) {
    this.unitList = unitList;
  }

  public String getCourseOutline() {
    return courseOutline;
  }

  public void setCourseOutline(String courseOutline) {
    this.courseOutline = courseOutline;
  }
}
