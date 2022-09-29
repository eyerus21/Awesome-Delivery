import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
  Form,
  Input,
  Button,
  Card,
  Typography,
  notification,
  Radio,
  Row,
  Col,
  Divider,
  DatePicker,
  Select,
} from "antd";
import moment from "moment";
import countries from "./Countries";
import {
  UserOutlined,
  LockOutlined,
  MailOutlined,
  PhoneOutlined,
} from "@ant-design/icons";

import { registerUser } from "../../actions/authActions";
const { Option } = Select;
const { Title } = Typography;
const tailLayout = {
  wrapperCol: {
    // offset: 5,
    span: 5,
  },
};

const Register = (props) => {
  const [form] = Form.useForm();
  const [errors, setErrors] = useState({});
  const [expDate, setExpDate] = useState("");
  const [userType, setUserType] = useState("restaurant");

  useEffect(() => {
    if (props.errors !== errors) {
      setErrors(props.errors);
    }
  }, [props.errors, errors]);

  const onChange = (e) => {
    setUserType(e.target.value);
  };

  const dateChange = (current, dateString) => {
    setExpDate(dateString);
  };

  const disabledDate = (current) => {
    // Can not select days before today and today
    return current && current < moment().endOf("day");
  };
  const responseMessage = (errorText) => {
    notification["error"]({
      message: "Failed to register!",
      description: errorText,
    });
  };

  const onSubmit = async (values) => {
    var newUser = {};
    if (userType === "restaurant") {
      newUser = {
        type: userType,
        username: values["username"],
        password: values["password"],
        name: values["restaurantName"],
        phoneNumber: values["phoneNumber"],
        email: values["email"],
        address: {
          country: values["country"],
          state: values["state"],
          city: values["city"],
          street: values["street"],
        },
        payment: {
          number: "",
          cvv: "",
          expireMonth: 0,
          expireYear: 0,
        },
      };
    } else if (userType === "customer") {
      newUser = {
        type: userType,
        username: values["username"],
        password: values["password"],
        name: values["fullName"],
        phoneNumber: values["phoneNumber"],
        email: values["email"],
        address: {
          country: values["country"],
          state: values["state"],
          city: values["city"],
          street: values["street"],
        },
        payment: {
          number: values["ccn"],
          cvv: values["cvv"],
          expireMonth: Number(expDate.split("-")[1]),
          expireYear: Number(expDate.split("-")[0]),
        },
      };
    } else {
      newUser = {
        type: userType,
        username: values["username"],
        password: values["password"],
        name: values["fullName"],
        phoneNumber: values["phoneNumber"],
        email: values["email"],
        address: {
          country: values["country"],
          state: values["state"],
          city: values["city"],
          street: values["street"],
        },
        payment: {
          number: "",
          cvv: "",
          expireMonth: 0,
          expireYear: 0,
        },
      };
    }
    await props.registerUser(newUser, props.history);
  };

  const validatePassword = (rule, value, callback) => {
    if (value && value !== form.getFieldValue("password")) {
      callback("Password missmatch!");
    } else {
      callback();
    }
  };

  return (
    <div align="center" className="register">
      <div className="register-inner">
        <Card
          title={<Title level={3}>Register</Title>}
          bordered={true}
          style={{
            width: 700,
            marginBottom: -3,
            borderRadius: "8px",
            // boxShadow: "5px 8px 24px 5px rgba(208, 216, 243, 0.6)",
            boxShadow: "5px 8px 24px 5px",
          }}
        >
          <Radio.Group
            defaultValue="restaurant"
            buttonStyle="solid"
            onChange={onChange}
          >
            <Radio.Button value="restaurant">Restaurant</Radio.Button>
            <Radio.Button value="customer">Customer</Radio.Button>
            <Radio.Button value="driver">Driver</Radio.Button>
          </Radio.Group>
          {errors.message ? responseMessage(errors.message) : null}
          <Form
            style={{ margin: 20, width: 450 }}
            name="basic"
            form={form}
            initialValues={{
              remember: false,
            }}
            onFinish={onSubmit}
            // onFinishFailed={onSubmitFailed}
          >
            <Divider orientation="left" plain>
              User Info
            </Divider>
            <Form.Item
              style={{ marginBottom: 20 }}
              name="username"
              rules={[
                {
                  required: true,
                  message: "Please input your username!",
                },
              ]}
            >
              <Input
                prefix={<UserOutlined className="site-form-item-icon" />}
                placeholder="Username"
              />
            </Form.Item>
            <Form.Item
              style={{ marginBottom: 20 }}
              name="email"
              rules={[
                {
                  required: true,
                  message: "Please input a valid email!",
                  type: "email",
                },
              ]}
            >
              <Input
                prefix={<MailOutlined className="site-form-item-icon" />}
                placeholder="Email"
              />
            </Form.Item>

            <Form.Item
              style={{ marginBottom: 20 }}
              name="password"
              rules={[
                {
                  required: true,
                  message: "Please input your password!",
                },
              ]}
            >
              <Input
                prefix={<LockOutlined className="site-form-item-icon" />}
                type="password"
                placeholder="Password"
              />
            </Form.Item>
            <Form.Item
              name="confirmPassword"
              style={{ marginBottom: 20 }}
              rules={[
                { required: true, message: "Please confirm your Password!" },
                { validator: validatePassword },
              ]}
            >
              <Input
                prefix={<LockOutlined className="site-form-item-icon" />}
                type="password"
                placeholder="Confirm Password"
              />
            </Form.Item>
            <Form.Item
              style={{ marginBottom: 20 }}
              name="phoneNumber"
              rules={[
                {
                  required: true,
                  message: "Please input your phone Number!",
                },
              ]}
            >
              <Input
                prefix={<PhoneOutlined className="site-form-item-icon" />}
                placeholder="Phone Number"
              />
            </Form.Item>
            <div>
              <Divider orientation="left" plain>
                Location
              </Divider>
              <Row gutter={16}>
                <Col className="gutter-row" span={24}>
                  <Form.Item
                    name="country"
                    rules={[
                      {
                        required: true,
                        message: "Please input your country!",
                      },
                    ]}
                  >
                    <Select
                      showSearch
                      placeholder="Search to Select country"
                      optionFilterProp="children"
                      filterOption={(input, option) =>
                        option.children
                          .toLowerCase()
                          .indexOf(input.toLowerCase()) >= 0
                      }
                      filterSort={(optionA, optionB) =>
                        optionA.children
                          .toLowerCase()
                          .localeCompare(optionB.children.toLowerCase())
                      }
                    >
                      {countries.map((country) => {
                        return (
                          <Option value={country.name}>{country.name}</Option>
                        );
                      })}
                    </Select>
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col className="gutter-row" span={8}>
                  <Form.Item
                    name="state"
                    rules={[
                      {
                        required: true,
                        message: "Please input your state!",
                      },
                    ]}
                  >
                    <Input placeholder="State" />
                  </Form.Item>
                </Col>
                <Col className="gutter-row" span={8}>
                  <Form.Item
                    name="city"
                    rules={[
                      {
                        required: true,
                        message: "Please input your city!",
                      },
                    ]}
                  >
                    <Input placeholder="City" />
                  </Form.Item>
                </Col>
                <Col className="gutter-row" span={8}>
                  <Form.Item
                    name="street"
                    rules={[
                      {
                        required: true,
                        message: "Please input your street!",
                      },
                    ]}
                  >
                    <Input placeholder="Street" />
                  </Form.Item>
                </Col>
              </Row>
            </div>
            {userType === "restaurant" ? (
              <div>
                <Divider orientation="left" plain>
                  Restaurant Info
                </Divider>
                <Form.Item
                  style={{ marginBottom: 20 }}
                  name="restaurantName"
                  rules={[
                    {
                      required: true,
                      message: "Please input your restaurant name!",
                    },
                  ]}
                >
                  <Input type="text" placeholder="Restaurant Name" />
                </Form.Item>
              </div>
            ) : userType === "customer" ? (
              <div>
                <Form.Item
                  style={{ marginBottom: 20 }}
                  name="fullName"
                  rules={[
                    {
                      required: true,
                      message: "Please input your full name!",
                    },
                  ]}
                >
                  <Input type="text" placeholder="Full Name" />
                </Form.Item>
                <Divider orientation="left" plain>
                  Payment Info
                </Divider>
                <Row gutter={16}>
                  <Col className="gutter-row" span={8}>
                    <Form.Item
                      name="ccn"
                      rules={[
                        {
                          required: true,
                          message: "Please input your credit card number!",
                        },
                      ]}
                    >
                      <Input type="number" placeholder="Credit Card Number" />
                    </Form.Item>
                  </Col>
                  <Col className="gutter-row" span={8}>
                    <Form.Item
                      name="expDate"
                      rules={[
                        {
                          required: true,
                          message: "Please input the expiraton month and year!",
                        },
                      ]}
                    >
                      <DatePicker
                        picker="month"
                        disabledDate={disabledDate}
                        onChange={dateChange}
                      />
                    </Form.Item>
                  </Col>
                  <Col className="gutter-row" span={8}>
                    <Form.Item
                      name="cvv"
                      rules={[
                        {
                          required: true,
                          message: "Please input your cvv!",
                        },
                      ]}
                    >
                      <Input type="text" placeholder="CVV" />
                    </Form.Item>
                  </Col>
                </Row>
              </div>
            ) : (
              <div>
                <Form.Item
                  style={{ marginBottom: 20 }}
                  name="fullName"
                  rules={[
                    {
                      required: true,
                      message: "Please input your full name!",
                    },
                  ]}
                >
                  <Input type="text" placeholder="Full Name" />
                </Form.Item>
              </div>
            )}

            <Form.Item {...tailLayout}>
              <Button type="primary" htmlType="submit">
                Submit
              </Button>
            </Form.Item>
          </Form>
        </Card>
      </div>
    </div>
  );
};

Register.propTypes = {
  registerUser: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
  errors: state.errors,
});

export default connect(mapStateToProps, { registerUser })(withRouter(Register));
