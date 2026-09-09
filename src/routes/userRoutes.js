const express = require("express");
const router = express.Router();

const {
  getUsers,
  getUserById,
  createUser,
  updateUser,
  deleteUser
} = require("../controllers/userController");

const {
  validateUserId,
  validateCreateUser,
  validateUpdateUser
} = require("../middleware/validator");

// User routes
router.get("/", getUsers);
router.get("/:id", validateUserId, getUserById);
router.post("/", validateCreateUser, createUser);
router.put("/:id", validateUserId, validateUpdateUser, updateUser);
router.delete("/:id", validateUserId, deleteUser);

module.exports = router;
