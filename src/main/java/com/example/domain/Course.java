package com.example.domain;

import javax.persistence.*;

/**
 * Created by Eugene on 11/28/2016.
 */
@Entity
@Table(name = "courses")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "course_id")
  private Long courseId;
  @Column(name = "name", nullable = false, unique = true)
  private String courseName;
  @Column(name = "summary")
  private String courseSummary;
  @Column(name = "week_count", nullable = false)
  private Integer courseWeekCount;
  @ManyToOne(optional = false)
  @JoinColumn(name = "userId", referencedColumnName = "user_id", insertable = false, updatable = false)
  private User user;

  public Course() {
  }

  public Course(String courseName, String courseSummary, Integer courseWeekCount, User user) {
    this.courseName = courseName;
    this.courseSummary = courseSummary;
    this.courseWeekCount = courseWeekCount;
//    this.user = user;
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

//  public User getUser() {
//    return user;
//  }
//
//  public void setUser(User user) {
//    this.user = user;
//  }
}
