package com.gk.service;

import com.gk.model.User;
import com.gk.repository.InMemoryUserRepository;
import com.gk.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceImplTest {
    private UserService service;

    @BeforeEach
    void setUp() {
        UserRepository repository = new InMemoryUserRepository();
        service = new UserServiceImpl(repository);
    }

    @Test
    void createAssignsId() {
        User created = service.create(new User(null, "Dave", "dave@example.com"));
        assertNotNull(created.id());
    }

    @Test
    void getByIdReturnsUser() {
        User created = service.create(new User(null, "Eve", "eve@example.com"));
        Optional<User> fetched = service.getById(created.id());
        assertTrue(fetched.isPresent());
        assertEquals("Eve", fetched.get().name());
    }

    @Test
    void listReturnsUsers() {
        service.create(new User(null, "L1", "l1@example.com"));
        service.create(new User(null, "L2", "l2@example.com"));
        assertEquals(2, service.list().size());
    }

    @Test
    void updateModifiesNameAndEmail() {
        User created = service.create(new User(null, "Frank", "frank@example.com"));
        Optional<User> updated = service.update(created.id(), new User(null, "Franky", "franky@example.com"));
        assertTrue(updated.isPresent());
        assertEquals("Franky", updated.get().name());
        assertEquals("franky@example.com", updated.get().email());
    }

    @Test
    void deleteRemovesUser() {
        User created = service.create(new User(null, "Gone", "gone@example.com"));
        assertTrue(service.delete(created.id()));
        assertTrue(service.getById(created.id()).isEmpty());
    }
}

