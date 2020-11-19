package br.com.utily.ecommerce.strategy.impl.domain.customer.creditCard;

import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import br.com.utily.ecommerce.strategy.IStrategy;
import org.springframework.stereotype.Component;

@Component
public class CreditCardDataValidator implements IStrategy<CreditCard> {

    @Override
    public String process(CreditCard creditCard) {

        String requiredFieldsMessage = "Preencha todos os campos obrigatórios";

        if (null == creditCard.getNumber() || creditCard.getNumber().trim().isEmpty()) {
            return requiredFieldsMessage;
        }
        if (null == creditCard.getSecurityCode() || creditCard.getSecurityCode().trim().isEmpty()) {
            return requiredFieldsMessage;
        }
        if (null == creditCard.getPrintedName() || creditCard.getPrintedName().trim().isEmpty()) {
            return requiredFieldsMessage;
        }
        if (!creditCard.validateCardNumber()) {
            return "Número do cartão de crédito inválido";
        }

        return null;
    }
}
