package com.eugene.repository;

import com.eugene.domain.NgoManhCuong_05_Course;
import com.eugene.domain.NgoManhCuong_05_Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Ngô Mạnh Cường on 12/4/2016.
 */

/**Kho chứa các hàm cho entity bài tập
 * Các hàm có @Query là tự tạo
 * Các hàm không có @Query có thể dùng được nhờ vào kết hợp các hàm có sẵn của JPA
 */
public interface NgoManhCuong_05_UnitRepository extends JpaRepository<NgoManhCuong_05_Unit, Long> {
  /*Lấy bài học theo tên*/
  List<NgoManhCuong_05_Unit> findByUnitName(String name);

  /*Lấy Unit theo tuần và khóa học*/
  NgoManhCuong_05_Unit findFirstByUnitWeekAndCourse(Integer week, NgoManhCuong_05_Course course);
}
