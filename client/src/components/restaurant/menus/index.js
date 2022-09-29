import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { Table, Input, Row, Col, Button, Modal, Spin, Result } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import _ from "lodash";
import {
  addMenu,
  editMenu,
  getMenus,
  deleteMenu,
} from "../../../actions/restaurantAction";
import { FrownOutlined } from "@ant-design/icons";
import ModalForm from "./Modal";
import Spinner from "../../common/spinner";
const { Search } = Input;

const AllMenus = (props) => {
  const { auth, addMenu, editMenu, deleteMenu } = props;
  const userId = auth.loggedInUser.userId;
  const loading = props.loading;
  const menus = props.menus;
  let menuContent;
  const [createMode, setCreateMode] = useState(true);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [menuName, setMenuName] = useState("");
  const [menuId, setMenuId] = useState("");
  const [deleteModal, setDeleteModal] = useState(false);
  const [menuData, setMenuData] = useState(menus);
  const [selectedRow, setSelectedRow] = useState({
    name: "",
    price: "",
    description: "",
    category: "",
  });

  const [selectedRowId, setSelectedRowId] = useState("");

  useEffect(() => {
    setMenuData(menus);
  }, [menus]);

  // const data = [
  //   {
  //     key: 1,
  //     name: "New york Steak",
  //     category: "Dinner",
  //     price: 39.99,
  //     description:
  //       "The New York Steak is also known as New York strip steak, ambassador steak, strip loin steak, club steak, or the Omaha Strip.",
  //     imageUrl:
  //       "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg",
  //   },
  //   {
  //     key: 2,
  //     name: "Double Cheese Burger",
  //     price: 11.99,
  //     category: "Lunch",
  //     description:
  //       "The McDonald's Double Cheeseburger features two 100% pure beef burger patties seasoned with just a pinch of salt and pepper.",
  //     imageUrl:
  //       "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg",
  //   },
  //   {
  //     key: 3,
  //     name: "Special Pizza",
  //     price: 29.99,
  //     category: "Lunch",
  //     description:
  //       "Our Special Pizza is the perfect homemade recipe with a crisp and chewy crust, tangy tomato sauce, and delicious toppings",
  //     imageUrl:
  //       "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg",
  //   },
  //   {
  //     key: 4,
  //     name: "Ethiopian Dish",
  //     price: 18.99,
  //     category: "Lunch",
  //     description:
  //       "A typical dish consists of injera accompanied by a spicy stew, which frequently includes beef, lamb, vegetables and various types of legumes, such...",
  //     imageUrl:
  //       "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg",
  //   },
  // ];

  const showModal = (isCreateMode) => {
    setCreateMode(isCreateMode);
    setIsModalVisible(true);
  };

  const showDeleteModal = (id, name) => {
    setMenuName(name);
    setMenuId(id);
    setDeleteModal(true);
  };

  const hideDeleteModal = () => {
    setMenuName("");
    setMenuId("");
    setDeleteModal(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    setSelectedRow({
      name: "",
      price: "",
      description: "",
      category: "",
    });
    setSelectedRowId("");
  };

  const filterMenu = (key) => {
    if (key === "") {
      setMenuData(menus);
    } else {
      const filteredMenu = _.filter(
        menuData,
        (menu) =>
          menu.name.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          menu.category.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          menu.description.toLowerCase().indexOf(key.toLowerCase()) > -1 ||
          menu.price.toString().indexOf(key) > -1
      );
      setMenuData(filteredMenu);
    }
  };

  const columns = [
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.name - b.name,
    },
    {
      title: "Category",
      dataIndex: "category",
      key: "category",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.category - b.category,
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.description - b.description,
      width: "30%",
    },
    {
      title: "Price",
      dataIndex: "price",
      key: "price",
      defaultSortOrder: "ascend",
      sorter: (a, b) => a.price - b.price,
    },
    {
      title: "Action",
      dataIndex: "",
      key: "x",
      render: (text, record) => (
        <span>
          {" "}
          <Button
            type="primary"
            style={{ marginRight: 30 }}
            onClick={() => {
              setSelectedRow(record);
              setSelectedRowId(record.id);
              showModal(false);
            }}
          >
            Edit
          </Button>
          <Button
            style={{ color: "red" }}
            onClick={() => showDeleteModal(record.id, record.name)}
            danger="true"
          >
            Delete
          </Button>
        </span>
      ),
    },
  ];

  const updateAllMenus = (evt) => {
    filterMenu(evt.target.value);
  };

  const onSaveOrUpdateMenu = async (id, menu) => {
    if (id) {
      console.log("Editing state where id is", id);
      console.log("Editing state where data is", menu);
      await editMenu(userId, id, menu);
    } else {
      console.log("Creating state where data is", menu);
      await addMenu(userId, menu);
    }
  };

  const onDeleteMenu = async () => {
    console.log("Menu id to be deleted : ", menuId);
    await deleteMenu(userId, menuId);
    setIsModalVisible(false);
  };

  if (menuData === null || loading) {
    menuContent = <Spinner />;
  } else {
    if (menuData && menuData.length > 0) {
      menuContent = (
        <div>
          <Row>
            <Col span={8}>
              <Search
                placeholder="Find your menus"
                onChange={updateAllMenus}
                style={{ width: 450, marginBottom: 10 }}
                enterButton
              />
            </Col>
            <Col span={8} offset={8}>
              <Button
                type="primary"
                style={{ float: "right" }}
                icon={<PlusOutlined />}
                onClick={(e) => showModal(true)}
              >
                Create Menu
              </Button>
            </Col>
            {isModalVisible && (
              <ModalForm
                menu={selectedRow}
                onSaveOrUpdateMenu={onSaveOrUpdateMenu}
                id={selectedRowId}
                createMode={createMode}
                handleCancel={handleCancel}
                isModalVisible={isModalVisible}
              />
            )}
            <Modal
              title="Confirm"
              visible={deleteModal}
              onOk={onDeleteMenu}
              onCancel={hideDeleteModal}
              okText="Delete"
              cancelText="Cancel"
            >
              <p>Are you sure, you want to delete {menuName}?</p>
              <Spin style={{ marginLeft: "200px" }} />
            </Modal>
          </Row>
          <Table
            columns={columns}
            dataSource={menuData}
            style={{ marginTop: "20px" }}
          />
        </div>
      );
    } else {
      menuContent = (
        <div>
          <Row>
            <Col span={8}>
              <Search
                placeholder="Find your menus"
                onChange={updateAllMenus}
                style={{ width: 450, marginBottom: 10 }}
                enterButton
              />
            </Col>
            {isModalVisible && (
              <ModalForm
                menu={selectedRow}
                onSaveOrUpdateMenu={onSaveOrUpdateMenu}
                id={selectedRowId}
                createMode={createMode}
                handleCancel={handleCancel}
                isModalVisible={isModalVisible}
              />
            )}
            <Modal
              title="Confirm"
              visible={deleteModal}
              onOk={onDeleteMenu}
              onCancel={hideDeleteModal}
              okText="Delete"
              cancelText="Cancel"
            >
              <p>Are, you want to delete {menuName}?</p>
            </Modal>
          </Row>
          <Row>
            <Col span={8} offset={8}>
              <Result
                icon={<FrownOutlined />}
                title="Sorry, no menus found!"
                extra={
                  <Button
                    type="primary"
                    icon={<PlusOutlined />}
                    onClick={(e) => showModal(true)}
                  >
                    Create Menu
                  </Button>
                }
              />
            </Col>
          </Row>
        </div>
      );
    }
  }
  return <div>{menuContent}</div>;
};

AllMenus.propTypes = {
  addMenu: PropTypes.func.isRequired,
  editMenu: PropTypes.func.isRequired,
  getMenus: PropTypes.func.isRequired,
  deleteMenu: PropTypes.func.isRequired,
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
  addMenu,
  editMenu,
  getMenus,
  deleteMenu,
})(withRouter(AllMenus));
