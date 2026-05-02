package org.rocs.vra.domain.user.principal;

import org.rocs.vra.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Custom implementation of Spring Security's UserDetails interface.
 * This class acts as an adapter between the application's User entity and
 * Spring Security's authentication model. It provides the necessary user information
 * (username, password, authorities) and account status flags required for authentication
 * and authorization.
 */
public class UserPrincipal implements UserDetails {

    /**
     * The underlying user entity containing the authentication data.
     */
    private User user;

    /**
     * Constructs a new {@code UserPrincipal} wrapping the given user entity.
     */
    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * Returns the user's password used for authentication.
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * Returns the username used for authentication.
     */
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return !this.user.isLocked();
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled (active).
     */
    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }
}
