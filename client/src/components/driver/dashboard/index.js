import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { Layout, Menu, Breadcrumb } from "antd";
import {
  ShoppingCartOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import {
 getOrders
} from "../../../actions/driverActions";
import AllOrders from "../orders";
const { SubMenu } = Menu;
const { Content, Sider } = Layout;

const Dashboard = (props) => {
  const { auth, getOrders} = props;
  const { loggedInUser } = auth;
  const { loading, orders, orderStatusLoading } = props.driver;
  const [selectedMenuItem, setSelectedMenuItem] = useState("All Orders");
  const userId = loggedInUser.userId;

  useEffect(() => {
    getOrders(userId);
  }, [getOrders, userId]);

  const switchComponent = (key) => {
    switch (key) {
      case "All Orders":
        return <AllOrders loading={loading} orders={orders} getOrders={getOrders} orderStatusLoading={orderStatusLoading}/>;
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
              onClick={(e) => setSelectedMenuItem(e.key)}
              defaultSelectedKeys={["1"]}
              defaultOpenKeys={["sub-order"]}
              style={{ height: "100%", borderRight: 0 }}
            >
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
  getOrders: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
  restaurant: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  driver: state.driver,
  errors: state.errors,
});

export default connect(mapStateToProps, {
  getOrders,
})(withRouter(Dashboard));