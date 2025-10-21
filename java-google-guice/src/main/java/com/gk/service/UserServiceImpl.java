package com.gk.service;

import com.google.inject.Inject;
import com.gk.model.User;
import com.gk.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Inject
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<User> list() {
        return repository.findAll();
    }

    @Override
    public Optional<User> update(String id, User user) {
        return repository.findById(id).map(existing -> {
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            return repository.save(existing);
        });
    }

    @Override
    public boolean delete(String id) {
        return repository.deleteById(id);
    }
}
