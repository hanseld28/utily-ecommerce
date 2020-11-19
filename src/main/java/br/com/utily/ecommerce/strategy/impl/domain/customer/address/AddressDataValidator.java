package br.com.utily.ecommerce.strategy.impl.domain.customer.address;

import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.strategy.IStrategy;
import org.springframework.stereotype.Component;

@Component
public class AddressDataValidator implements IStrategy<Address> {

    @Override
    public String process(Address address) {

        String requiredFieldsMessage = "Preencha todos os campos obrigatórios";

        if (isNull(address.getCep()) || isEmpty(address.getCep())) {
            return requiredFieldsMessage;
        }
        if (isNull(address.getPublicPlace()) || isEmpty(address.getPublicPlace().toString())) {
            return requiredFieldsMessage;
        }
        if (isNull(address.getNumber()) || isEmpty(address.getNumber())) {
            return requiredFieldsMessage;
        }
        if (isNull(address.getNeighbourhood()) || isEmpty(address.getNeighbourhood())) {
            return requiredFieldsMessage;
        }
        if (null == address.getType() || address.getType().toString().isEmpty()) {
            return requiredFieldsMessage;
        }
        if (isNull(address.getCity()) || isEmpty(address.getCity())) {
            return requiredFieldsMessage;
        }
        if (isNull(address.getState()) || isEmpty(address.getState())) {
            return requiredFieldsMessage;
        }

        return null;
    }

    private boolean isNull(String field) {
        return null == field;
    }

    private boolean isEmpty(String field) {
        return field.trim().isEmpty();
    }
}
