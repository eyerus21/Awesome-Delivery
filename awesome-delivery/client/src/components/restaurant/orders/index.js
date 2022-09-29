import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { Table, Input, Modal, Button, Spin, Row, Col, Result } from "antd";
import DetailData from "./DetailData";
// import mockData from "./mockData";
import moment from "moment";
import _ from "lodash";
import Spinner from "../../common/spinner";
import {
  readyOrder,
  getOrders,
  acceptOrder,
  rejectOrder,
} from "../../../actions/restaurantAction";
import { FrownOutlined } from "@ant-design/icons";
const { Search } = Input;

const AllOrders = (props) => {
  const { auth, readyOrder, acceptOrder, rejectOrder } = props;
  const loading = props.loading;
  const orders = props.orders;
  const isStatusChanged = props.orderStatusLoading;

  const [orderData, setOrderData] = useState(orders);
  const { loggedInUser } = auth;
  let restaurantId = loggedInUser.userId;
  let orderContent;
  const [orderMode, setOrderMode] = useState("");
  const [orderId, setOrderId] = useState("");
  const [modalVisible, setModalVisible] = useState(false);

  useEffect(() => {
    setOrderData(
      orders?.map((row, i) => {
        let customerName = row.customer.name;
        return {
          key: i + 1,
          id: row.id,
          orderedAt: moment(row.orderDate).format("MMMM DD, YYYY HH:mm"),
          orderedBy: customerName,
          menus: row.menus,
          totalPrice: row.totalPrice,
          status: row.status,
        };
      })
    );
  }, [orders]);

  const showModal = (id) => {
    setOrderId(id);
    setModalVisible(true);
  };

  const hideModal = () => {
    setOrderId("");
    setModalVisible(false);
  };

  const populateOrder = () => {
    setOrderData(
      orders?.map((row, i) => {
        let customerName = row.customer.name;
        return {
          key: i + 1,
          orderedAt: moment(row.orderDate).format("MMMM DD, YYYY HH:mm"),
          orderedBy: customerName,
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
      const filteredOrder = _.filter(
        orderData,
        (order) =>
          order.orderedBy.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          order.orderedAt.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          order.totalPrice.toString().toLowerCase().indexOf(key.toLowerCase()) >
            -1 ||
          order.status.toLowerCase().indexOf(key.toLowerCase()) > -1
      );
      setOrderData(filteredOrder);
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
      dataIndex: "orderedBy",
      key: "orderedBy",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.orderedBy - b.orderedBy,
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
    {
      title: "Action",
      dataIndex: "",
      key: "x",
      render: (text, record) => (
        <span>
          {" "}
          {record.status === "PAYED" && (
            <div>
              <Button
                type="primary"
                style={{ marginRight: 30 }}
                onClick={() => {
                  setOrderMode("Accept");
                  showModal(record.id);
                }}
              >
                Accept
              </Button>
              <Button
                style={{ color: "red" }}
                onClick={() => {
                  setOrderMode("Reject");
                  showModal(record.id);
                }}
                danger="true"
              >
                Reject
              </Button>
            </div>
          )}
          {record.status === "ACCEPTED" && (
            <Button
              type="primary"
              style={{ marginRight: 30 }}
              onClick={() => {
                setOrderMode("Ready");
                showModal(record.id);
              }}
            >
              Ready
            </Button>
          )}
        </span>
      ),
    },
  ];

  const updateAllOrders = (evt) => {
    filterOrder(evt.target.value);
  };

  const onRejectOrder = async () => {
    console.log("Order id to be rejected is : ", orderId);
    await rejectOrder(restaurantId, orderId);
    setModalVisible(false);
  };

  const onAcceptOrder = async () => {
    console.log("Order id to be accepted is : ", orderId);
    await acceptOrder(restaurantId, orderId);
    setModalVisible(false);
  };

  const onReadyOrder = async () => {
    console.log("Order id to be ready is : ", orderId);
    await readyOrder(restaurantId, orderId);
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
              rowExpandable: (record) => record.name !== "Not Expandable",
            }}
            dataSource={orderData}
          />
          <Modal
            title="Confirm"
            visible={modalVisible}
            onOk={
              orderMode === "Ready"
                ? onReadyOrder
                : orderMode === "Accept"
                ? onAcceptOrder
                : onRejectOrder
            }
            onCancel={hideModal}
            okText={
              orderMode === "Ready"
                ? "Ready"
                : orderMode === "Accept"
                ? "Accept"
                : "Reject"
            }
            cancelText={"Exit"}
          >
            <p>
              Are you sure,{" "}
              {orderMode === "Ready"
                ? `order ${orderId} is ready for pickup`
                : orderMode === "Accept"
                ? `you want to accept order ${orderId}`
                : `you want to reject order ${orderId}`}
              ?
            </p>
            {isStatusChanged && <Spin style={{ marginLeft: "200px" }} />}
          </Modal>
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
  readyOrder: PropTypes.func.isRequired,
  acceptOrder: PropTypes.func.isRequired,
  rejectOrder: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
  restaurant: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  restaurant: state.restaurant,
  errors: state.errors,
});

export default connect(mapStateToProps, {
  getOrders,
  readyOrder,
  acceptOrder,
  rejectOrder,
})(withRouter(AllOrders));
