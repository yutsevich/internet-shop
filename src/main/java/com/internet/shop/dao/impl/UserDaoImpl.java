package com.internet.shop.dao.impl;

import com.internet.shop.dao.interfaces.UserDao;
import com.internet.shop.db.Storage;
import com.internet.shop.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        List<User> users = getAll();
        IntStream.range(0,users.size())
                .filter(i -> users.get(i).getId().equals(user.getId()))
                .forEach(i -> users.set(i, user));
        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        return Storage.users.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Storage.users.stream()
                .filter(user -> user.getLogin().equals(login)).findFirst();
    }
}
