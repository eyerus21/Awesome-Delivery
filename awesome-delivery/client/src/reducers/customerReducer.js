import {
  CUSTOMER_LOADING,
  GET_CUSTOMER_RESTAURANTS,
  GET_CUSTOMER_MENUS,
  GET_CUSTOMER_ORDERS,
  CLEAR_CUSTOMER,
} from "../actions/types";

const initialState = {
  orders: null,
  restaurants: null,
  menus: null,
  loading: false,
};

// eslint-disable-next-line
export default function (state = initialState, action) {
  switch (action.type) {
    case CUSTOMER_LOADING:
      return {
        ...state,
        loading: true,
      };
    case GET_CUSTOMER_RESTAURANTS:
      return {
        ...state,
        restaurants: action.payload,
        loading: false,
      };
    case GET_CUSTOMER_MENUS:
      return {
        ...state,
        menus: action.payload,
        loading: false,
      };
    case GET_CUSTOMER_ORDERS:
      return {
        ...state,
        orders: action.payload,
        loading: false,
      };
    case CLEAR_CUSTOMER:{
      return {
        ...state,
        orders: null,
        restaurants: null,
        menus: null,
        loading: false
      }
    }
    default:
      return state;
  }
}
