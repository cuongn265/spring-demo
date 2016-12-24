package com.eugene.validator;

import com.eugene.domain.NgoManhCuong_05_Unit;
import com.eugene.repository.NgoManhCuong_05_UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Ngô Mạnh Cường on 12/19/2016.
 */
@Component
public class NgoManhCuong_05_UnitNameValidator implements Validator {
  final
  NgoManhCuong_05_UnitRepository unitRepository;

  @Autowired
  public NgoManhCuong_05_UnitNameValidator(NgoManhCuong_05_UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  public boolean supports(Class<?> clazz) {
    return NgoManhCuong_05_Unit.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    NgoManhCuong_05_Unit unit = (NgoManhCuong_05_Unit) target;
    int count =  unitRepository.findByUnitName(unit.getUnitName()).size();
    if (count >= 1) {
      errors.rejectValue("unitName", "unit.name.notUnique");
    }
  }
}
