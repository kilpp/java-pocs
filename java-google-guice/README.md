# Java Guice CRUD example

This small project demonstrates a CRUD REST API wired with Google Guice. It uses an in-memory repository (no external DB).

Build and run

Run with Gradle:

```bash
./gradlew clean build
./gradlew run
```

The server starts on http://localhost:7000

Endpoints

- POST /users    -> create user {name,email}
- GET  /users    -> list users
- GET  /users/:id -> get user
- PUT  /users/:id -> update user {name,email}
- DELETE /users/:id -> delete user

Example curl

```bash
# create
curl -s -X POST -H "Content-Type: application/json" -d '{"name":"Alice","email":"a@x.com"}' http://localhost:7000/users

# list
curl http://localhost:7000/users

# get
curl http://localhost:7000/users/<id>

# update
curl -s -X PUT -H "Content-Type: application/json" -d '{"name":"Alicia","email":"a@x.com"}' http://localhost:7000/users/<id>

# delete
curl -s -X DELETE http://localhost:7000/users/<id>
```
