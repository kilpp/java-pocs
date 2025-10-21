package com.gk.di;

import com.google.inject.AbstractModule;
import com.gk.repository.InMemoryUserRepository;
import com.gk.repository.UserRepository;
import com.gk.service.UserService;
import com.gk.service.UserServiceImpl;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserRepository.class).to(InMemoryUserRepository.class).asEagerSingleton();
        bind(UserService.class).to(UserServiceImpl.class).asEagerSingleton();
    }
}
