import React from "react";
import { Input, Modal, notification, Form } from "antd";

const { TextArea } = Input;
class Menu extends React.Component {
  constructor(props) {
    super(props);
    const { name, description, category, price, imageUrl } = props.menu;
    this.state = {
      name,
      description,
      category,
      price,
      imageUrl,
    };
  }


  render() {
    const { isModalVisible, handleCancel, onSaveOrUpdateMenu, createMode, id } = this.props;
    const { name, description, category, price, imageUrl } = this.state;

    return (
      <div>
        <Modal
          title={createMode ? "Create Menu" : "Edit Menu"}
          visible={isModalVisible}
          onCancel={handleCancel}
          width={600}
          closable={false}
          onOk={() => {
            if (name === "") {
              notification.error({
                message: "Name is required!",
              });
              return;
            }
            if (description === "") {
              notification.error({
                message: "Description is required!",
              });
              return;
            }
            if (category === "") {
              notification.error({
                message: "Category is required!",
              });
              return;
            }
            if (price === 0 || price == null) {
              notification.error({
                message: "Price is required!",
              });
              return;
            }
            if (imageUrl === 0 || imageUrl == null) {
              notification.error({
                message: "Image url is required!",
              });
              return;
            }
            handleCancel();
            onSaveOrUpdateMenu(id, {
              name: name,
              description: description,
              category: category,
              price: price,
              imageUrl: imageUrl
            });
            this.setState({
              name: "",
              description: "",
              category: "",
              price: null,
              imageUrl : ""
            });
          }}
        >
          <Form
            labelCol={{ span: 4 }}
            wrapperCol={{ span: 14 }}
            layout="horizontal"
            initialValues={{
                name: name,
                description: description,
                category: category,
                price: price
            }}
          >
            <Form.Item label="Name">
              <Input
                required
                name="name"
                placeholder="Name"
                onChange={(event) =>
                  this.setState({ name: event.target.value })
                }
                value={name}
                margin="none"
              />
            </Form.Item>
            <Form.Item label="Category">
              <Input
                required
                name="category"
                placeholder="Category"
                onChange={(event) =>
                  this.setState({ category: event.target.value })
                }
                value={category}
                margin="none"
              />
            </Form.Item>
            <Form.Item label="Description">
              <TextArea
                required
                name="description"
                placeholder="Description"
                onChange={(event) =>
                  this.setState({ description: event.target.value })
                }
                value={description}
                margin="none"
              />
            </Form.Item>
            <Form.Item label="Price">
              <Input
                required
                type="number"
                name="price"
                placeholder="Price"
                onChange={(event) =>
                  this.setState({ price: event.target.value })
                }
                value={price}
                margin="none"
              />
            </Form.Item>
            <Form.Item label="Image URL">
              <Input
                required
                name="imageUrl"
                placeholder="imageUrl"
                onChange={(event) =>
                  this.setState({ imageUrl: event.target.value })
                }
                value={imageUrl}
                margin="none"
              />
            </Form.Item>
          </Form>
        </Modal>
      </div>
    );
  }
}
export default Menu;
