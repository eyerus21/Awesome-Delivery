import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";

import { Button, Divider } from "antd";

class Landing extends Component {
  componentDidMount() {
    if (this.props.auth.isAuthenticated) {
      let userType;
      if (
        this.props.auth.loggedInUser &&
        this.props.auth.loggedInUser.authorities
      ) {
        userType =
          this.props.auth.loggedInUser &&
          this.props.auth.loggedInUser.authorities[0].authority;
      }

      if (userType === "ROLE_RESTAURANT") {
        this.props.history.push("/restaurant");
      }
      if (userType === "ROLE_CUSTOMER") {
        this.props.history.push("/customer");
      }
      if (userType === "ROLE_DRIVER") {
        this.props.history.push("/driver");
      }
    }
  }

  render() {
    return (
      <div className="landing">
        <div className="dark-overlay landing-inner text-light">
          <div className="container">
            <div className="row">
              <div className="col-md-12 text-center">
                <h1
                  className="display-3 mb-4"
                  style={{ color: "white", marginTop: "145px" }}
                >
                  Awesome Delivery
                </h1>
                <p className="lead" style={{ color: "white" }}>
                  {" "}
                  Create your account, choose a restaurant and menus, then place
                  your order and we'll deliver right away!
                </p>
                <Divider style={{ color: "white" }}>
                  <p className="lead">Get Started</p>
                </Divider>
                <Link to="/register">
                  {" "}
                  <Button
                    type="primary"
                    size="large"
                    style={{ width: 100, marginRight: 20 }}
                  >
                    <p>Sign up</p>
                  </Button>
                </Link>
                <Link to="/login">
                  {" "}
                  <Button type="block" size="large" style={{ width: 100 }}>
                    <p>Login</p>
                  </Button>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Landing.propTypes = {
  auth: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
});

export default connect(mapStateToProps)(Landing);
