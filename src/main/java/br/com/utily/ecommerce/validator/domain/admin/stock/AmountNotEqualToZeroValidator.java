package br.com.utily.ecommerce.validator.domain.admin.stock;

import br.com.utily.ecommerce.constraint.domain.admin.stock.AmountNotEqualToZeroConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountNotEqualToZeroValidator implements ConstraintValidator<AmountNotEqualToZeroConstraint, Integer> {

    public void initialize(AmountNotEqualToZeroConstraint constraint) {
    }

    public boolean isValid(Integer amount, ConstraintValidatorContext context) {
        return amount != null && amount != 0;
    }
}
