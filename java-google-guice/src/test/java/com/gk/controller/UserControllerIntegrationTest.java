package com.gk.controller;

import com.gk.di.AppModule;
import com.gk.model.User;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerIntegrationTest {
    private static final Gson gson = new Gson();
    private Javalin app;
    private final int port = 7100;
    private OkHttpClient client;

    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new AppModule());
        UserController controller = injector.getInstance(UserController.class);
        app = Javalin.create(controller::registerRoutes).start(port);
        client = new OkHttpClient();
    }

    @AfterEach
    void tearDown() {
        app.stop();
    }

    String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void createAndGetUser() throws IOException {
        User newUser = new User(null, "Henry", "henry@example.com");
        RequestBody body = RequestBody.create(gson.toJson(newUser), MediaType.parse("application/json"));
        Request createReq = new Request.Builder().url(url("/users")).post(body).build();
        try (Response createResp = client.newCall(createReq).execute()) {
            assertEquals(201, createResp.code());
            User created = gson.fromJson(createResp.body().string(), User.class);
            assertNotNull(created.id());

            Request getReq = new Request.Builder().url(url("/users/" + created.id())).get().build();
            try (Response getResp = client.newCall(getReq).execute()) {
                assertEquals(200, getResp.code());
                User fetched = gson.fromJson(getResp.body().string(), User.class);
                assertEquals(created.id(), fetched.id());
            }
        }
    }

    @Test
    void updateUser() throws IOException {
        User newUser = new User(null, "Ivy", "ivy@example.com");
        RequestBody body = RequestBody.create(gson.toJson(newUser), MediaType.parse("application/json"));
        Request createReq = new Request.Builder().url(url("/users")).post(body).build();
        User created;
        try (Response createResp = client.newCall(createReq).execute()) {
            assertEquals(201, createResp.code());
            created = gson.fromJson(createResp.body().string(), User.class);
        }
        User updatedUser = new User(null, "Ivy New", "ivy.new@example.com");
        RequestBody updBody = RequestBody.create(gson.toJson(updatedUser), MediaType.parse("application/json"));
        Request updReq = new Request.Builder().url(url("/users/" + created.id())).put(updBody).build();
        try (Response updResp = client.newCall(updReq).execute()) {
            assertEquals(200, updResp.code());
            User returned = gson.fromJson(updResp.body().string(), User.class);
            assertEquals("Ivy New", returned.name());
        }
    }

    @Test
    void deleteUser() throws IOException {
        User newUser = new User(null, "Jack", "jack@example.com");
        RequestBody body = RequestBody.create(gson.toJson(newUser), MediaType.parse("application/json"));
        Request createReq = new Request.Builder().url(url("/users")).post(body).build();
        User created;
        try (Response createResp = client.newCall(createReq).execute()) {
            assertEquals(201, createResp.code());
            created = gson.fromJson(createResp.body().string(), User.class);
        }
        Request delReq = new Request.Builder().url(url("/users/" + created.id())).delete().build();
        try (Response delResp = client.newCall(delReq).execute()) {
            assertEquals(204, delResp.code());
        }
        Request getReq = new Request.Builder().url(url("/users/" + created.id())).get().build();
        try (Response getResp = client.newCall(getReq).execute()) {
            assertEquals(404, getResp.code());
        }
    }
}

