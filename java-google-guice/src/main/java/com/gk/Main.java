package com.gk;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.gk.controller.UserController;
import com.gk.di.AppModule;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        UserController controller = injector.getInstance(UserController.class);

        Javalin app = Javalin.create(config -> config.defaultContentType = "application/json");
        controller.registerRoutes(app);
        app.start(7000);

        System.out.println("Server started at http://localhost:7000");
    }
}