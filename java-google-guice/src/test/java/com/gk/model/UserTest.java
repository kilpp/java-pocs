package com.gk.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void recordStoresValues() {
        User u = new User("id123", "Alice", "alice@example.com");
        assertEquals("id123", u.id());
        assertEquals("Alice", u.name());
        assertEquals("alice@example.com", u.email());
    }
}