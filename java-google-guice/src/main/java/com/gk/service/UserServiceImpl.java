package com.gk.service;

import com.gk.model.User;
import com.gk.repository.UserRepository;
import com.google.inject.Inject;

import java.util.List;
import java.util.Optional;

public record UserServiceImpl(UserRepository repository) implements UserService {

    @Inject
    public UserServiceImpl {
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
        return repository
                .findById(id)
                .map(existing ->
                        repository.save(
                                new User(existing.id(), user.name(), user.email())
                        )
                );
    }

    @Override
    public boolean delete(String id) {
        return repository.deleteById(id);
    }
}
