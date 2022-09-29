import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { getOrders } from "../../../actions/customerActions";
import { Table, Input, Result, Row, Col } from "antd";
import Spinner from "../../common/spinner";
import DetailData from "./DetailData";
import moment from "moment";
import _ from "lodash";
import { FrownOutlined } from "@ant-design/icons";
const { Search } = Input;

const AllOrders = (props) => {
  const { auth, getOrders } = props;
  const { loggedInUser } = auth;
  const loading = props.loading;
  const orders = props.orders;
  const [orderData, setOrderData] = useState(orders);
  const userId = loggedInUser.userId;
  // const loading = false;
  let orderContent;

  useEffect(() => {
    getOrders(userId);
  }, [getOrders, userId]);

  useEffect(() => {
    setOrderData(
      orders?.map((row, i) => {
        return {
          key: i + 1,
          orderedAt: moment(row.orderDate).format("MMMM DD, YYYY HH:mm"),
          menus: row.menus,
          totalPrice: row.totalPrice,
          status: row.status,
        };
      })
    );
  }, [orders]);

  const populateOrder = () => {
    setOrderData(
      orders?.map((row, i) => {
        return {
          key: i + 1,
          orderedAt: moment(row.orderDate).format("MMMM DD, YYYY HH:mm"),
          menus: row.menus,
          totalPrice: row.totalPrice,
          status: row.status,
        };
      })
    );
  };
  const filterOrder = (key) => {
    if (key === "") {
      populateOrder();
    } else {
      const filteredOrders = _.filter(
        orderData,
        (menu) =>
          // menu.menus.name.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          menu.orderedAt.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          menu.totalPrice.toString().indexOf(key.toLowerCase()) > -1 ||
          menu.status.toLowerCase().indexOf(key.toLowerCase()) > -1
      );
      setOrderData(filteredOrders);
    }
  };

  const columns = [
    {
      title: "Order Id",
      dataIndex: "key",
      key: "key",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.key - b.key,
    },
    {
      title: "Ordered At",
      dataIndex: "orderedAt",
      key: "orderedAt",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.orderedAt - b.orderedAt,
    },
    {
      title: "Total Price ($)",
      dataIndex: "totalPrice",
      key: "totalPrice",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.totalPrice - b.totalPrice,
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.status - b.status,
    },
  ];

  const updateAllOrders = (evt) => {
    filterOrder(evt.target.value);
  };

  if (orderData === null || (loading)) {
    orderContent = <Spinner />;
  } else {
    if (orderData && orderData.length > 0) {
      orderContent = (
        <div>
          <Table
            style={{ marginTop: "20px" }}
            columns={columns}
            expandable={{
              expandedRowRender: (record) => <DetailData data={record.menus} />,
              rowExpandable: (record) => record.name !== "Not Expandable",
            }}
            dataSource={orderData}
          />
        </div>
      );
    } else {
      orderContent = (
        <div>
          <Row>
            <Col span={8} offset={8}>
              <Result
                icon={<FrownOutlined />}
                title="Sorry, no orders found!"
              />
            </Col>
          </Row>
        </div>
      );
    }
  }
  return (
    <div>
      {" "}
      <Search
        placeholder="Search your orders"
        onChange={updateAllOrders}
        style={{ width: 520, marginBottom: 10 }}
        enterButton
      />
      {orderContent}
    </div>
  );
};

AllOrders.propTypes = {
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

export default connect(mapStateToProps, { getOrders })(withRouter(AllOrders));
