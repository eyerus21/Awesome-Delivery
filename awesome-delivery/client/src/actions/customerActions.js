import axios from "axios";
import BASE_URL from "../utils/baseUrl";
import {
  GET_ERRORS,
  GET_CUSTOMER_ORDERS,
  CREATE_ORDER,
  CUSTOMER_LOADING,
  GET_CUSTOMER_RESTAURANTS,
  GET_CUSTOMER_MENUS
} from "./types";

import { notification } from "antd";

const successMessage = (successText) => {
  notification["success"]({
    message: "Confirmation",
    description: successText,
  });
};

// Create an order
export const createOrder = (cusId,orderData) => (dispatch) => {
  axios
    .post(BASE_URL + `/customers/${cusId}/orders`, orderData)
    .then((res) => {
      if (res.status === 200) {
        dispatch(clearErrors());
        dispatch({
          type: CREATE_ORDER,
        });
        successMessage("Order placed successfully!");
      } else {
        dispatch({
          type: GET_ERRORS,
          payload: res.data,
        });
      }
    })
    .catch((err) =>
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data,
      })
    );
};

// Get all restaurants
export const getRestaurants = () => (dispatch) => {
  dispatch(setCustomerLoading());
  axios
    .get(BASE_URL + "/customers/restaurants")
    .then((res) => {
      dispatch({
        type: GET_CUSTOMER_RESTAURANTS,
        payload: res.data,
      });
    })
    .catch(() =>
      dispatch({
        type: GET_CUSTOMER_RESTAURANTS,
        payload: null,
      })
    );
};

// Get all fitered restaurants
export const getFilteredRestaurants = (restaurantName) => (dispatch) => {
  dispatch(setCustomerLoading());
  axios
    .get(BASE_URL + `/customers/restaurants/search?restaurant=${restaurantName}`)
    .then((res) => {
      dispatch({
        type: GET_CUSTOMER_RESTAURANTS,
        payload: res.data,
      });
    })
    .catch(() =>
      dispatch({
        type: GET_CUSTOMER_RESTAURANTS,
        payload: null,
      })
    );
};

// Get all fitered menus
export const getFilteredMenus = (restaurantName, menuName) => (dispatch) => {
  dispatch(setCustomerLoading());
  axios
    .get(BASE_URL + `/customers/restaurants/search?restaurant=${restaurantName}&menu=${menuName}`)
    .then((res) => {
      dispatch({
        type: GET_CUSTOMER_MENUS,
        payload: res.data,
      });
    })
    .catch(() =>
      dispatch({
        type: GET_CUSTOMER_MENUS,
        payload: null,
      })
    );
};

// Get all orders
export const getOrders = (cusId) => (dispatch) => {
  dispatch(setCustomerLoading());
  axios
    .get(BASE_URL + `/customers/${cusId}/orders`)
    .then((res) => {
      dispatch({
        type: GET_CUSTOMER_ORDERS,
        payload: res.data,
      });
    })
    .catch(() =>
      dispatch({
        type: GET_CUSTOMER_ORDERS,
        payload: null,
      })
    );
};

// Populate menus from selected restaurant
export const populateMenusFromRestaurant = (data) => {
  return {
    type: GET_CUSTOMER_MENUS,
    payload: data
  }
}

// Clear Errors
export const clearErrors = () => {
  return {
    type: GET_ERRORS,
    payload: {},
  };
};

// Menus and Order Loading
export const setCustomerLoading = () => {
  return {
    type: CUSTOMER_LOADING,
  };
};
