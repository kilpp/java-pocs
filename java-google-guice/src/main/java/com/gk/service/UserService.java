package com.gk.service;

import com.gk.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<User> getById(String id);
    List<User> list();
    Optional<User> update(String id, User user);
    boolean delete(String id);
}
