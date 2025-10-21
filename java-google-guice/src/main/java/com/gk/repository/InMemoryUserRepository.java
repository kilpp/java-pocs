package com.gk.repository;

import com.gk.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> store = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        if (user.id() == null || user.id().isEmpty()) {
            String id = UUID.randomUUID().toString();
            var newUser = new User(id, user.name(), user.email());
            store.put(id, newUser);
            return store.get(id);
        } else {
            store.put(user.id(), user);
            return store.get(user.id());
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(String id) {
        return store.remove(id) != null;
    }
}
