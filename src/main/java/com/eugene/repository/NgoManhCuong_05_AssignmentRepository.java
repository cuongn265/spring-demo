package com.eugene.repository;

import com.eugene.domain.NgoManhCuong_05_Assignment;
import com.eugene.domain.NgoManhCuong_05_Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Ngô Mạnh Cường on 12/23/2016.
 */

/**Kho chứa các hàm cho entity bài tập
 * Các hàm có @Query là tự tạo
 * Các hàm không có @Query có thể dùng được nhờ vào kết hợp các hàm có sẵn của JPA
 */

public interface NgoManhCuong_05_AssignmentRepository extends JpaRepository<NgoManhCuong_05_Assignment, Long> {
  /*Lấy tất cả bài tập theo bài học*/
  List<NgoManhCuong_05_Assignment> findAllByUnit(NgoManhCuong_05_Unit unit);
}
