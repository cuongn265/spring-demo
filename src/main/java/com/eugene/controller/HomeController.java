package com.eugene.controller;

import com.eugene.domain.Course;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UserRepository;
import com.eugene.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by Eugene on 11/26/2016.
 */
@Controller
public class HomeController {

  private final CourseRepository courseRepository;
  @Autowired
  private final UserRepository userRepository;

  @Autowired
  public HomeController(CourseRepository courseRepository, UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @RequestMapping("/")
  public String home(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    List<Course> courseList = courseRepository.findCourseByUserId(userId);
    model.addAttribute("courseList", courseList);
    return "home";
  }



  @RequestMapping("/course/new")
  public String newCourse(Model model){
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    Course course = new Course();
    course.setUser(userDetails);
    model.addAttribute("course", course);
    return "course_form";
  }



  @RequestMapping(value = "/course", method = RequestMethod.POST)
  public String saveCourse(Course course){
    courseRepository.save(course);
    return "redirect:/courses/" + course.getCourseId();
  }

  @RequestMapping("/courses/{courseId}")
  public String showCourse(@PathVariable Integer courseId, Model model){
    Course course = courseRepository.findOne(courseId.longValue());
    model.addAttribute("course", course);
    return "course";
  }

  @RequestMapping(value = "/courses/{courseId}/remove", method = RequestMethod.DELETE)
  public String removeCourse(@PathVariable Integer courseId) {
    courseRepository.delete(courseId.longValue());
    return "redirect:/";
  }

  @RequestMapping("/hello")
  public String hello() {
    return "hello";
  }

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @RequestMapping("/403")
  public String error() {
    return "403";
  }
}
