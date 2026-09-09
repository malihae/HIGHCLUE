/**
 * Users Controller
 * Business logic for user resource handling
 */

const usersData = require("../data/usersData");

/**
 * GET /api/users
 * Returns list of all users
 */
const getUsers = (req, res) => {
  const users = usersData.getAllUsers();
  res.status(200).json({
    success: true,
    count: users.length,
    data: users
  });
};

/**
 * GET /api/users/:id
 * Returns a single user by numeric ID
 */
const getUserById = (req, res) => {
  const user = usersData.getUserById(req.numericId);

  if (!user) {
    return res.status(404).json({
      success: false,
      error: "Not Found",
      message: `User with ID ${req.numericId} does not exist.`
    });
  }

  res.status(200).json({
    success: true,
    data: user
  });
};

/**
 * POST /api/users
 * Creates a new user with name, email, age
 */
const createUser = (req, res) => {
  const { name, email, age } = req.body;

  // Optional: check if email is already in use
  const existingUser = usersData.getAllUsers().find(
    (u) => u.email.toLowerCase() === email.trim().toLowerCase()
  );

  if (existingUser) {
    return res.status(400).json({
      success: false,
      error: "Conflict / Validation Error",
      message: `A user with email '${email.trim().toLowerCase()}' already exists.`
    });
  }

  const newUser = usersData.createUser({ name, email, age });

  res.status(201).json({
    success: true,
    message: "User created successfully",
    data: newUser
  });
};

/**
 * PUT /api/users/:id
 * Updates an existing user by ID
 */
const updateUser = (req, res) => {
  const { name, email, age } = req.body;

  // Check if user exists first
  const existingUser = usersData.getUserById(req.numericId);
  if (!existingUser) {
    return res.status(404).json({
      success: false,
      error: "Not Found",
      message: `User with ID ${req.numericId} does not exist.`
    });
  }

  // If updating email, verify uniqueness against other users
  if (email) {
    const emailConflict = usersData.getAllUsers().find(
      (u) => u.id !== req.numericId && u.email.toLowerCase() === email.trim().toLowerCase()
    );
    if (emailConflict) {
      return res.status(400).json({
        success: false,
        error: "Conflict / Validation Error",
        message: `Email '${email.trim().toLowerCase()}' is already taken by another user.`
      });
    }
  }

  const updatedUser = usersData.updateUser(req.numericId, { name, email, age });

  res.status(200).json({
    success: true,
    message: "User updated successfully",
    data: updatedUser
  });
};

/**
 * DELETE /api/users/:id
 * Deletes user by ID
 */
const deleteUser = (req, res) => {
  const deletedUser = usersData.deleteUser(req.numericId);

  if (!deletedUser) {
    return res.status(404).json({
      success: false,
      error: "Not Found",
      message: `User with ID ${req.numericId} does not exist.`
    });
  }

  res.status(200).json({
    success: true,
    message: `User with ID ${req.numericId} deleted successfully.`,
    data: deletedUser
  });
};

module.exports = {
  getUsers,
  getUserById,
  createUser,
  updateUser,
  deleteUser
};
