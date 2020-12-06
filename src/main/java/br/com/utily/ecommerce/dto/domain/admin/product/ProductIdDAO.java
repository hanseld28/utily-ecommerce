package br.com.utily.ecommerce.dto.domain.admin.product;

import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter

@Component
public class ProductIdDAO extends DTOEntity {

    @NotNull
    private Long id;
}
