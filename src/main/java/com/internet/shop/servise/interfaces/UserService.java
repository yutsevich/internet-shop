package com.internet.shop.servise.interfaces;

import com.internet.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    User get(Long id);

    Optional<User> findByLogin(String login);

    List<User> getAll();

    User update(User user);

    boolean deleteById(Long id);
}
