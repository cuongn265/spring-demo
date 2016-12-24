package com.eugene.controller;

import com.eugene.domain.User;
import com.eugene.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by Eugene on 12/24/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
  private final UserRepository userRepository;

  @Autowired
  public AdminController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping("")
  public String index(Model model, Principal principal) {
    List<User> activeUsers = userRepository.findAllByEnabledAndUsernameNot(true, principal.getName());
    List<User> deActiveUsers = userRepository.findAllByEnabledAndUsernameNot(false, principal.getName());
    model.addAttribute("activeUsers", activeUsers);
    model.addAttribute("deActiveUsers", deActiveUsers);
    return "admin/index";
  }

  @RequestMapping(value = "users/{userId}/changeState", method = RequestMethod.POST)
  String changeState(@PathVariable Integer userId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    User user = userRepository.findOne(userId.longValue());
    userRepository.updateState(!user.getEnabled(), userId.longValue());
    String referer = request.getHeader("Referer");
    redirectAttributes.addFlashAttribute("message", "User state was successfully updated");
    return "redirect:" + referer;
  }

  @RequestMapping(value = "users/{userId}/resetPassword", method = RequestMethod.POST)
  String resetPassword(@PathVariable Integer userId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    User user = userRepository.findOne(userId.longValue());

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String newPassword = bCryptPasswordEncoder.encode("1");

    userRepository.updatePassword(newPassword, userId.longValue());

    String referer = request.getHeader("Referer");
    redirectAttributes.addFlashAttribute("message", "User password was successfully reset");

    return "redirect:" + referer;
  }

}
