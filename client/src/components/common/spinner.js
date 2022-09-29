import React from 'react';
import { Spin } from 'antd';

// eslint-disable-next-line
const spinner = () => {
    return (
        <div>
            {/* <img src={spinner}
                style={{ width: '200px',margin: 'auto', display: 'block' }}
                alt="Loading..."
            /> */}
            <Spin style={{ width: '200px',margin: 'auto', marginTop: 60,display: 'block' }} size="large" />
        </div>
    );
}

export default spinner;