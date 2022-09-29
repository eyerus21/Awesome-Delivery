import axios from "axios";
import BASE_URL from "../utils/baseUrl";
import {
  GET_ERRORS,
  GET_RESTAURANT_MENUS,
  ADD_RESTAURANT_MENU,
  UPDATE_RESTAURANT_MENU,
  DELETE_RESTAURANT_MENU,
  GET_RESTAURANT_ORDERS,
  RESTAURANT_ORDER_ACCEPT,
  RESTAURANT_ORDER_READY,
  CANCEL_RESTAURANT_ORDER,
  RESTAURANT_LOADING,
  RESTAURANT_ORDER_STATUS
} from "./types";

import { notification } from "antd";

const successMessage = (successText) => {
  notification["success"]({
    message: "Confirmation",
    description: successText,
  });
};

// Create a Menu
export const addMenu = (resId, menuData) => (dispatch) => {
  axios
    .post(BASE_URL + `/restaurants/${resId}/menus`, menuData)
    .then((res) => {
      if (res.status === 200) {
        dispatch(clearErrors);
        dispatch({
          type: ADD_RESTAURANT_MENU,
        });
        successMessage("Menu added successfully!");
        dispatch(getMenus(resId));
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

// Get all menus
export const getMenus = (resId) => (dispatch) => {
  dispatch(setRestaurantLoading());
  axios
    .get(BASE_URL + `/restaurants/${resId}/menus`)
    .then((res) => {
      dispatch({
        type: GET_RESTAURANT_MENUS,
        payload: res.data,
      });
    })
    .catch(() =>
      dispatch({
        type: GET_RESTAURANT_MENUS,
        payload: null,
      })
    );
};

// Edit menus
export const editMenu = (resId, menuId, menu) => (dispatch) => {
  dispatch(clearErrors);
  axios
    .put(BASE_URL + `/restaurants/${resId}/menus/${menuId}`, menu)
    .then((res) => {
      if (res.status === 200) {
        dispatch({
          type: UPDATE_RESTAURANT_MENU,
          payload: res.data,
        });
        successMessage("Menu updated successfully!");
        dispatch(getMenus(resId));
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

// Delete menu
export const deleteMenu = (resId, menuId) => (dispatch) => {
  axios
    .delete(`${BASE_URL}/restaurants/${resId}/menus/${menuId}`)
    .then((res) => {
      dispatch(clearErrors);
      if (res.status === 200) {
        dispatch({
          type: DELETE_RESTAURANT_MENU,
          payload: res.data,
        });
        successMessage("Menu deleted Successfully!");
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

// Get all orders
export const getOrders = (resId) => (dispatch) => {
  dispatch(setRestaurantLoading());
  axios
    .get(BASE_URL + `/restaurants/${resId}/orders`)
    .then((res) => {
      dispatch({
        type: GET_RESTAURANT_ORDERS,
        payload: res.data,
      });
    })
    .catch(() =>
      dispatch({
        type: GET_RESTAURANT_ORDERS,
        payload: null,
      })
    );
};

// Order ready for pickup
export const acceptOrder = (resId, orderId) => (dispatch) => {
  dispatch(setRestaurantOrderStatus());
  axios
    .put(`${BASE_URL}/restaurants/${resId}/orders/${orderId}/accept`)
    .then((res) => {
      dispatch(clearErrors);
      if (res.status === 200) {
        dispatch({
          type: RESTAURANT_ORDER_ACCEPT,
          payload: res.data,
        });
        dispatch(getOrders(resId));
        successMessage("Order accepted successfully!");
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

// Reject order
export const rejectOrder = (resId, orderId) => (dispatch) => {
  dispatch(setRestaurantOrderStatus());
  axios
    .put(`${BASE_URL}/restaurants/${resId}/orders/${orderId}/reject`)
    .then((res) => {
      dispatch(clearErrors);
      if (res.status === 200) {
        dispatch({
          type: CANCEL_RESTAURANT_ORDER,
          payload: res.data,
        });
        dispatch(getOrders(resId));
        successMessage("Order rejected successfully!");
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

// Order ready for pickup
export const readyOrder = (resId, orderId) => (dispatch) => {
  dispatch(setRestaurantOrderStatus());
  axios
    .put(`${BASE_URL}/restaurants/${resId}/orders/${orderId}/ready`)
    .then((res) => {
      dispatch(clearErrors);
      if (res.status === 200) {
        dispatch({
          type: RESTAURANT_ORDER_READY,
          payload: res.data,
        });
        dispatch(getOrders(resId));
        successMessage("Order ready for pickup!");
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

// Clear Errors
export const clearErrors = () => {
  return {
    type: GET_ERRORS,
    payload: {},
  };
};

// Menus Change Loading
export const setRestaurantOrderStatus = () => {
  return {
    type: RESTAURANT_ORDER_STATUS,
  };
};

// Menus and Orders Loading
export const setRestaurantLoading = () => {
  return {
    type: RESTAURANT_LOADING,
  };
};
