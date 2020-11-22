package br.com.utily.ecommerce.dto.domain.user;

import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter

@Component
public class UserSignUpDTO extends DTOEntity {

    @NotEmpty(message = "O e-mail é obrigatório")
    @Email(message = "O endereço de e-mail deve ser válido")
    private String username;

    @NotEmpty(message = "A senha é obrigatória")
    private String password;

    @NotEmpty(message = "A confirmação da senha é obrigatória")
    private String confirmPassword;
}
