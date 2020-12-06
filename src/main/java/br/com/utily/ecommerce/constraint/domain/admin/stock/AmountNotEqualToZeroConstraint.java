package br.com.utily.ecommerce.constraint.domain.admin.stock;

import br.com.utily.ecommerce.validator.domain.admin.stock.AmountNotEqualToZeroValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = AmountNotEqualToZeroValidator.class)
@Documented
public @interface AmountNotEqualToZeroConstraint {

    String message() default "{AmountNotEqualToZeroConstraint.rule}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
