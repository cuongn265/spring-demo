package com.eugene.controller;

import com.eugene.domain.NgoManhCuong_05_User;
import com.eugene.repository.NgoManhCuong_05_UserRepository;
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
 * Created by Ngô Mạnh Cường on 12/24/2016.
 */

/**
 * Controller cho admin
 * Xem danh sách người dùng active, deactive
 * Khóa, mở khóa người dùng
 * Reset mật khẩu về 123456
 */
@Controller
@RequestMapping("/admin")
public class NgoManhCuong_05_AdminController {
  /*lấy bean user repository*/
  private final NgoManhCuong_05_UserRepository userRepository;

  @Autowired
  public NgoManhCuong_05_AdminController(NgoManhCuong_05_UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /* Xem trang index, gắn list các deactive user và active user */
  @RequestMapping("")
  public String index(Model model, Principal principal) {
    List<NgoManhCuong_05_User> activeUsers = userRepository.findAllByStatus(true, principal.getName(), 1L);
    List<NgoManhCuong_05_User> deActiveUsers = userRepository.findAllByStatus(false, principal.getName(), 1L);
    model.addAttribute("activeUsers", activeUsers);
    model.addAttribute("deActiveUsers", deActiveUsers);
    return "admin/index";
  }

  /*Thay đổi trạng thái người dùng (chặn, mở)*/
  @RequestMapping(value = "users/{userId}/changeState", method = RequestMethod.POST)
  String changeState(@PathVariable Integer userId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    NgoManhCuong_05_User user = userRepository.findOne(userId.longValue());
    userRepository.updateState(!user.getEnabled(), userId.longValue());
    String referer = request.getHeader("Referer");
    redirectAttributes.addFlashAttribute("message", "User state was successfully updated");
    return "redirect:" + referer;
  }

  /*Reset mật khẩu cho người dùng*/
  @RequestMapping(value = "users/{userId}/resetPassword", method = RequestMethod.POST)
  String resetPassword(@PathVariable Integer userId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    NgoManhCuong_05_User user = userRepository.findOne(userId.longValue());

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String newPassword = bCryptPasswordEncoder.encode("1");

    userRepository.updatePassword(newPassword, userId.longValue());

    String referer = request.getHeader("Referer");
    redirectAttributes.addFlashAttribute("message", "User password was successfully reset");

    return "redirect:" + referer;
  }

}
