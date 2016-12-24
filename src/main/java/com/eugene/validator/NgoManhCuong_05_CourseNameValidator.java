package com.eugene.validator;

import com.eugene.repository.NgoManhCuong_05_CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Ngô Mạnh Cường on 12/3/2016.
 */

/**
 * Validator cho tên khóa học, đảm bảo tên không bị trùng
 */

@Component
public class NgoManhCuong_05_CourseNameValidator implements ConstraintValidator<NgoManhCuong_05_NotExistingCourseName, String> {

  private final NgoManhCuong_05_CourseRepository courseRepository;

  @Autowired
  public NgoManhCuong_05_CourseNameValidator(NgoManhCuong_05_CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  /*Hàm khởi tạo*/
  @Override
  public void initialize(NgoManhCuong_05_NotExistingCourseName notExistingCourseName) {
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /*Hàm kiểm tra thỏa điều kiện hay không, nếu đúng thì trả lỗi*/
  public boolean isValid(String courseName, ConstraintValidatorContext constraintValidatorContext) {
    return courseRepository.findCourseByCourseName(courseName) == 0;
  }
}
