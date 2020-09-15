package com.internet.shop.security;

import com.internet.shop.model.User;
import javax.naming.AuthenticationException;

public interface AuthenticationService {
    User login(String login, String password)
            throws AuthenticationException, com.internet.shop.exceptions.AuthenticationException;
}
