package com.gk;

import com.gk.controller.UserController;
import com.gk.di.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        UserController controller = injector.getInstance(UserController.class);

        Javalin
                .create(controller::registerRoutes)
                .start(8080);

        System.out.println("Server started at http://localhost:7000");
    }
}