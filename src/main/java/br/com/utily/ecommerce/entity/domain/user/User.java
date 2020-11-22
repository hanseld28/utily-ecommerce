package br.com.utily.ecommerce.entity.domain.user;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Component
@Table(name = "users")
public class User extends AlternativeDomainEntity {

    @Basic
    @Column(name = "usr_username", nullable = false)
    private String username;

    @Basic
    @Column(name = "usr_password", nullable = false)
    private String password;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "usr_role")
    private EUserRole role;

    @Transient
    private String confirmPassword;
}