# Express.js REST API - Users Backend

A production-ready, clean-architecture backend REST API built with **Node.js**, **Express.js**, and **Jest + Supertest**.

---

## 📖 Table of Contents
1. [Core Concepts Explained](#-core-concepts-explained)
   - [What is Node.js?](#what-is-nodejs)
   - [What is Express.js?](#what-is-expressjs)
   - [What is a REST API?](#what-is-a-rest-api)
   - [HTTP Methods: GET, POST, PUT, DELETE](#http-methods-get-post-put-delete)
   - [Request and Response Lifecycle Flow](#request-and-response-lifecycle-flow)
2. [Project Architecture](#-project-architecture)
3. [Quickstart Guide](#-quickstart-guide)
4. [API Endpoints & Specifications](#-api-endpoints--specifications)
5. [How Flutter Will Communicate With This API](#-how-flutter-will-communicate-with-this-api)
6. [Testing the API](#-testing-the-api)
7. [GitHub Preparation & Environment Variables](#-github-preparation--environment-variables)

---

## 🧠 Core Concepts Explained

### What is Node.js?
**Node.js** is an open-source, cross-platform JavaScript runtime environment built on Chrome's V8 JavaScript engine. Traditionally, JavaScript could only run inside web browsers. Node.js enables developers to run JavaScript directly on computers and servers to build high-performance web servers, command-line tools, and backend services using an asynchronous, event-driven architecture.

### What is Express.js?
**Express.js** is a fast, unopinionated, minimalist web application framework for Node.js. It acts as the backbone layer that simplifies:
- **Routing**: Mapping incoming HTTP requests (like `GET /api/users`) to specific handler functions.
- **Middleware**: Intercepting, validating, and transforming requests before they reach your controllers.
- **Response Handling**: Formatting JSON data, setting HTTP status codes, and managing headers.

### What is a REST API?
**REST** stands for **Representational State Transfer**. It is a software architectural style for distributed systems:
- **Stateless**: Each request contains all information needed to understand and process it. The server stores no client session context.
- **Client-Server**: The frontend (e.g., Flutter, React, mobile app) and backend (Express server) are completely decoupled.
- **Standardized URIs**: Resources are identified by predictable URLs (e.g., `/api/users/1`).
- **JSON Format**: Data is sent and received using standardized JavaScript Object Notation (`{ "key": "value" }`).

### HTTP Methods: GET, POST, PUT, DELETE

| Method | Purpose | Idempotent? | Example URL | Expected Status |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | Retrieve data without altering the server state | Yes | `/api/users` | `200 OK` |
| **POST** | Create a new resource on the server | No | `/api/users` | `201 Created` |
| **PUT** | Update or replace an existing resource | Yes | `/api/users/1` | `200 OK` |
| **DELETE** | Remove a resource from the server | Yes | `/api/users/1` | `200 OK` |

*Note: **Idempotent** means making the identical request multiple times yields the same intended server outcome without additional side-effects.*

### Request and Response Lifecycle Flow

```text
[Client (Postman / Flutter / Browser)]
                │
                ▼  HTTP Request (e.g., POST /api/users { name, email, age })
   ┌────────────────────────────────────────────────────────┐
   │ Express Server (src/app.js)                            │
   │                                                        │
   │  1. CORS Middleware (cors)                             │
   │  2. Body Parser (express.json())                       │
   │  3. Request Logger (src/middleware/logger.js)          │
   │  4. Route Matching (src/routes/userRoutes.js)          │
   │  5. Input Validation (src/middleware/validator.js)     │
   │      ├─ Invalid: Returns 400 Bad Request immediately   │
   │      └─ Valid: Proceeds to Controller                  │
   │  6. Business Logic Controller                          │
   │     (src/controllers/userController.js)                │
   │  7. Data Access Layer (src/data/usersData.js)          │
   │  8. Centralized Error Handler (if exception thrown)    │
   └────────────────────────────────────────────────────────┘
                │
                ▼  HTTP Response (e.g., Status 201 Created + JSON payload)
[Client receives formatted JSON]
```

---

## 📂 Project Architecture

The codebase follows the industry-standard separation of concerns:

```text
express-users-rest-api/
├── .env                  # Local environment configuration (git ignored)
├── .env.example          # Public environment configuration template
├── .gitignore            # Git exclusion patterns (node_modules, .env, etc.)
├── package.json          # Node.js project manifest and scripts
├── README.md             # Beginner-friendly documentation
├── scripts/
│   └── test-api.js       # Standalone live HTTP API test runner with colored output
├── src/
│   ├── app.js            # Express app configuration, middlewares, and route mounting
│   ├── server.js         # HTTP server entrypoint listening on PORT
│   ├── controllers/
│   │   └── userController.js # Handles request/response logic for user endpoints
│   ├── data/
│   │   └── usersData.js      # In-memory users data store & state helpers
│   ├── middleware/
│   │   ├── errorHandler.js   # 404 and global 500 error handling middlewares
│   │   ├── logger.js         # Incoming HTTP request logging middleware
│   │   └── validator.js      # Parameter and payload validation middlewares
│   └── routes/
│       └── userRoutes.js     # Route endpoint definitions for /api/users
└── tests/
    └── user.test.js      # Automated test suite using Jest and Supertest
```

---

## 🚀 Quickstart Guide

### Prerequisites
- **Node.js** (v18 or higher recommended)
- **npm** (v9 or higher)

### 1. Installation
Clone the repository and install dependencies:
```bash
git clone <your-repo-url>
cd express-users-rest-api
npm install
```

### 2. Configure Environment Variables
Copy `.env.example` to `.env`:
```bash
cp .env.example .env
```
Default `.env` contents:
```env
PORT=5000
NODE_ENV=development
```

### 3. Start the Server
- **Production Mode**:
  ```bash
  npm start
  ```
- **Development Mode** (with automatic file reloads):
  ```bash
  npm run dev
  ```

Output:
```text
=========================================
🚀 Express REST API Server running on port 5000
👉 Health check:   http://localhost:5000/
👉 Users endpoint: http://localhost:5000/api/users
👉 Environment:    development
=========================================
```

---

## 📡 API Endpoints & Specifications

Base URL: `http://localhost:5000`

### 1. Health Check
- **URL**: `GET /`
- **Success Response**: `200 OK`
```json
{
  "message": "API is working"
}
```

---

### 2. List All Users
- **URL**: `GET /api/users`
- **Success Response**: `200 OK`
```json
{
  "success": true,
  "count": 3,
  "data": [
    {
      "id": 1,
      "name": "Alice Johnson",
      "email": "alice.johnson@example.com",
      "age": 24
    },
    {
      "id": 2,
      "name": "Bob Smith",
      "email": "bob.smith@example.com",
      "age": 29
    },
    {
      "id": 3,
      "name": "Charlie Brown",
      "email": "charlie.brown@example.com",
      "age": 19
    }
  ]
}
```

---

### 3. Get User By ID
- **URL**: `GET /api/users/:id`
- **Parameters**: `id` (positive integer)
- **Success Response (`200 OK`)**:
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Alice Johnson",
    "email": "alice.johnson@example.com",
    "age": 24
  }
}
```
- **Error Response (`404 Not Found`)**:
```json
{
  "success": false,
  "error": "Not Found",
  "message": "User with ID 999 does not exist."
}
```
- **Error Response (`400 Bad Request`)**:
```json
{
  "success": false,
  "error": "Bad Request",
  "message": "User ID must be a positive whole integer."
}
```

---

### 4. Create New User
- **URL**: `POST /api/users`
- **Headers**: `Content-Type: application/json`
- **Body Schema**:
```json
{
  "name": "Diana Prince",
  "email": "diana.prince@example.com",
  "age": 28
}
```
- **Validation Rules**:
  - `name`: Required, non-empty string, length 2–50 characters.
  - `email`: Required, valid email format regex (`user@domain.com`). Unique.
  - `age`: Required, whole integer between 1 and 120.
- **Success Response (`201 Created`)**:
```json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "id": 4,
    "name": "Diana Prince",
    "email": "diana.prince@example.com",
    "age": 28
  }
}
```
- **Error Response (`400 Bad Request`)**:
```json
{
  "success": false,
  "error": "Validation Error",
  "messages": [
    "Field 'name' is required and must be a non-empty string.",
    "Field 'age' must be a whole number between 1 and 120."
  ]
}
```

---

### 5. Update User By ID
- **URL**: `PUT /api/users/:id`
- **Headers**: `Content-Type: application/json`
- **Body**: Any combination of `name`, `email`, or `age`.
```json
{
  "name": "Diana Prince-Wayne",
  "age": 29
}
```
- **Success Response (`200 OK`)**:
```json
{
  "success": true,
  "message": "User updated successfully",
  "data": {
    "id": 4,
    "name": "Diana Prince-Wayne",
    "email": "diana.prince@example.com",
    "age": 29
  }
}
```

---

### 6. Delete User By ID
- **URL**: `DELETE /api/users/:id`
- **Success Response (`200 OK`)**:
```json
{
  "success": true,
  "message": "User with ID 4 deleted successfully.",
  "data": {
    "id": 4,
    "name": "Diana Prince-Wayne",
    "email": "diana.prince@example.com",
    "age": 29
  }
}
```

---

## 📱 How Flutter Will Communicate With This API

When you are ready to connect a **Flutter** mobile application to this backend API, you will follow this standard client-server integration pattern.

### 1. Network Address Consideration
- **Physical Device**: Use your computer's local Wi-Fi IP address (e.g. `http://192.168.1.50:5000`).
- **Android Emulator**: In the Android Emulator, `localhost` refers to the emulator itself. To reach your computer's host machine, use `http://10.0.2.2:5000`.
- **iOS Simulator**: Uses `http://localhost:5000`.

```dart
import 'dart:io';

String get baseUrl {
  if (Platform.isAndroid) {
    return 'http://10.0.2.2:5000'; // Android emulator host alias
  }
  return 'http://localhost:5000';   // iOS Simulator or desktop
}
```

### 2. User Dart Model
```dart
class UserModel {
  final int id;
  final String name;
  final String email;
  final int age;

  UserModel({
    required this.id,
    required this.name,
    required this.email,
    required this.age,
  });

  factory UserModel.fromJson(Map<String, dynamic> json) {
    return UserModel(
      id: json['id'] as int,
      name: json['name'] as String,
      email: json['email'] as String,
      age: json['age'] as int,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'email': email,
      'age': age,
    };
  }
}
```

### 3. Flutter API Service Implementation (using `http` package)
Add `http: ^1.2.0` to your `pubspec.yaml`.

```dart
import 'dart:convert';
import 'package:http/http.dart' as http;

class UserApiService {
  final String baseUrl;

  UserApiService({required this.baseUrl});

  // GET /api/users
  Future<List<UserModel>> fetchUsers() async {
    final response = await http.get(Uri.parse('$baseUrl/api/users'));

    if (response.statusCode == 200) {
      final Map<String, dynamic> body = jsonDecode(response.body);
      final List<dynamic> usersJson = body['data'];
      return usersJson.map((json) => UserModel.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load users: ${response.statusCode}');
    }
  }

  // POST /api/users
  Future<UserModel> createUser(String name, String email, int age) async {
    final response = await http.post(
      Uri.parse('$baseUrl/api/users'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'name': name, 'email': email, 'age': age}),
    );

    if (response.statusCode == 201) {
      final Map<String, dynamic> body = jsonDecode(response.body);
      return UserModel.fromJson(body['data']);
    } else {
      throw Exception('Failed to create user: ${response.body}');
    }
  }

  // PUT /api/users/:id
  Future<UserModel> updateUser(int id, {String? name, String? email, int? age}) async {
    final response = await http.put(
      Uri.parse('$baseUrl/api/users/$id'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        if (name != null) 'name': name,
        if (email != null) 'email': email,
        if (age != null) 'age': age,
      }),
    );

    if (response.statusCode == 200) {
      final Map<String, dynamic> body = jsonDecode(response.body);
      return UserModel.fromJson(body['data']);
    } else {
      throw Exception('Failed to update user: ${response.body}');
    }
  }

  // DELETE /api/users/:id
  Future<void> deleteUser(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/api/users/$id'));

    if (response.statusCode != 200) {
      throw Exception('Failed to delete user: ${response.body}');
    }
  }
}
```

---

## 🧪 Testing the API

### 1. Automated Test Suite (Jest + Supertest)
Run all 19 automated integration and unit tests:
```bash
npm test
```

Test coverage includes:
- Health check verification (`GET /`)
- Retrieving all users and schema validation
- Fetching user by ID (existing, non-existent 404, invalid ID 400)
- Creating users (valid data 201, missing fields 400, bad emails 400, duplicates 400)
- Updating users (valid data 200, non-existent 404, empty payload 400, duplicate email 400)
- Deleting users (valid 200, non-existent 404, invalid ID 400)
- Undefined route handling (404)

### 2. Live HTTP Runner Script
To test live HTTP requests through the Node network stack with color-coded badges:
```bash
npm run test:api
```

### 3. Testing with cURL / Postman

#### Health Check
```bash
curl -X GET http://localhost:5000/
```

#### Get All Users
```bash
curl -X GET http://localhost:5000/api/users
```

#### Get Single User
```bash
curl -X GET http://localhost:5000/api/users/1
```

#### Create a User
```bash
curl -X POST http://localhost:5000/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Barry Allen", "email": "barry.allen@centralcity.com", "age": 26}'
```

#### Update a User
```bash
curl -X PUT http://localhost:5000/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Alice Wonderland", "age": 25}'
```

#### Delete a User
```bash
curl -X DELETE http://localhost:5000/api/users/1
```

---

## 🔒 GitHub Preparation & Environment Variables

- `.gitignore` is pre-configured to exclude:
  - `node_modules/`
  - `.env` (prevents secret leaks)
  - `coverage/`
  - log files
- `.env.example` is checked into version control to allow teammates to quickly configure their environment without exposing sensitive variables.
- All dependencies and scripts are specified in `package.json`.
