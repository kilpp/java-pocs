package com.gk.controller;

import com.gk.model.User;
import com.gk.service.UserService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class UserController {
    private static final Gson gson = new Gson();

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(JavalinConfig config) {
        config.routes.post("/users", this::createUser);
        config.routes.get("/users", this::listUsers);
        config.routes.get("/users/{id}", this::getUser);
        config.routes.put("/users/{id}", this::updateUser);
        config.routes.delete("/users/{id}", this::deleteUser);
    }

    private void createUser(Context ctx) {
        User user = gson.fromJson(ctx.body(), User.class);
        User created = userService.create(user);
        ctx.status(201).result(gson.toJson(created));
    }

    private void listUsers(Context ctx) {
        ctx.json(userService.list());
    }

    private void getUser(Context ctx) {
        String id = ctx.pathParam("id");
        userService.getById(id).ifPresentOrElse(
                ctx::json,
                () -> ctx.status(404).result("Not found")
        );
    }

    private void updateUser(Context ctx) {
        String id = ctx.pathParam("id");
        User user = gson.fromJson(ctx.body(), User.class);
        userService.update(id, user).ifPresentOrElse(
                ctx::json,
                () -> ctx.status(404).result("Not found")
        );
    }

    private void deleteUser(Context ctx) {
        String id = ctx.pathParam("id");
        boolean removed = userService.delete(id);
        if (removed) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Not found");
        }
    }
}
