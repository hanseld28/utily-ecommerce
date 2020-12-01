package br.com.utily.ecommerce.entity.domain.user.customer.creditCard;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleCreditCard;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "credit_cards")
public class CreditCard extends AlternativeDomainEntity {

    @Basic
    @Column(name = "crd_number")
    private String number;

    @Basic
    @Column(name = "crd_printed_name")
    private String printedName;

    @Basic
    @Column(name = "crd_security_code")
    private String securityCode;

    @JoinColumn(name = "crd_cst_id", foreignKey = @ForeignKey(name = "crd_cst_fk"))
    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    @Column(name = "crd_flag")
    @Enumerated(EnumType.STRING)
    private ECardFlag flag;

    @OneToMany(mappedBy = "creditCard")
    private List<SaleCreditCard> relatedSales = new ArrayList<>();


    public CreditCard(Long id) {
        this.id = id;
    }

    public CreditCard(String number, String printedName, String securityCode, Customer customer) {
        this.number = number;
        this.printedName = printedName;
        this.securityCode = securityCode;
        this.customer = customer;

        computeCreditCardFlagByNumber();
    }

    public CreditCard(String number, String printedName, String securityCode) {
        this.number = number;
        this.printedName = printedName;
        this.securityCode = securityCode;

        computeCreditCardFlagByNumber();
    }

    public void setNumber(String number) {
        this.number = number;
        computeCreditCardFlagByNumber();
    }

    public String buildCreditCardShortName() {
        StringBuilder creditCardShortName = new StringBuilder();

        if (number != null) {
            if (flag == null) {
                computeCreditCardFlagByNumber();
            }
            if (flag != null && printedName != null) {
                String flagName = flag.getDisplayName();
                creditCardShortName.append(flagName);
                creditCardShortName.append(" - ");

                creditCardShortName.append(printedName);
                creditCardShortName.append(" - ");

                String cardNumberMasked = buildMaskedCardNumber();
                creditCardShortName.append(cardNumberMasked);

                return creditCardShortName.toString();
            }
        } else {
            if (id != null) {
                creditCardShortName.append("Cartão de Crédito ID ");
                creditCardShortName.append(id);

                return creditCardShortName.toString();
            }
        }
        return null;
    }

    public String buildMaskedCardNumber() {
        StringBuilder cardNumberOnlyLastFourDigits = new StringBuilder();

        int numberLength = number.length() - 4;

        for (int i = 0; i < numberLength; i++) {
            boolean fourGroup = (i == 3 || i == 7 || i == 11);
            String maskedNumberOrWhitespace = fourGroup ? "* " : "*";
            cardNumberOnlyLastFourDigits.append(maskedNumberOrWhitespace);
        }

        cardNumberOnlyLastFourDigits.append(number.substring((numberLength)));

        return cardNumberOnlyLastFourDigits.toString();
    }

    private void computeCreditCardFlagByNumber() {
        if (validateVisaCardNumber()) {
            flag = ECardFlag.VISA;
        } else if (validateMasterCardNumber()) {
            flag = ECardFlag.MASTERCARD;
        }
    }

    public boolean validateCardNumber() {
        return validateVisaCardNumber() || validateMasterCardNumber();
    }

    public boolean validateVisaCardNumber() {
        boolean valid = false;

        if (number != null) {
            String visaRegex = "^4[0-9]{12}(?:[0-9]{3})?$";
            valid = number.matches(visaRegex);
        }

        return valid;
    }

    public boolean validateMasterCardNumber() {
        boolean valid = false;

        if (number != null) {
            String masterCardRegex = "^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]"
                    + "|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$";

            valid = number.matches(masterCardRegex);
        }

        return valid;
    }
}