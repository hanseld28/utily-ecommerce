package br.com.utily.ecommerce.strategy.impl.domain.customer;

import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.strategy.IStrategy;
import org.springframework.stereotype.Component;

@Component
public class ConfirmPasswordValidator implements IStrategy<Customer> {

    @Override
    public String process(Customer customer) {
        String password = customer.getUser().getPassword();
        String confirmPassword = customer.getUser().getConfirmPassword();

        if (password != null && confirmPassword != null) {

            password = password.trim();
            confirmPassword = confirmPassword.trim();

            if (!password.isEmpty() && !confirmPassword.isEmpty()
                    && passwordsNotMatches(password, confirmPassword))
            {
                    return "As senhas não se coincidem";
            }
        }

        return null;
    }

    private boolean passwordsNotMatches(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

}
