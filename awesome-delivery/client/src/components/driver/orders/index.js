import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { Table, Input, Modal, Button, Spin, Result, Row, Col } from "antd";
import {
  getOrders,
  pickOrder,
  deliverOrder,
} from "../../../actions/driverActions";
import { FrownOutlined } from "@ant-design/icons";
import Spinner from "../../common/spinner";
import DetailData from "./DetailData";
import _ from "lodash";
import moment from "moment";
const { Search } = Input;

const AllOrders = (props) => {
  const { auth, pickOrder, deliverOrder } = props;
  const { loggedInUser } = auth;
  const loading = props.loading;
  const orders = props.orders;
  const isStatusChanged = props.orderStatusLoading;
  let orderContent;
  const userId = loggedInUser.userId;
  const [orderData, setOrderData] = useState(orders);

  const [orderId, setOrderId] = useState("");
  const [orderMode, setOrderMode] = useState("");
  const [order, setOrder] = useState({});
  const [modalVisible, setModalVisible] = useState(false);

  useEffect(() => {
    setOrderData(
      orders?.map((row, i) => {
        let newAddress = row.customer
          ? row.customer.address.country +
            " " +
            row.customer.address.state +
            " " +
            row.customer.address.city +
            " " +
            row.customer.address.street
          : null;
        return {
          key: i,
          id: row.id,
          orderedAt: moment(row.orderDate).format("MMMM DD, YYYY HH:mm"),
          menus: row.menus && row.menus,
          user: row.customer,
          username: row.customer && row.customer.name,
          phoneNumber: row.customer && row.customer.phoneNumber,
          totalPrice: row.totalPrice,
          address: newAddress,
          status: row.status,
        };
      })
    );
  }, [orders]);

  const populateOrder = () => {
    setOrderData(
      orders?.map((row, i) => {
        let newAddress = row.customer
          ? row.customer.address.country +
            " " +
            row.customer.address.state +
            " " +
            row.customer.address.city +
            " " +
            row.customer.address.street
          : null;
        return {
          key: i,
          id: row.id,
          orderedAt: row.orderDate,
          menus: row.menus && row.menus,
          user: row.customer,
          username: row.customer && row.customer.name,
          phoneNumber: row.customer && row.customer.phoneNumber,
          address: newAddress,
          status: row.status,
        };
      })
    );
  };

  const showModal = (id, record) => {
    setOrderId(id);
    setOrder(record);
    setModalVisible(true);
  };

  const hideModal = () => {
    setOrderId("");
    setModalVisible(false);
  };

  const filterOrder = (key) => {
    if (key === "") {
      populateOrder();
    } else {
      const filteredOrders = _.filter(
        orderData,
        (order) =>
          order.username.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          order.address.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          order.orderedAt.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          order.phoneNumber.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          order.totalPrice.toString().indexOf(key.toLowerCase()) > -1 ||
          order.status.toLowerCase().indexOf(key.toLowerCase()) > -1
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
      title: "Ordered By",
      dataIndex: "username",
      key: "username",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.username - b.username,
    },
    {
      title: "Ordered At",
      dataIndex: "orderedAt",
      key: "orderedAt",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.orderedAt - b.orderedAt,
    },
    {
      title: "Address",
      dataIndex: "address",
      key: "address",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.address - b.address,
      width: "15%",
    },
    {
      title: "Phone Number",
      dataIndex: "phoneNumber",
      key: "phoneNumber",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.phoneNumber - b.phoneNumber,
    },
    {
      title: "Total Price($)",
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
    {
      title: "Action",
      dataIndex: "",
      key: "x",
      render: (text, record) => (
        <span>
          {" "}
          {record.status === "READY" && (
            <Button
              type="primary"
              style={{ marginRight: 30 }}
              onClick={() => {
                setOrderMode("Pick");
                showModal(record.id, record);
              }}
            >
              Pick
            </Button>
          )}
          {record.status === "PICKED" && (
            <Button
              type="primary"
              style={{ marginRight: 30 }}
              onClick={() => {
                setOrderMode("Deliver");
                showModal(record.id, record);
              }}
            >
              Deliver
            </Button>
          )}
        </span>
      ),
    },
  ];

  const updateAllOrders = (evt) => {
    filterOrder(evt.target.value);
  };

  const onPickOrder = () => {
    console.log("Order id to be picked is : ", orderId);
    pickOrder(userId, orderId, order);
    setModalVisible(false);
  };

  const onDeliverOrder = () => {
    console.log("Order id to be delivered is : ", orderId);
    deliverOrder(userId, orderId, order);
    setModalVisible(false);
  };

  if (orderData === null || loading) {
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
              rowExpandable: (record) => record.menus !== null,
            }}
            dataSource={orderData}
          />
          <Modal
            title="Confirm"
            visible={modalVisible}
            onOk={orderMode === "Pick" ? onPickOrder : onDeliverOrder}
            onCancel={hideModal}
            okText={orderMode === "Pick" ? "Picked" : "Delivered"}
            cancelText={"Exit"}
          >
            <p>
              Are you sure,{" "}
              {orderMode === "Pick"
                ? `order ${orderId} is picked`
                : `order ${orderId} is delivered`}
              ?
            </p>
            {isStatusChanged && <Spin style={{ marginLeft: "200px" }} />}
          </Modal>
        </div>
      );
    } else {
      orderContent = <div>
      <Row>
        <Col span={8} offset={8}>
          <Result
            icon={<FrownOutlined />}
            title="Sorry, no orders found!"
          />
        </Col>
      </Row>
    </div>;
    }
  }

  return (
    <div>
      <Search
        placeholder="Search orders"
        onChange={updateAllOrders}
        style={{ width: 450, marginBottom: 10 }}
        enterButton
      />
      {orderContent}
    </div>
  );
};

AllOrders.propTypes = {
  getOrders: PropTypes.func.isRequired,
  pickOrder: PropTypes.func.isRequired,
  deliverOrder: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
  driver: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  driver: state.driver,
  errors: state.errors,
});

export default connect(mapStateToProps, {
  getOrders,
  pickOrder,
  deliverOrder,
})(withRouter(AllOrders));
