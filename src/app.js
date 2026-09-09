const express = require("express");
const cors = require("cors");
const dotenv = require("dotenv");

// Load environment variables
dotenv.config();

const requestLogger = require("./middleware/logger");
const { notFoundHandler, errorHandler } = require("./middleware/errorHandler");
const userRoutes = require("./routes/userRoutes");

const app = express();

// Standard Middlewares
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Log requests unless in test mode
if (process.env.NODE_ENV !== "test") {
  app.use(requestLogger);
}

// Health check endpoint (As specifically required)
app.get("/", (req, res) => {
  res.status(200).json({ message: "API is working" });
});

// Mount Users API routes
app.use("/api/users", userRoutes);

// Handle 404 Not Found
app.use(notFoundHandler);

// Centralized Error Handling Middleware
app.use(errorHandler);

module.exports = app;
