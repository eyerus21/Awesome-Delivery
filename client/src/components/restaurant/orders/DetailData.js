import React from "react";
import { List } from "antd";

const DetailData = (props) => {
  return (
    <div>
      <List
        dataSource={props.data}
        renderItem={(item) => (
          <List.Item key={item.key}>
            <List.Item.Meta
              title={<a href="#/">{item.name}</a>}
              description={`$${item.price}`}
            />
            <div>{item.quantity}</div>
          </List.Item>
        )}
      ></List>
    </div>
  );
};

export default DetailData;
