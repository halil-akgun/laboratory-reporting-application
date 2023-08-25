package com.ozgur.laboratoryreportingapplication.shared.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { UniquePatientValidator.class })
@Target({  FIELD })
@Retention(RUNTIME)
public @interface UniquePatient {

    String message() default "{validation.constraints.UniquePatient.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
