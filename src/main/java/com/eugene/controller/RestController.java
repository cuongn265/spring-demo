package com.eugene.controller;

import com.eugene.domain.Course;
import com.eugene.domain.User;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UserRolesRepository;
import com.eugene.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Eugene on 11/28/2016.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

  private final CourseRepository courseRepository;

  @Autowired
  public RestController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @RequestMapping(value = "/courses", headers = "Accept=application/json", method = RequestMethod.GET)
  List<Course> readCourses() {
    CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Integer userId = user.getUserId();
    return courseRepository.findCourseByUsernameXXX(userId);
  }
}
