package org.rocs.vra.utils.security.constant;

/**
 *  Defines authority levels and their associated permissions.
 */
public class Authority {

    /**
     * Authorities granted to regular users: read and update.
     */
    public static final String[] USER_AUTHORITIES = {"user:read", "user:update"};

    /**
     * Authorities granted to admin users: read, create, and update.
     */
    public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:create", "user:update"};

    /**
     * Authorities granted to super admin users: read, create, update, and delete.
     */
    public static final String[] SUPER_ADMIN_AUTHORITIES = {"user:read", "user:create", "user:update", "user:delete"};
}
