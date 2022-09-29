import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import {
  getRestaurants,
  getFilteredRestaurants,
} from "../../../actions/customerActions";
import {
  List,
  Avatar,
  Space,
  PageHeader,
  Divider,
  Input,
  Rate,
  Typography,
  Row,
  Col,
  Button,
} from "antd";
import { MessageOutlined, LikeOutlined } from "@ant-design/icons";
import Spinner from "../../common/spinner";

const { Search } = Input;
const { Text } = Typography;

const IconText = ({ icon, text }) => (
  <Space>
    {React.createElement(icon)}
    {text}
  </Space>
);

const AllRestaurants = (props) => {
  const { getRestaurants, getFilteredRestaurants } = props;
  const { restaurants, loading } = props.customer;
  const [goBack, setGoBack] = useState(false);

  let restaurantContent;

  useEffect(() => {
    getRestaurants();
  }, [getRestaurants]);

  const searchRestaurant = (restaurantName) => {
    if (restaurantName !== null || restaurantName !== "") {
      getFilteredRestaurants(restaurantName);
      setGoBack(true);
    }
  };

  const BackToList = async () => {
    getRestaurants();
    setGoBack(false);
  };

  if (restaurants === null || loading) {
    restaurantContent = <Spinner />;
  } else {
    if (restaurants && restaurants.length > 0) {
      restaurantContent = (
        <div>
          <Search
            placeholder="Search restaurants"
            onSearch={searchRestaurant}
            style={{ width: "100%", marginBottom: 10 }}
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
            dataSource={restaurants}
            renderItem={(item) => (
              <List.Item
                key={item.title}
                actions={[
                  <IconText
                    icon={LikeOutlined}
                    text="156"
                    key="list-vertical-like-o"
                  />,
                  <IconText
                    icon={MessageOutlined}
                    text="2"
                    key="list-vertical-message"
                  />,
                ]}
                extra={
                  <img
                    width={272}
                    alt="logo"
                    src="http://cdn.cnn.com/cnnnext/dam/assets/190710135245-12-waterfront-restaurants.jpg"
                  />
                }
              >
                <List.Item.Meta
                  avatar={
                    <Avatar src="http://cdn.cnn.com/cnnnext/dam/assets/190710135245-12-waterfront-restaurants.jpg" />
                  }
                  title={
                    <a
                      href="#/"
                      onClick={() => {
                        props.pupulateRestaurant(item);
                        props.setSelectedMenuItem("Menu");
                      }}
                    >
                      {item.name}
                    </a>
                  }
                  description={item.address.country + ", " + item.address.state}
                />
                <Text type="secondary">
                  {item.address.city + " " + item.address.street}
                </Text>
                <p></p>
                <Rate disabled defaultValue={Math.random() * 5} />
              </List.Item>
            )}
          />
        </div>
      );
    } else {
      restaurantContent = <h4>No restaurants found ...</h4>;
    }
  }
  return (
    <div>
      <Row>
        <Col span={8}>
          <PageHeader
            className="site-page-header"
            title="DISCOVER MORE"
            subTitle="Look for restaurants and foods"
          />
          <Divider plain orientation="left">
            Restaurants
          </Divider>
        </Col>
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
        )}
      </Row>
      {restaurantContent}
    </div>
  );
};

AllRestaurants.propTypes = {
  getRestaurants: PropTypes.func.isRequired,
  getFilteredRestaurants: PropTypes.func.isRequired,
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
  getRestaurants,
  getFilteredRestaurants,
})(withRouter(AllRestaurants));
