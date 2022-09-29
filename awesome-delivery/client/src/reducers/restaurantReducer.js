import {
  RESTAURANT_LOADING,
  GET_RESTAURANT_MENUS,
  GET_RESTAURANT_ORDERS,
  CLEAR_RESTAURANT,
  RESTAURANT_ORDER_STATUS,
  RESTAURANT_ORDER_READY,
  RESTAURANT_ORDER_ACCEPT,
} from "../actions/types";

const initialState = {
  menus: null,
  orders: null,
  loading: false,
  orderStatusLoading: false,
};

// eslint-disable-next-line
export default function (state = initialState, action) {
  switch (action.type) {
    case RESTAURANT_LOADING:
      return {
        ...state,
        loading: true,
      };
    case GET_RESTAURANT_ORDERS:
      return {
        ...state,
        orders: action.payload,
        loading: false,
      };
    case GET_RESTAURANT_MENUS:
      return {
        ...state,
        menus: action.payload,
        loading: false,
      };
    case CLEAR_RESTAURANT:
      return {
        ...state,
        menus: null,
        orders: null,
        loading: false,
      };
    case RESTAURANT_ORDER_STATUS:
      return {
        ...state,
        orderStatusLoading: true,
      };
    case RESTAURANT_ORDER_READY:
      return {
        ...state,
        orderStatusLoading: false,
      };
    case RESTAURANT_ORDER_ACCEPT:
      return {
        ...state,
        orderStatusLoading: false,
      };
    default:
      return state;
  }
}
