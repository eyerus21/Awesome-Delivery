import axios from "axios";
import BASE_URL from "../utils/baseUrl";
import {
  GET_ERRORS,
  ORDER_DELIVERED,
  ORDER_PICKED,
  DRIVER_ORDERS_LOADING,
  GET_DRIVER_ORDERS,
  DRIVER_ORDER_STATUS,
} from "./types";

import { notification } from "antd";

const successMessage = (successText) => {
  notification["success"]({
    message: "Confirmation",
    description: successText,
  });
};

// Pick Order
export const pickOrder = (driId, orderId, orderData) => (dispatch) => {
  dispatch(setRestaurantOrderStatus());
  axios
    .put(BASE_URL + `/drivers/${driId}/orders/${orderId}/pick`, orderData)
    .then((res) => {
      if (res.status === 200) {
        dispatch(clearErrors);
        successMessage("Order picked successfully!");
        dispatch(getOrders(driId));
        dispatch({
          type: ORDER_PICKED,
        });
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

// Deliver Order
export const deliverOrder = (driId, orderId, orderData) => (dispatch) => {
  dispatch(setRestaurantOrderStatus());
  axios
    .put(BASE_URL + `/drivers/${driId}/orders/${orderId}/deliver`, orderData)
    .then((res) => {
      if (res.status === 200) {
        dispatch(clearErrors);
        successMessage("Order delivered successfully!");
        dispatch({
          type: ORDER_DELIVERED,
        });
        dispatch(getOrders(driId));
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

// Get All Orders
export const getOrders = (driId) => (dispatch) => {
  dispatch(setDriversLoading());
  axios
    .get(BASE_URL + `/drivers/${driId}/orders`)
    .then((res) => {
      dispatch({
        type: GET_DRIVER_ORDERS,
        payload: res.data,
      });
    })
    .catch(() =>
      dispatch({
        type: GET_DRIVER_ORDERS,
        payload: null,
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
    type: DRIVER_ORDER_STATUS,
  };
};

// Orders Loading
export const setDriversLoading = () => {
  return {
    type: DRIVER_ORDERS_LOADING,
  };
};
