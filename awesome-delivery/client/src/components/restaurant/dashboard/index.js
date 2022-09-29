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
import {
  getMenus, getOrders
} from "../../../actions/restaurantAction";
import AllMenus from "../menus";
import AllOrders from "../orders";
const { SubMenu } = Menu;
const { Content, Sider } = Layout;

const Dashboard = (props) => {
  const { auth, getMenus, getOrders} = props;
  const { loggedInUser } = auth;
  const { menus, loading, orders, orderStatusLoading } = props.restaurant;
  const userId = loggedInUser.userId;
  const [selectedMenuItem, setSelectedMenuItem] = useState("All Menus");

  useEffect(() => {
    getMenus(userId);
    getOrders(userId);
  }, [userId, getMenus, getOrders]);


  const switchComponent = (key) => {
    switch (key) {
      case "All Menus":
        return <AllMenus menus={menus} loading={loading} getMenus={getMenus}/>;
      case "All Orders":
        return <AllOrders orders={orders} loading={loading} orderStatusLoading={orderStatusLoading}/>;
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
              defaultOpenKeys={["sub-menu", "sub-order"]}
              style={{ height: "100%", borderRight: 0 }}
            >
              <SubMenu
                key="sub-menu"
                icon={<LaptopOutlined />}
                title="Menus"
              >
                <Menu.Item key="All Menus" icon={<UnorderedListOutlined />}>
                  All Menus
                </Menu.Item>
              </SubMenu>
              <SubMenu
                key="sub-order"
                icon={<ShoppingCartOutlined />}
                title="Orders"
              >
                <Menu.Item key="All Orders" icon={<UnorderedListOutlined />}>
                  All Orders
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
                height: "700px",
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
  getMenus: PropTypes.func.isRequired,
  getOrders: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
  restaurant: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  restaurant: state.restaurant,
  errors: state.errors,
});

export default connect(mapStateToProps, {
  getMenus,
  getOrders,
})(withRouter(Dashboard));
