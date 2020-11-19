package br.com.utily.ecommerce.dao.domain.user;

import br.com.utily.ecommerce.dao.IDomainDAO;
import br.com.utily.ecommerce.entity.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUserDAO extends IDomainDAO<User> {

    Optional<User> findDistinctByInactivatedFalseAndUsername(String username);
}
