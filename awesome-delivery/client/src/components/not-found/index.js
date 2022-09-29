import React from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { Result, Button } from "antd";

const NotFound = (props) => {
  const { auth } = props;
  let redirectLink = "/";
  if (auth.authenticated && auth.loggedInUser && auth.loggedInUser.authorities) {
    let userType = auth.loggedInUser && auth.loggedInUser.authorities[0].authority;
    if (userType === "ROLE_RESTAURANT") {
      redirectLink = "restaurant";
    } else if (userType === "ROLE_CUSTOMER") {
      redirectLink = "customer";
    } else if (userType === "ROLE_DRIVER") {
      redirectLink = "driver";
    } else {
      redirectLink = "/";
    }
  }

  return (
    <div>
      <Result
        status="404"
        title="404"
        subTitle="Sorry, the page you visited does not exist."
        extra={
          <Link to={redirectLink}>
            <Button type="primary">Back Home</Button>
          </Link>
        }
      />
    </div>
  );
};

NotFound.propTypes = {
  auth: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
});

export default connect(mapStateToProps)(NotFound);
