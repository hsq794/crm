package com.shsxt.crm.annotaions;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrmLog {
    String oper() default "";
    String module() default "";
}
