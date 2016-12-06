package com.eugene.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.eugene.domain.Course;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UserRepository;
import com.eugene.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.*;
import java.util.List;

/**
 * Created by Eugene on 11/26/2016.
 */
@Controller
public class HomeController {

  private final CourseRepository courseRepository;
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

  @RequestMapping("/test_editor")
  public String testEditor() {
    return "test_editor";
  }

  @RequestMapping(value = "/save_editor", method = RequestMethod.POST)
  public String saveEditor(@RequestParam("editor1") String editor1){
    System.out.println("EDITORRRRRRRRRR:" + editor1);
    return  "redirect:/test_editor";
  }
}
