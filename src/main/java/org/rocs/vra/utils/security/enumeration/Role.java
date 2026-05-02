package org.rocs.vra.utils.security.enumeration;

import static org.rocs.vra.utils.security.constant.Authority.*;

/**
 * Represents user roles with their associated authorities.
 */
public enum Role {

    /** Role with read and update authorities. */
    ROLE_USER(USER_AUTHORITIES),
    /** Role with read, create, and update authorities. */
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    /** Role with read, create, update, and delete authorities. */
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authorities;

    /**
     * Constructs a role with the given authorities.
     */
    Role(String... authorities) {
        this.authorities = authorities;
    }

    /**
     * Returns the authorities granted to this role.
     */
    public String[] getAuthorities() {
        return authorities;
    }
}
