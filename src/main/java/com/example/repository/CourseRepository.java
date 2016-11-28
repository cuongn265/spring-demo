package com.example.repository;

import com.example.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Eugene on 11/28/2016.
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}
