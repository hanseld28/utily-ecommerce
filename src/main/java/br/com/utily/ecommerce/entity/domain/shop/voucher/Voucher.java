package br.com.utily.ecommerce.entity.domain.shop.voucher;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "vouchers")
public class Voucher extends AlternativeDomainEntity {

    @Basic
    @Column(name = "vch_name")
    private String name;

    @Basic
    @Column(name = "vch_multiplication_factor")
    private Double multiplicationFactor;

    @Enumerated(EnumType.STRING)
    @Column(name = "vch_type")
    private EVoucherType type;

    @Basic
    @Column(name = "vch_value")
    private Double value;


    public String format() {
        StringBuilder formatted = new StringBuilder();

        switch (type) {
            case DISCOUNT:
                int percent = (int)(multiplicationFactor * 100);
                formatted.append(percent);
                formatted.append("%");
                break;
            case TRADE:
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
                String currencyValue = currencyFormatter.format(value).replace("$", "R$");
                formatted.append(currencyValue);
                break;
            default:
                break;
        }

        return formatted.toString();
    }

    public String formatToText() {
        StringBuilder formattedText = new StringBuilder("Cupom de ");

        formattedText.append(type.getDisplayName().toLowerCase());

        if (type.equals(EVoucherType.DISCOUNT)) {
            formattedText.append(" de ");
        }
        if (type.equals(EVoucherType.TRADE)) {
            formattedText.append(" no valor de ");
        }

        formattedText.append(format());

        return formattedText.toString();
    }
}
