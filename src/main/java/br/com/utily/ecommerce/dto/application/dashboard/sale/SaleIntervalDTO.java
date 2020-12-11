package br.com.utily.ecommerce.dto.application.dashboard.sale;

import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class SaleIntervalDTO extends DTOEntity {

    private String startDate;
    private String endDate;

}
