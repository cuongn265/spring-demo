package com.eugene.validator;

import com.eugene.domain.NgoManhCuong_05_User;
import com.eugene.service.NgoManhCuong_05_CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Ngô Mạnh Cường on 12/18/2016.
 */

/**
 * Validator cho mật khẩu khi đổi mật khẩu
 */
@Component
public class NgoManhCuong_05_PasswordValidator implements Validator {

  /*Khởi tạo*/
  public boolean supports(Class<?> clazz) {
    return NgoManhCuong_05_User.class.isAssignableFrom(clazz);
  }

  /*Kiểm tra*/
  public void validate(Object target, Errors errors) {
    NgoManhCuong_05_User user = (NgoManhCuong_05_User) target;
    String newPassword = user.getNewPassword();
    String confPassword = user.getRetypePassword();
    String oldPassword = user.getOldPassword();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    NgoManhCuong_05_CustomUserDetails userDetails = (NgoManhCuong_05_CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /*Mật khẩu mới và nhập lại không khớp*/
    if (!newPassword.equals(confPassword)) {
      errors.rejectValue("newPassword", "user.password.missMatch");
    }

    /*Mật khẩu cũ không đúng*/
    if (!passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
      errors.rejectValue("oldPassword", "user.password.incorrect");
    }
  }
}
