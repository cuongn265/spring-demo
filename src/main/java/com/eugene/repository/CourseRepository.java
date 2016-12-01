package com.eugene.repository;

import com.eugene.domain.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eugene on 11/28/2016.
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
  @Query("select a from Course a where a.user.userId=?1")
  public List<Course> findCourseByUserId(Long userId);
}
