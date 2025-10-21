package com.gk.controller;

import com.google.inject.Inject;
import com.gk.model.User;
import com.gk.service.UserService;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {
    private final UserService userService;
    private final Gson gson = new Gson();

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(Javalin app) {
        app.post("/users", this::createUser);
        app.get("/users", this::listUsers);
        app.get("/users/:id", this::getUser);
        app.put("/users/:id", this::updateUser);
        app.delete("/users/:id", this::deleteUser);
    }

    private void createUser(Context ctx) {
        User user = gson.fromJson(ctx.body(), User.class);
        User created = userService.create(user);
        ctx.status(201).result(gson.toJson(created));
    }

    private void listUsers(Context ctx) {
        List<User> users = userService.list();
        ctx.json(users);
    }

    private void getUser(Context ctx) {
        String id = ctx.pathParam("id");
        userService.getById(id).ifPresentOrElse(
                user -> ctx.json(user),
                () -> ctx.status(404).result("Not found")
        );
    }

    private void updateUser(Context ctx) {
        String id = ctx.pathParam("id");
        User user = gson.fromJson(ctx.body(), User.class);
        userService.update(id, user).ifPresentOrElse(
                updated -> ctx.json(updated),
                () -> ctx.status(404).result("Not found")
        );
    }

    private void deleteUser(Context ctx) {
        String id = ctx.pathParam("id");
        boolean removed = userService.delete(id);
        if (removed) ctx.status(204);
        else ctx.status(404).result("Not found");
    }
}
