package br.com.utily.ecommerce.entity.domain.shop.freight;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "freights")
public class Freight extends DomainEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "fgh_table")
    private EFreightTable freightTable;

    @Basic
    @Column(name = "fgh_value")
    private Double value;

    @Basic
    @Column(name = "fgh_estimate_in_days")
    private Integer deliveryEstimate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "fgh_sls_id")
    private Sale sale;

    public void calculate(final Double baseValue, final String zipCode) {
        int MINIMUM_DAYS = 2;
        computeFreightTableBy(zipCode);
        double coefficient = freightTable.getCoefficient();
        double weight = freightTable.getWeight();

        this.value = baseValue * (isFree() ? 0.0 : weight) * coefficient;
        deliveryEstimate = (int) Math.ceil(coefficient + MINIMUM_DAYS);
    }

    private void computeFreightTableBy(final String zipCode) {
        int DIVIDER_NUMBER = 100;
        int length = zipCode.length();
        int identifier = Integer.parseInt(zipCode.substring(length-3, length-1)) / DIVIDER_NUMBER;

        EFreightTable selectedFreightTable;

        if (identifier > -1 && identifier < 3) {
            selectedFreightTable = EFreightTable.FREE;
        } else if (identifier > 2 && identifier < 5) {
            selectedFreightTable = EFreightTable.SLOWER;
        } else if (identifier > 4 && identifier < 7) {
            selectedFreightTable = EFreightTable.FAST;
        } else if (identifier > 6 && identifier < 9) {
            selectedFreightTable = EFreightTable.SUPER_FAST;
        } else {
            selectedFreightTable = EFreightTable.NORMAL;
        }

        freightTable = selectedFreightTable;
    }

    public Boolean isFree() {
        return freightTable.equals(EFreightTable.FREE);
    }
}
