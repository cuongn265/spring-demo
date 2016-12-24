package com.eugene.repository;

import com.eugene.domain.NgoManhCuong_05_Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ngô Mạnh Cường on 11/28/2016.
 */

/**Kho chứa các hàm cho entity khóa học
 * Các hàm có @Query là tự tạo
 * Các hàm không có @Query có thể dùng được nhờ vào kết hợp các hàm có sẵn của JPA
 */
@Repository
public interface NgoManhCuong_05_CourseRepository extends CrudRepository<NgoManhCuong_05_Course, Long> {
  /*Lấy bài học theo id người dùng*/
  @Query("select a from NgoManhCuong_05_Course a where a.user.userId=?1")
  List<NgoManhCuong_05_Course> findCourseByUserId(Long userId);

  /*Lấy số lượng bài học theo tên*/
  @Query("select count(a) from NgoManhCuong_05_Course a where a.courseName=?1")
  Integer findCourseByCourseName(String courseName);
}
