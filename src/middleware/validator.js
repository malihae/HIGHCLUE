/**
 * Request Validation Middleware for Users API
 */

const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

/**
 * Validates the numeric :id URL parameter
 */
const validateUserId = (req, res, next) => {
  const { id } = req.params;
  const numericId = Number(id);

  if (!Number.isInteger(numericId) || numericId <= 0) {
    return res.status(400).json({
      success: false,
      error: "Bad Request",
      message: "User ID must be a positive whole integer."
    });
  }

  req.numericId = numericId;
  next();
};

/**
 * Validates request body for POST /api/users
 * Required fields: name (string), email (valid email string), age (number 1..120)
 */
const validateCreateUser = (req, res, next) => {
  const { name, email, age } = req.body || {};
  const errors = [];

  // Validate name
  if (name === undefined || name === null || typeof name !== "string" || name.trim().length === 0) {
    errors.push("Field 'name' is required and must be a non-empty string.");
  } else if (name.trim().length < 2) {
    errors.push("Field 'name' must be at least 2 characters long.");
  } else if (name.trim().length > 50) {
    errors.push("Field 'name' must not exceed 50 characters.");
  }

  // Validate email
  if (email === undefined || email === null || typeof email !== "string" || email.trim().length === 0) {
    errors.push("Field 'email' is required and must be a non-empty string.");
  } else if (!EMAIL_REGEX.test(email.trim())) {
    errors.push("Field 'email' must be a valid email address (e.g., user@example.com).");
  }

  // Validate age
  if (age === undefined || age === null) {
    errors.push("Field 'age' is required.");
  } else {
    const numericAge = Number(age);
    if (!Number.isInteger(numericAge) || numericAge < 1 || numericAge > 120) {
      errors.push("Field 'age' must be a whole number between 1 and 120.");
    }
  }

  if (errors.length > 0) {
    return res.status(400).json({
      success: false,
      error: "Validation Error",
      messages: errors
    });
  }

  next();
};

/**
 * Validates request body for PUT /api/users/:id
 * Optional fields (at least one required): name, email, age
 */
const validateUpdateUser = (req, res, next) => {
  const { name, email, age } = req.body || {};
  const errors = [];

  if (name === undefined && email === undefined && age === undefined) {
    return res.status(400).json({
      success: false,
      error: "Validation Error",
      messages: ["At least one field ('name', 'email', or 'age') must be provided to update."]
    });
  }

  // If name is provided
  if (name !== undefined) {
    if (typeof name !== "string" || name.trim().length === 0) {
      errors.push("Field 'name' must be a non-empty string.");
    } else if (name.trim().length < 2) {
      errors.push("Field 'name' must be at least 2 characters long.");
    } else if (name.trim().length > 50) {
      errors.push("Field 'name' must not exceed 50 characters.");
    }
  }

  // If email is provided
  if (email !== undefined) {
    if (typeof email !== "string" || !EMAIL_REGEX.test(email.trim())) {
      errors.push("Field 'email' must be a valid email address (e.g., user@example.com).");
    }
  }

  // If age is provided
  if (age !== undefined) {
    const numericAge = Number(age);
    if (!Number.isInteger(numericAge) || numericAge < 1 || numericAge > 120) {
      errors.push("Field 'age' must be a whole number between 1 and 120.");
    }
  }

  if (errors.length > 0) {
    return res.status(400).json({
      success: false,
      error: "Validation Error",
      messages: errors
    });
  }

  next();
};

module.exports = {
  validateUserId,
  validateCreateUser,
  validateUpdateUser
};
