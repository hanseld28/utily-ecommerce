package br.com.utily.ecommerce.config.security.user;

import br.com.utily.ecommerce.dao.domain.user.IUserDAO;
import br.com.utily.ecommerce.entity.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findDistinctByInactivatedFalseAndUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nenhum usuário encontrado com o username: " + username));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (user.getRole() != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        }

        return new UserDetailsImpl(user,
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }

}
