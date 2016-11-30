package com.eugene.controller;

import com.eugene.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Eugene on 11/26/2016.
 */
@Controller
public class HomeController {

  private final CourseRepository courseRepository;

  @Autowired
  public HomeController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @RequestMapping("/")
  public String home(Model model) {
    model.addAttribute("courses", courseRepository.findAll());
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
}
