package br.com.utily.ecommerce.dto.domain.user;

import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter

@Component
public class UserUpdateDTO extends DTOEntity {

    @NotNull
    private Long id;

    @NotEmpty(message = "O e-mail é obrigatório")
    @Email(message = "O endereço de e-mail deve ser válido")
    private String username;

    private String password;

    private String confirmPassword;
}
