package com.gk.di;

import com.gk.controller.UserController;
import com.gk.repository.InMemoryUserRepository;
import com.gk.repository.UserRepository;
import com.gk.service.UserService;
import com.gk.service.UserServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppModuleTest {

    @Test
    void bindingsResolveCorrectTypes() {
        Injector injector = Guice.createInjector(new AppModule());
        UserRepository repo = injector.getInstance(UserRepository.class);
        assertInstanceOf(InMemoryUserRepository.class, repo);

        UserService service = injector.getInstance(UserService.class);
        assertInstanceOf(UserServiceImpl.class, service);

        UserController controller = injector.getInstance(UserController.class);
        assertNotNull(controller);

        WeekDayBlocker blocker = injector.getInstance(WeekDayBlocker.class);
        assertNotNull(blocker);
    }
}

