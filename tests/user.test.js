const request = require("supertest");
const app = require("../src/app");
const usersData = require("../src/data/usersData");

beforeEach(() => {
  // Reset in-memory database to pristine seed state before each test
  usersData.resetUsers();
});

describe("API Health Check", () => {
  test("GET / should return 200 with API is working message", async () => {
    const response = await request(app).get("/");
    expect(response.status).toBe(200);
    expect(response.body).toEqual({ message: "API is working" });
  });
});

describe("GET /api/users", () => {
  test("should return list of all users with status 200", async () => {
    const response = await request(app).get("/api/users");
    expect(response.status).toBe(200);
    expect(response.body.success).toBe(true);
    expect(Array.isArray(response.body.data)).toBe(true);
    expect(response.body.data.length).toBe(3);

    // Verify user object fields
    const firstUser = response.body.data[0];
    expect(firstUser).toHaveProperty("id");
    expect(firstUser).toHaveProperty("name");
    expect(firstUser).toHaveProperty("email");
    expect(firstUser).toHaveProperty("age");
  });
});

describe("GET /api/users/:id", () => {
  test("should return user details when valid ID exists", async () => {
    const response = await request(app).get("/api/users/1");
    expect(response.status).toBe(200);
    expect(response.body.success).toBe(true);
    expect(response.body.data.id).toBe(1);
    expect(response.body.data.name).toBe("Alice Johnson");
  });

  test("should return 404 when user ID does not exist", async () => {
    const response = await request(app).get("/api/users/9999");
    expect(response.status).toBe(404);
    expect(response.body.success).toBe(false);
    expect(response.body.error).toBe("Not Found");
    expect(response.body.message).toMatch(/does not exist/);
  });

  test("should return 400 when user ID is not a valid positive integer", async () => {
    const response = await request(app).get("/api/users/invalid-id");
    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
    expect(response.body.error).toBe("Bad Request");
  });

  test("should return 400 when user ID is zero or negative", async () => {
    const response = await request(app).get("/api/users/-1");
    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
  });
});

describe("POST /api/users", () => {
  test("should create a new user with valid data and return 201", async () => {
    const newUser = {
      name: "Diana Prince",
      email: "diana.prince@example.com",
      age: 28
    };

    const response = await request(app)
      .post("/api/users")
      .send(newUser);

    expect(response.status).toBe(201);
    expect(response.body.success).toBe(true);
    expect(response.body.data).toHaveProperty("id");
    expect(response.body.data.name).toBe("Diana Prince");
    expect(response.body.data.email).toBe("diana.prince@example.com");
    expect(response.body.data.age).toBe(28);

    // Verify user can now be fetched
    const fetchResponse = await request(app).get(`/api/users/${response.body.data.id}`);
    expect(fetchResponse.status).toBe(200);
    expect(fetchResponse.body.data.name).toBe("Diana Prince");
  });

  test("should return 400 if name is missing or empty", async () => {
    const response = await request(app)
      .post("/api/users")
      .send({ email: "test@example.com", age: 25 });

    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
    expect(response.body.messages).toEqual(
      expect.arrayContaining([expect.stringMatching(/name/i)])
    );
  });

  test("should return 400 if email is invalid format", async () => {
    const response = await request(app)
      .post("/api/users")
      .send({ name: "Bruce Wayne", email: "not-an-email", age: 35 });

    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
    expect(response.body.messages).toEqual(
      expect.arrayContaining([expect.stringMatching(/email/i)])
    );
  });

  test("should return 400 if age is invalid or missing", async () => {
    const response = await request(app)
      .post("/api/users")
      .send({ name: "Clark Kent", email: "clark@dailyplanet.com", age: 150 });

    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
    expect(response.body.messages).toEqual(
      expect.arrayContaining([expect.stringMatching(/age/i)])
    );
  });

  test("should return 400 if email already exists", async () => {
    const response = await request(app)
      .post("/api/users")
      .send({
        name: "Duplicate User",
        email: "alice.johnson@example.com", // existing seed email
        age: 30
      });

    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
    expect(response.body.message).toMatch(/already exists/i);
  });
});

describe("PUT /api/users/:id", () => {
  test("should update user fields and return 200", async () => {
    const updateData = {
      name: "Alice J. Cooper",
      age: 25
    };

    const response = await request(app)
      .put("/api/users/1")
      .send(updateData);

    expect(response.status).toBe(200);
    expect(response.body.success).toBe(true);
    expect(response.body.data.name).toBe("Alice J. Cooper");
    expect(response.body.data.age).toBe(25);
    expect(response.body.data.email).toBe("alice.johnson@example.com"); // unchanged
  });

  test("should return 404 when updating non-existent user", async () => {
    const response = await request(app)
      .put("/api/users/9999")
      .send({ name: "Ghost User" });

    expect(response.status).toBe(404);
    expect(response.body.success).toBe(false);
  });

  test("should return 400 if no update fields are provided", async () => {
    const response = await request(app)
      .put("/api/users/1")
      .send({});

    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
  });

  test("should return 400 if updating to an existing user email", async () => {
    const response = await request(app)
      .put("/api/users/1")
      .send({ email: "bob.smith@example.com" }); // user 2's email

    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
    expect(response.body.message).toMatch(/already taken/i);
  });
});

describe("DELETE /api/users/:id", () => {
  test("should delete user and return 200 with deleted user data", async () => {
    const response = await request(app).delete("/api/users/2");
    expect(response.status).toBe(200);
    expect(response.body.success).toBe(true);
    expect(response.body.data.id).toBe(2);

    // Verify user is gone
    const fetchResponse = await request(app).get("/api/users/2");
    expect(fetchResponse.status).toBe(404);
  });

  test("should return 404 when deleting non-existent user", async () => {
    const response = await request(app).delete("/api/users/9999");
    expect(response.status).toBe(404);
    expect(response.body.success).toBe(false);
  });

  test("should return 400 when deleting with invalid ID format", async () => {
    const response = await request(app).delete("/api/users/invalid");
    expect(response.status).toBe(400);
    expect(response.body.success).toBe(false);
  });
});

describe("Undefined routes", () => {
  test("should return 404 for non-existent endpoint", async () => {
    const response = await request(app).get("/api/non-existent-route");
    expect(response.status).toBe(404);
    expect(response.body.error).toBe("Not Found");
  });
});
