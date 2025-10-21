package com.gk.repository;

import com.gk.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {
    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void saveGeneratesIdForNewUser() {
        User u = new User(null, "Bob", "bob@example.com");
        User saved = repository.save(u);
        assertNotNull(saved.id());
        assertEquals("Bob", saved.name());
    }

    @Test
    void saveUpdatesExistingUser() {
        User u = new User(null, "Bob", "bob@example.com");
        User saved = repository.save(u);
        User updated = repository.save(new User(saved.id(), "Bobby", "bob@example.com"));
        assertEquals(saved.id(), updated.id());
        assertEquals("Bobby", updated.name());
    }

    @Test
    void findByIdReturnsUser() {
        User saved = repository.save(new User(null, "Carol", "carol@example.com"));
        Optional<User> found = repository.findById(saved.id());
        assertTrue(found.isPresent());
        assertEquals("Carol", found.get().name());
    }

    @Test
    void findAllReturnsAllUsers() {
        repository.save(new User(null, "U1", "u1@example.com"));
        repository.save(new User(null, "U2", "u2@example.com"));
        List<User> users = repository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void deleteByIdRemovesUser() {
        User saved = repository.save(new User(null, "Del", "del@example.com"));
        boolean removed = repository.deleteById(saved.id());
        assertTrue(removed);
        assertTrue(repository.findById(saved.id()).isEmpty());
    }
}

