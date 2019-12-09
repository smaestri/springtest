package springtest.security;

import springtest.api.MongoUserRepository;
import springtest.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MongoUserRepository mongoUserRepository;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<Utilisateur> userAuthenticated = mongoUserRepository.findByEmail(login);

        if (!userAuthenticated.isPresent()) {
            throw new UsernameNotFoundException(login);
        }

        return new UserPrincipal(userAuthenticated.get());

    }

    public static class UserPrincipal implements UserDetails {

        private Utilisateur user;

        public UserPrincipal(Utilisateur user) {
            this.user = user;
        }

        public Utilisateur getUser() {
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
