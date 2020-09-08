package com.internet.shop.servise.interfaces;

import com.internet.shop.model.User;
import java.util.List;

public interface UserService {
    User create(User user);

    User get(Long id);

    List<User> getAll();

    User update(User user);

    boolean deleteById(Long id);
}
