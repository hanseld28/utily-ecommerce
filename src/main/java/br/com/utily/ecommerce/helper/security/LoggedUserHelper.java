package br.com.utily.ecommerce.helper.security;

import br.com.utily.ecommerce.config.security.user.UserDetailsImpl;
import br.com.utily.ecommerce.entity.domain.user.User;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoggedUserHelper {

    private final Customer mockCustomer;
    private final IDomainService<Customer> customerDomainService;

    @Autowired
    public LoggedUserHelper(Customer mockCustomer,
                             @Qualifier("domainService") IDomainService<Customer> customerDomainService) {
        this.mockCustomer = mockCustomer;
        this.customerDomainService = customerDomainService;
    }

    public static User getLoggedUser() {
        Authentication auth = getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return userDetails.getUser();
    }

    public Customer getLoggedCustomerUser() {
        User loggedUser = getLoggedUser();
        Optional<Customer> optionalCustomer = customerDomainService.findBy(loggedUser, mockCustomer);

        return optionalCustomer.orElseThrow(NotFoundException::new);
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
