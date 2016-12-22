package com.eugene.repository;

import com.eugene.domain.Course;
import com.eugene.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Eugene on 12/4/2016.
 */
public interface UnitRepository extends JpaRepository<Unit, Long> {
  List<Unit> findByUnitName(String name);

  Unit findFirstByUnitWeekAndCourse(Integer week, Course course);
}
