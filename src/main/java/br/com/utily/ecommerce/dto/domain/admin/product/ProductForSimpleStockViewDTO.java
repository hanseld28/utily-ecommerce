package br.com.utily.ecommerce.dto.domain.admin.product;

import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter

@Component
public class ProductForSimpleStockViewDTO extends DTOEntity {

    private Long id;

    private String title;

    private String imageUrl;
}
