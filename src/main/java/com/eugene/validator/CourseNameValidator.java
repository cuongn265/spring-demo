package com.eugene.validator;

import com.eugene.repository.CourseRepository;
import com.eugene.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Eugene on 12/3/2016.
 */
@Component
public class CourseNameValidator implements ConstraintValidator<NotExistingCourseName, String> {

  private final CourseRepository courseRepository;
  private boolean isUpdate;

  @Autowired
  public CourseNameValidator(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }


  public void initialize(NotExistingCourseName notExistingCourseName) {
    isUpdate = notExistingCourseName.isUpdate();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  public boolean isValid(String courseName, ConstraintValidatorContext constraintValidatorContext) {
    if (isUpdate) {
      return false;
    } else {
      return courseRepository.findCourseByCourseName(courseName) == 0;
    }
  }
}
