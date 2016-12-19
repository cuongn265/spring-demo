package com.eugene.validator;

import com.eugene.domain.User;
import com.eugene.service.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Eugene on 12/18/2016.
 */
@Component
public class PasswordValidator implements Validator {

  public boolean supports(Class<?> clazz) {
    return User.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    User user = (User) target;
    String newPassword = user.getNewPassword();
    String confPassword = user.getRetypePassword();
    String oldPassword = user.getOldPassword();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!newPassword.equals(confPassword)) {
      errors.rejectValue("newPassword", "user.password.missMatch");
    }

    if (!passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
      errors.rejectValue("oldPassword", "user.password.incorrect");
    }
  }
}
