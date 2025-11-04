package com.gk.di;

import com.gk.repository.InMemoryUserRepository;
import com.gk.repository.UserRepository;
import com.gk.service.UserService;
import com.gk.service.UserServiceImpl;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // install the module that wires up the weekdays interceptor
        install(new WeekDayBlockerModule());

        bind(UserRepository.class).to(InMemoryUserRepository.class).asEagerSingleton();

        bind(UserService.class).to(UserServiceImpl.class).asEagerSingleton();

    }
}
