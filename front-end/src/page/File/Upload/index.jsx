import React, { Component } from 'react'
import OtherMain from '../../../layout/OtherMain'
import { Layout, Breadcrumb } from 'antd'
import "antd/dist/antd.css"

export default class FileUpload extends Component {

    content = () => {
        return (
            <Layout.Content style={{ margin: '0 16px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                    文件上传待施工...
                </div>
            </Layout.Content>
        )
    }

    render() {
        return <OtherMain history={this.props.history} content={this.content()} />
    }
}
