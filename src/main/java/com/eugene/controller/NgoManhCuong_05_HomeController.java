package com.eugene.controller;

import com.eugene.domain.NgoManhCuong_05_Course;
import com.eugene.repository.NgoManhCuong_05_CourseRepository;
import com.eugene.repository.NgoManhCuong_05_UserRepository;
import com.eugene.service.NgoManhCuong_05_CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ngô Mạnh Cường on 11/26/2016.
 */
/*Controller cho màn hình chính, các chức năng khác*/
@Controller
public class NgoManhCuong_05_HomeController {
  private final NgoManhCuong_05_CourseRepository courseRepository;
  private final NgoManhCuong_05_UserRepository userRepository;

  @Autowired
  public NgoManhCuong_05_HomeController(NgoManhCuong_05_CourseRepository courseRepository, NgoManhCuong_05_UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  /*Lấy trang index cho user*/
  @RequestMapping("/")
  public String home(Model model) {
    NgoManhCuong_05_CustomUserDetails userDetails = (NgoManhCuong_05_CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    List<NgoManhCuong_05_Course> courseList = courseRepository.findCourseByUserId(userId);
    model.addAttribute("courseList", courseList);
    return "courses/index";
  }

  /*Gọi trang đăng nhập*/
  @RequestMapping("/login")
  public String login() {
    return "users/login";
  }

  /*Gọi trang 403 access denied*/
  @RequestMapping("/403")
  public String error() {
    return "application/403";
  }
}
