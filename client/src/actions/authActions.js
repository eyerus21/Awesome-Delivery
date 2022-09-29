import axios from "axios";
import jwt_decode from "jwt-decode";
import BASE_URL from "../utils/baseUrl";
import setAuthToken from "../utils/setAuthToken";
import { CLEAR_CURRENT_USER, CLEAR_CUSTOMER, CLEAR_DRIVER, CLEAR_ERRORS, CLEAR_RESTAURANT, GET_CURRENT_USER, GET_ERRORS, SET_CURRENT_USER } from "./types";
import { notification } from "antd";

const successMessage = (successText) => {
  notification["success"]({
    message: "Confirmation",
    description: successText,
  });
};

const errorMessage = (errorText) => {
  notification["error"]({
    message: 'Failed to login!',
    description: errorText,
  });
};
// Register User
export const registerUser = (userData, history) => (dispatch) => {
  axios
    .post(BASE_URL + "/accounts/register", userData)
    .then((res) => {
      if (res.status === 200) {
        successMessage("Registered Successfully");
        history.push("/login");
      } else {
        const e = { registration: "Unable to register!" };
        dispatch({
          type: GET_ERRORS,
          payload: e,
        });
      }
    })
    .catch((err) => {
      const e = { registration: "Unable to register!" };
      dispatch({
        type: GET_ERRORS,
        payload: e,
      });
    });
};

// Login - Get User Token
export const loginUser = (userData) => (dispatch) => {
  axios
    .post(BASE_URL + "/accounts/login", userData)
    .then((res) => {
      if (res.data.success || res.status === 200) {
        dispatch(clearErrors());
        // Save token to localStorage
        const { tokenValue } = res.data;
        localStorage.setItem("jwtToken", tokenValue);
        // Set token to Auth header
        setAuthToken(tokenValue);
        // Decode token to get user data
        const decoded = jwt_decode(tokenValue);
        // Set current user
        dispatch(setCurrentUser(decoded));
        dispatch(getCurrentUser());
        
      } else {
        const e = { credentials:  "Invalid credentials!"};
        errorMessage("Invalid credentials!");
        dispatch({
          type: GET_ERRORS,
          payload: e,
        });
      }
    })
    .catch((err) => {
      const e = { credentials: "Invalid credentials!" };
      errorMessage("Invalid credentials!");
      dispatch({
        type: GET_ERRORS,
        payload: e,
      });
    });
};

export const getCurrentUser = () => (dispatch) => {
  axios
    .get(BASE_URL + "/accounts/current")
    .then((res) => {
      dispatch({
        type: GET_CURRENT_USER,
        payload: res.data,
      });
    })
    .catch((err) =>
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data,
      })
    );
};
// Set logged in user
export const setCurrentUser = (decoded) => {
  return {
    type: SET_CURRENT_USER,
    payload: decoded,
  };
};

// Set logged in user
export const clearErrors= () => {
  return {
    type: CLEAR_ERRORS
  };
};

// Set logged in user
export const clearCurrentUser = () => {
  return {
    type: CLEAR_CURRENT_USER
  };
};


// Clear driver
export const clearDriver = () => {
  return {
    type: CLEAR_DRIVER
  };
};

// Clear restaurant
export const clearRestaurant = () => {
  return {
    type: CLEAR_RESTAURANT
  };
};

// Clear customer
export const clearCustomer = () => {
  return {
    type: CLEAR_CUSTOMER
  };
};


// Log user out
export const logoutUser = (history) => (dispatch) => {
  // Remove token from localstorage
  localStorage.removeItem("jwtToken");
  // Remove auth header for future requests
  setAuthToken(false);
  // Set current user to {} which will set isAuthenticated to false
  dispatch(setCurrentUser({}));
  dispatch(clearErrors());
  dispatch(clearCurrentUser());
  dispatch(clearDriver());
  dispatch(clearCustomer());
  dispatch(clearRestaurant());

  history.push("/");
};
