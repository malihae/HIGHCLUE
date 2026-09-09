/**
 * Error Handling Middleware
 */

/**
 * 404 Handler for undefined routes
 */
const notFoundHandler = (req, res, next) => {
  res.status(404).json({
    success: false,
    error: "Not Found",
    message: `Cannot ${req.method} ${req.originalUrl}`
  });
};

/**
 * Centralized Error Handler
 */
const errorHandler = (err, req, res, next) => {
  console.error("Unhandled Error:", err);

  const statusCode = err.statusCode || err.status || 500;
  const message = err.message || "Internal Server Error";

  res.status(statusCode).json({
    success: false,
    error: statusCode === 500 ? "Internal Server Error" : "Error",
    message: message
  });
};

module.exports = {
  notFoundHandler,
  errorHandler
};
