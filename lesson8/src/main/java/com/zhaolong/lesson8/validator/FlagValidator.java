package com.zhaolong.lesson8.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {
    //flag的有效值多个使用','隔开
    String value();
    //提示内容
    String message() default "flag不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
