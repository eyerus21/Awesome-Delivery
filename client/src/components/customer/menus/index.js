import React, { useState } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import {
  getFilteredMenus,
  createOrder,
  populateMenusFromRestaurant
} from "../../../actions/customerActions";
import {
  List,
  Avatar,
  PageHeader,
  Divider,
  Input,
  Typography,
  InputNumber,
  Button,
  Row, Col
} from "antd";
import Spinner from "../../common/spinner";

const { Search } = Input;
const { Text } = Typography;

const AllMenus = (props) => {
  const [goBack, setGoBack] = useState(false);
  const { name, menus, id } = props.selectedRestaurant;
  const filteredMenus = props.customer.menus;
  let { loading } = props.customer;
  const { populateMenusFromRestaurant } = props;

  let menuContent;
  var allMenus = [];

  if(filteredMenus){
    allMenus = filteredMenus;
  }
  const { auth, createOrder, getFilteredMenus } = props;
  const { loggedInUser } = auth;

  const onChange = (value, item) => {
    let index = allMenus.findIndex((menu) => menu.id === item.id);
    allMenus[index].quantity = value;
  };

  const searchMenu = async(menuName) => {
    if (menuName !== null || menuName !== "") {
      loading = true;
      await getFilteredMenus(name, menuName);
      loading = false;
      setGoBack(true);
    }
  };

  const BackToList = async () => {
    populateMenusFromRestaurant(menus);
    setGoBack(false);
  };

  const submitOrder = () => {
    var finalOrder = {
      customerId: loggedInUser.userId,
      restaurantId: id,
      menus: [],
    };
    finalOrder["menus"] = allMenus.filter((menu) => menu.quantity > 0);
    createOrder( loggedInUser.userId, finalOrder);
  };

  if (allMenus === null || loading) {
    menuContent = <Spinner />;
  } else {
    if (allMenus && allMenus.length > 0) {
      menuContent = (
        <div>
          <div>
            <Search
              placeholder="input search text"
              style={{ width: "100%", marginBottom: 10 }}
              onSearch={searchMenu}
              enterButton
            />
            <List
              itemLayout="vertical"
              size="large"
              pagination={{
                onChange: (page) => {
                  // console.log(page);
                },
                pageSize: 3,
              }}
              dataSource={allMenus}
              renderItem={(item) => (
                <List.Item key={item.name}>
                  <List.Item.Meta
                    avatar={<Avatar src={item.imageUrl} />}
                    title={
                      <a
                        href="#/"
                        // onClick={() => {
                        //   props.pupulateRestaurant(item);
                        //   props.setSelectedMenuItem("Menu");
                        // }}
                      >
                        {item.name}
                      </a>
                    }
                    description={item.description}
                  />
                  <Text type="secondary">${item.price}</Text>
                  <div style={{ float: "right", marginBottom: "10px" }}>
                    <InputNumber
                      min={0}
                      max={10}
                      defaultValue={0}
                      onChange={(value) => {
                        onChange(value, item);
                      }}
                    />
                  </div>
                </List.Item>
              )}
            />
          </div>
          <div style={{ marginTop: 30 }}>
            <Divider plain orientation="left">
              Ready to order, please press the order button?
            </Divider>
            <Button
              type="primary"
              style={{ float: "right", width: "10%" }}
              onClick={submitOrder}
            >
              Order
            </Button>
          </div>
        </div>
      );
    } else {
      menuContent = <h4>No menus found ...</h4>;
    }
  }
  return (
    <div>
            <Row>
        <Col span={8}>
      <PageHeader
        className="site-page-header"
        onBack={() => props.goBack("Restaurants")}
        title="Back to restaurants"
      /></Col>
      {goBack && (
          <Col span={8} offset={8}>
            <Button
              type="primary"
              style={{ float: "right" }}
              // icon={<PlusOutlined />}
              onClick={BackToList}
            >
              Go back to list
            </Button>
          </Col>
        )}</Row>
      <div>
        <Divider plain orientation="left">
          {name}
        </Divider>
        {menuContent}
      </div>
    </div>
  );
};

AllMenus.propTypes = {
  getFilteredMenus: PropTypes.func.isRequired,
  populateMenusFromRestaurant: PropTypes.func.isRequired,
  createOrder: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
  customer: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  customer: state.customer,
  errors: state.errors,
});

export default connect(mapStateToProps, {
  createOrder,
  getFilteredMenus,
  populateMenusFromRestaurant
})(withRouter(AllMenus));
