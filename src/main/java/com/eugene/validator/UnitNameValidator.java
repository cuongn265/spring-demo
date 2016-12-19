package com.eugene.validator;

import com.eugene.domain.Unit;
import com.eugene.domain.User;
import com.eugene.repository.UnitRepository;
import com.eugene.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Eugene on 12/19/2016.
 */
@Component
public class UnitNameValidator implements Validator {
  final
  UnitRepository unitRepository;

  @Autowired
  public UnitNameValidator(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  public boolean supports(Class<?> clazz) {
    return Unit.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    Unit unit = (Unit) target;
    System.out.println("+++++++++" + unitRepository.findByUnitName(unit.getUnitName()));
    System.out.println("+++++++++COUNT" + unitRepository.findByUnitName(unit.getUnitName()).size());
    int count =  unitRepository.findByUnitName(unit.getUnitName()).size();
    if (count >= 1) {
      errors.rejectValue("unitName", "unit.name.notUnique");
    }
  }
}
