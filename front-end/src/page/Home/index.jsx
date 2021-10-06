import React, { Component } from 'react'
import Main from '../../layout/Main'
import { Layout, Breadcrumb } from 'antd'

export default class Home extends Component {

    content = () => {
        return (
            <Layout.Content style={{ margin: '0 16px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                    概览待施工...
                </div>
            </Layout.Content>
        )
    }

    render() {
        // todo 验证是否以及登录
        return (
            <div>
                <Main history={this.props.history} content={this.content()} />
            </div>
        )
    }
}
