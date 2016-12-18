package com.eugene.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Eugene on 12/3/2016.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
  @Constraint(validatedBy = CourseNameValidator.class)
@Documented
public @interface NotExistingCourseName {

  String message() default "Course name already exist";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean isUpdate() default true;
}
