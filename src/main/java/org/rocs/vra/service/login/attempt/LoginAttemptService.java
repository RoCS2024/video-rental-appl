package org.rocs.vra.service.login.attempt;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Service that tracks and manages failed login attempts per user.
 */
@Service
public class LoginAttemptService {

    /** Maximum allowed failed attempts before lockout. */
    public static final int MAX_NUMBER_OF_ATTEMPTS = 5;

    /** Increment value added to attempt count on each failure. */
    public static final int ATTEMPT_INCREMENTS = 1;

    /** The cache map that monitors the number of failed login attempts */
    private LoadingCache<String, Integer> loginAttemptCache;

    /**
     * Constructs the login attempt service and initializes the cache.
     * The cache expires entries 15 minutes after write, holds at most 100 entries,
     * and returns 0 as the default value for a key that has not been loaded.
     */
    public LoginAttemptService() {
        super();
        loginAttemptCache = CacheBuilder.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                return 0;
            }
        });
    }

    /**
     * Removes a user from the login attempt cache.
     */
    public void evictUserFromLoginAttemptCache(String username) {
        loginAttemptCache.invalidate(username);
    }

    /**
     * Increments the failed attempt counter for the specified user.
     */
    public void addUserToLoginAttemptCache(String username) {
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENTS + loginAttemptCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptCache.put(username, attempts);
    }


    /**
     * Checks whether the given user has exceeded the maximum allowed failed attempts.
     */
    public boolean hasExceededMaxAttempts(String username) {
        try {
            return loginAttemptCache.get(username) >= MAX_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
