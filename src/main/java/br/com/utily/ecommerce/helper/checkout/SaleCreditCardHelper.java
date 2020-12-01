package br.com.utily.ecommerce.helper.checkout;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleCreditCard;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleCreditCardId;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.CreditCardValue;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SaleCreditCardHelper {

    private final IDomainService<CreditCard> creditCardDomainService;
    private final CreditCard mockCreditCard;

    private ObjectProvider<SaleCreditCard> saleCreditCardProvider;
    private ObjectProvider<SaleCreditCardId> saleCreditCardIdProvider;

    @Autowired
    public SaleCreditCardHelper(@Qualifier("domainService")
                                IDomainService<CreditCard> creditCardDomainService,
                                CreditCard mockCreditCard,
                                ObjectProvider<SaleCreditCard> saleCreditCardProvider,
                                ObjectProvider<SaleCreditCardId> saleCreditCardIdProvider) {
        this.creditCardDomainService = creditCardDomainService;
        this.mockCreditCard = mockCreditCard;
        this.saleCreditCardProvider = saleCreditCardProvider;
        this.saleCreditCardIdProvider = saleCreditCardIdProvider;
    }

    public SaleCreditCard adapt(CreditCardValue creditCardValue, Sale sale) {
        Long creditCardId = creditCardValue.getCreditCard().getId();
        Optional<CreditCard> creditCardOptional = creditCardDomainService.findById(creditCardId, mockCreditCard);
        CreditCard creditCard = creditCardOptional.orElseThrow(NotFoundException::new);
        creditCardValue.setCreditCard(creditCard);

        SaleCreditCard saleCreditCard = saleCreditCardProvider.getObject();
        SaleCreditCardId saleCreditCardId = saleCreditCardIdProvider.getObject();


        return saleCreditCard.adapt(saleCreditCardId, sale, creditCardValue);
    }
}
