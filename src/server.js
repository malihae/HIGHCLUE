const app = require("./app");

const PORT = process.env.PORT || 5000;

const server = app.listen(PORT, () => {
  console.log("=========================================");
  console.log(`🚀 Express REST API Server running on port ${PORT}`);
  console.log(`👉 Health check:   http://localhost:${PORT}/`);
  console.log(`👉 Users endpoint: http://localhost:${PORT}/api/users`);
  console.log(`👉 Environment:    ${process.env.NODE_ENV || "development"}`);
  console.log("=========================================");
});

// Handle graceful termination
process.on("SIGTERM", () => {
  console.log("SIGTERM signal received: closing HTTP server");
  server.close(() => {
    console.log("HTTP server closed");
  });
});

process.on("SIGINT", () => {
  console.log("SIGINT signal received: closing HTTP server");
  server.close(() => {
    console.log("HTTP server closed");
    process.exit(0);
  });
});

module.exports = server;
