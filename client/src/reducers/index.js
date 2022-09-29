import { combineReducers } from 'redux';
import authReducer from './authReducer';
import customerReducer from './customerReducer';
import restaurantReducer from './restaurantReducer';
import driverReducer from './driverReducer';
import errorReducer from './errorReducer';


export default combineReducers({
    auth: authReducer,
    driver: driverReducer,
    customer: customerReducer,
    restaurant: restaurantReducer,
    errors: errorReducer
});
