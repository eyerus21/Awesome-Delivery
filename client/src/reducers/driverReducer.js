import {
  GET_DRIVER_ORDERS,
  DRIVER_ORDERS_LOADING,
  DRIVER_ORDER_STATUS,
  CLEAR_DRIVER,
  ORDER_DELIVERED,
  ORDER_PICKED,
} from "../actions/types";

const initialState = {
  orders: null,
  loading: false,
  orderStatusLoading: false,
};

// eslint-disable-next-line
export default function (state = initialState, action) {
  switch (action.type) {
    case DRIVER_ORDERS_LOADING:
      return {
        ...state,
        loading: true,
      };

    case GET_DRIVER_ORDERS:
      return {
        ...state,
        orders: action.payload,
        loading: false,
      };
    case CLEAR_DRIVER:
      return {
        ...state,
        orders: null,
        loading: false,
      };
    case ORDER_DELIVERED:
      return {
        ...state,
        orderStatusLoading: false,
      };
    case ORDER_PICKED:
      return {
        ...state,
        orderStatusLoading: false,
      };
    case DRIVER_ORDER_STATUS:
      return {
        ...state,
        orderStatusLoading: true,
      };

    default:
      return state;
  }
}
