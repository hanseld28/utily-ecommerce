package br.com.utily.ecommerce.validator.constraints.domain.shop.sale.creditCard;

import br.com.utily.ecommerce.validator.impl.domain.shop.sale.creditCard.MinimumPaymentValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = MinimumPaymentValueValidator.class)
@Documented
public @interface MinimumPaymentValueConstraint {

    String message() default "{MinimumPaymentValueConstraint.rule}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
