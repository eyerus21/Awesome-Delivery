import React, { Component } from "react";
import { Menu, Typography, Avatar, Image } from "antd";
import { LoginOutlined, UserAddOutlined } from "@ant-design/icons";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logoutUser } from "../actions/authActions";

const { Title } = Typography;
class Navbar extends Component {
  state = {
    current: "",
    redirectLink: "/",
    userType: "",
  };

  handleClick = (e) => {
    this.setState({ current: e.key });
  };

  onLogoutClick(e) {
    e.preventDefault();
    this.props.logoutUser(this.props.history);
  }

  componentDidMount() {
    if (this.props.auth.isAuthenticated) {
      if (
        this.props.auth.loggedInUser &&
        this.props.auth.loggedInUser.authorities
      ) {
        this.setState({
          userType:
            this.props.auth.loggedInUser &&
            this.props.auth.loggedInUser.authorities[0].authority,
        });
      }

      if (this.state.userType === "ROLE_RESTAURANT") {
        this.setState({ redirectLink: "/restaurant" });
      }
      if (this.state.userType === "ROLE_CUSTOMER") {
        this.setState({ redirectLink: "/customer" });
      }
      if (this.state.userType === "ROLE_DRIVER") {
        this.setState({ redirectLink: "/driver" });
      }
    }
  }

  render() {
    const { current, redirectLink } = this.state;
    const { isAuthenticated, loggedInUser } = this.props.auth;
    const authLinks = (
      <Menu
        onClick={this.handleClick}
        selectedKeys={[current]}
        mode="horizontal"
        theme="dark"
      >
        <Menu.Item key="Title">
          <Link to={redirectLink}>
            <Title
              style={{ marginTop: 8, float: "left", color: "white" }}
              level={4}
            >
              {loggedInUser &&
              loggedInUser.authorities &&
              loggedInUser.authorities[0].authority === "ROLE_RESTAURANT"
                ? "Restaurant Admin Portal"
                : loggedInUser &&
                  loggedInUser.authorities &&
                  loggedInUser.authorities[0].authority === "ROLE_CUSTOMER"
                ? "Customer Portal"
                : loggedInUser.authorities &&
                  loggedInUser.authorities &&
                  loggedInUser.authorities[0].authority === "ROLE_DRIVER"
                ? "Driver Portal"
                : null}
            </Title>
          </Link>
        </Menu.Item>
        <Menu.Item
          key="logout"
          icon={<LoginOutlined />}
          style={{ float: "right" }}
        >
          <a href="/#" onClick={this.onLogoutClick.bind(this)}>
            Logout
          </a>
        </Menu.Item>
        <Title
          style={{
            marginTop: 10,
            marginRight: 5,
            float: "right",
            color: "white",
          }}
          level={5}
        >
          <Avatar
            size={30}
            src={
              <Image
                src="https://joeschmoe.io/api/v1/male/jack"
                style={{ width: 32 }}
              />
            }
          />
          {loggedInUser && loggedInUser.username && loggedInUser.username}
        </Title>
      </Menu>
    );

    const guestLinks = (
      <Menu
        onClick={this.handleClick}
        selectedKeys={[current]}
        mode="horizontal"
        theme="dark"
      >
        <Menu.Item key="Title">
          <Link to={isAuthenticated ? "/dashboard" : "/"}>
            <Title
              style={{ marginTop: 8, float: "left", color: "white" }}
              level={4}
            >
              Awesome Delivery
            </Title>
          </Link>
        </Menu.Item>

        <Menu.Item
          key="register"
          icon={<UserAddOutlined />}
          style={{ float: "right" }}
        >
          <Link to="/register">Sign up</Link>
        </Menu.Item>
        <Menu.Item
          key="login"
          icon={<LoginOutlined />}
          style={{ float: "right" }}
        >
          <Link to="/login">Login</Link>
        </Menu.Item>
      </Menu>
    );
    return isAuthenticated ? authLinks : guestLinks;
  }
}

Navbar.propTypes = {
  logoutUser: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
};
const mapStateToProps = (state) => ({
  auth: state.auth,
});

export default connect(mapStateToProps, { logoutUser })(withRouter(Navbar));
