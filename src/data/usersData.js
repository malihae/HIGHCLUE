/**
 * In-Memory Users Data Store
 *
 * Each user object contains:
 * - id (number)
 * - name (string)
 * - email (string)
 * - age (number)
 */

let nextId = 1;

let initialUsers = [
  { id: 1, name: "Alice Johnson", email: "alice.johnson@example.com", age: 24 },
  { id: 2, name: "Bob Smith", email: "bob.smith@example.com", age: 29 },
  { id: 3, name: "Charlie Brown", email: "charlie.brown@example.com", age: 19 }
];

let users = JSON.parse(JSON.stringify(initialUsers));
nextId = 4;

/**
 * Get all users
 */
const getAllUsers = () => {
  return [...users];
};

/**
 * Find user by ID
 */
const getUserById = (id) => {
  const numericId = Number(id);
  return users.find((user) => user.id === numericId) || null;
};

/**
 * Create a new user
 */
const createUser = ({ name, email, age }) => {
  const newUser = {
    id: nextId++,
    name: name.trim(),
    email: email.trim().toLowerCase(),
    age: Number(age)
  };
  users.push(newUser);
  return { ...newUser };
};

/**
 * Update an existing user by ID
 */
const updateUser = (id, { name, email, age }) => {
  const numericId = Number(id);
  const userIndex = users.findIndex((user) => user.id === numericId);

  if (userIndex === -1) {
    return null;
  }

  const existingUser = users[userIndex];
  const updatedUser = {
    ...existingUser,
    ...(name !== undefined && { name: name.trim() }),
    ...(email !== undefined && { email: email.trim().toLowerCase() }),
    ...(age !== undefined && { age: Number(age) })
  };

  users[userIndex] = updatedUser;
  return { ...updatedUser };
};

/**
 * Delete a user by ID
 */
const deleteUser = (id) => {
  const numericId = Number(id);
  const userIndex = users.findIndex((user) => user.id === numericId);

  if (userIndex === -1) {
    return null;
  }

  const [deletedUser] = users.splice(userIndex, 1);
  return deletedUser;
};

/**
 * Reset store to initial state (useful for automated testing)
 */
const resetUsers = () => {
  users = JSON.parse(JSON.stringify(initialUsers));
  nextId = 4;
};

module.exports = {
  getAllUsers,
  getUserById,
  createUser,
  updateUser,
  deleteUser,
  resetUsers
};
