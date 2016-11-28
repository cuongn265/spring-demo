package com.example.controller;

import com.example.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Eugene on 11/26/2016.
 */
@Controller
public class HomeController {

  private final UserRolesRepository userRolesRepository;

  @Autowired
  public HomeController(UserRolesRepository userRolesRepository) {
    this.userRolesRepository = userRolesRepository;
  }

  @RequestMapping("/")
  public String home(Model model) {
    model.addAttribute("roles", userRolesRepository.findAll());
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

//  @RequestMapping(value="/logout", method = RequestMethod.POST)
//  public String deleteUser () {
//    return "redirect:/";
//  }
}
