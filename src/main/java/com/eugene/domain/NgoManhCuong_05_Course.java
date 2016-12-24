package com.eugene.domain;

import com.eugene.group.NgoManhCuong_05_CreateCourse;
import com.eugene.validator.NgoManhCuong_05_NotExistingCourseName;
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
@Table(name = "courses")
public class NgoManhCuong_05_Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id")
  private Long courseId;

  @Column(name = "name", nullable = false, unique = true)
  @NgoManhCuong_05_NotExistingCourseName(groups = NgoManhCuong_05_CreateCourse.class)
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
  private NgoManhCuong_05_User user;

  @Column(name = "outline", columnDefinition = "LONGTEXT")
  private String courseOutline;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
  private Set<NgoManhCuong_05_Unit> unitList = new HashSet<>();

  public NgoManhCuong_05_Course() {
  }

  public NgoManhCuong_05_Course(String courseName, String courseSummary, Integer courseWeekCount, NgoManhCuong_05_User user, Set<NgoManhCuong_05_Unit> unitList, String courseOutline, String courseImageUrl) {
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

  public NgoManhCuong_05_User getUser() {
    return user;
  }

  public void setUser(NgoManhCuong_05_User user) {
    this.user = user;
  }

  public Set<NgoManhCuong_05_Unit> getUnitList() {
    return unitList;
  }

  public void setUnitList(Set<NgoManhCuong_05_Unit> unitList) {
    this.unitList = unitList;
  }

  public String getCourseOutline() {
    return courseOutline;
  }

  public void setCourseOutline(String courseOutline) {
    this.courseOutline = courseOutline;
  }
}
