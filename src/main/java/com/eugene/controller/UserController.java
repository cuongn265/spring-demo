package com.eugene.controller;

import com.eugene.domain.User;
import com.eugene.repository.UserRepository;
import com.eugene.service.CustomUserDetails;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Eugene on 12/12/2016.
 */
@Controller
public class UserController {

  final
  UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping("/profile")
  public String profile(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    User user = userRepository.findOne(userId);
    model.addAttribute("user", user);
    return "user/profile";
  }
}
