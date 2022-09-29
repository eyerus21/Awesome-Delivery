import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import jwt_decode from "jwt-decode";
import setAuthToken from "./utils/setAuthToken";
import {
  logoutUser,
  setCurrentUser,
  getCurrentUser,
  clearCurrentUser
} from "./actions/authActions";

import { Provider } from "react-redux";
import store from "./store";

import Login from "./components/auth/Login";
import Register from "./components/auth/Register";
import Navbar from "./layouts/Navbar";
import RestaurantProtected from "./components/common/RestaurantProtected";
import DriverProtected from "./components/common/DriverProtected";
import CustomerProtected from "./components/common/CustomerProtected";

import RestaurantDashboard from "./components/restaurant/dashboard";
import CustomerDashboard from "./components/customer/dashboard";
import DriverDashboard from "./components/driver/dashboard";
import NotFound from "./components/not-found";

import "antd/dist/antd.css";
import "./App.css";
import landing from "./components/landing";

// Check for token
if (localStorage.jwtToken) {
  // Set auth token header auth
  setAuthToken(localStorage.jwtToken);
  // Decode token and get user info and expiration
  const decoded = jwt_decode(localStorage.jwtToken);
  // Set user and isAuthenticated
  store.dispatch(setCurrentUser(decoded));
  store.dispatch(getCurrentUser());
  // Check for expired token
  const currentTime = Date.now() / 1000;
  if (decoded.exp < currentTime) {
    // Logout user
    store.dispatch(logoutUser());

    // TODO: Clear current Profile
    store.dispatch(clearCurrentUser());
    // Redirect to login
    window.location.href = "/login";
  }
}

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Navbar />
            <Route exact path="/" component={landing} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/not-found" component={NotFound} />
            <Switch>
              <CustomerProtected exact path="/customer" component={CustomerDashboard}/>
              <RestaurantProtected exact path="/restaurant" component={RestaurantDashboard}/>
              <DriverProtected exact path="/driver" component={DriverDashboard}/>
            </Switch>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
