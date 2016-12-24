package com.eugene.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Ngô Mạnh Cường on 12/3/2016.
 */

/**
 * Mẫu hàm cho validator
 * message là thông báo lỗi
 * group là nhóm để thực hiện kiểm tra báo lỗi
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NgoManhCuong_05_CourseNameValidator.class)
@Documented
public @interface NgoManhCuong_05_NotExistingCourseName {

  String message() default "Course name already exist";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean isUpdate() default true;
}
