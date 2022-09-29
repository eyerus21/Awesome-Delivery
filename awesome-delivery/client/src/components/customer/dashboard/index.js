import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { Layout, Menu, Breadcrumb } from "antd";
import {
  LaptopOutlined,
  ShoppingCartOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import Restaurants from "../restaurants";
import AllOrders from "../orders";
import Menus from "../menus";
import { populateMenusFromRestaurant, getOrders } from "../../../actions/customerActions";

// import store from "../../../store";
// import { GET_CUSTOMER_MENUS } from "../../../actions/types";

const { SubMenu } = Menu;
const { Content, Sider } = Layout;

const Dashboard = (props) => {
  const { populateMenusFromRestaurant } = props;
  const { auth, getOrders} = props;
  const { loggedInUser } = auth;
  const { loading, orders } = props.customer;
  const userId = loggedInUser.userId;

  const [selectedMenuItem, setSelectedMenuItem] = useState("Restaurants");
  const [selectedRestaurant, setSelectedRestaurant] = useState({});

  useEffect(() => {
    getOrders(userId);
  }, [getOrders, userId]);

  const populateRestaurant = (restaurant) => {
    setSelectedRestaurant(restaurant);
    populateMenusFromRestaurant(restaurant.menus);
  }

  const goBack =(key) => {
    setSelectedMenuItem(key);
  }

  const switchComponent = (key) => {
    switch (key) {
      case "Restaurants":
        return <Restaurants setSelectedMenuItem= {setSelectedMenuItem} pupulateRestaurant= {populateRestaurant}/>;
      case "My Orders":
        return <AllOrders orders={orders} loading={loading}/>;
      case "Menu":
        return <Menus goBack= {goBack} selectedRestaurant={selectedRestaurant}/>;
      default:
        break;
    }
  };
  return (
    <div>
      <Layout>
        <Layout>
          <Sider width={200} className="site-layout-background">
            <Menu
              mode="inline"
              selectedKeys={selectedMenuItem}
              // mode="horizontal"
              onClick={(e) => setSelectedMenuItem(e.key)}
              defaultSelectedKeys={["1"]}
              defaultOpenKeys={["sub-food", "sub-order"]}
              style={{ height: "100%", borderRight: 0 }}
            >
              <SubMenu
                key="sub-food"
                icon={<LaptopOutlined />}
                title="Foods"
              >
                <Menu.Item key="Restaurants" icon={<UnorderedListOutlined />}>
                  Restaurants
                </Menu.Item>
              </SubMenu>
              <SubMenu
                key="sub-order"
                icon={<ShoppingCartOutlined />}
                title="Orders"
              >
                <Menu.Item key="My Orders" icon={<UnorderedListOutlined />}>
                  My Orders
                </Menu.Item>
              </SubMenu>
            </Menu>
          </Sider>
          <Layout style={{ padding: "0 24px 24px" }}>
            <Breadcrumb style={{ margin: "16px 0" }}>
              <Breadcrumb.Item>Home</Breadcrumb.Item>
              <Breadcrumb.Item>{selectedMenuItem}</Breadcrumb.Item>
            </Breadcrumb>
            <Content
              className="site-layout-background"
              style={{
                padding: 24,
                margin: 0,
                height: "900px",
                minHeight: "auto",
              }}
            >
              {switchComponent(selectedMenuItem)}
            </Content>
          </Layout>
        </Layout>
      </Layout>
    </div>
  );
};


Dashboard.propTypes = {
  populateMenusFromRestaurant: PropTypes.func.isRequired,
  getOrders: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
  customer: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  customer: state.customer,
  errors: state.errors,
});

export default connect(mapStateToProps, { getOrders, populateMenusFromRestaurant })(
  withRouter(Dashboard)
);
