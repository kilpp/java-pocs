package annotations;

import validator.MinValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Min {

    String message() default "Invalid minimum value %s";
    int value() default 0;

    Class<?> validator() default MinValidator.class;

}
