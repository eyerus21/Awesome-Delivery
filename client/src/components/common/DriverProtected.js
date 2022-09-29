import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import NotFound from "../not-found";

const DriverProtected = ({ component: Component, auth, ...rest }) => (
  <Route
    {...rest}
    render={(props) =>
      auth.isAuthenticated === true ? (
        auth.loggedInUser && auth.loggedInUser.authorities && auth.loggedInUser.authorities[0].authority === "ROLE_DRIVER" ? (
          <Component {...props} />
        ) : (
          <NotFound />
        )
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);
DriverProtected.propTypes = {
  auth: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
});

export default connect(mapStateToProps)(DriverProtected);