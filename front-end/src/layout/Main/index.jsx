import React, { Component } from 'react'
import { Layout, Breadcrumb } from 'antd'
import "antd/dist/antd.css"
import Header from '../Header'
import LeftMenu from '../LeftMenu'
import Content from '../Content'
import Footer from '../Footer'

export default class Main extends Component {

    state = {
        collapsed: false,
        test: 'start'
    }

    onCollapse = collapsed => {
        console.log(collapsed)
        this.setState({ collapsed })
    }

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
        const { collapsed } = this.state
        return (
            <Layout style={{ minHeight: '100vh' }}>
                <Layout.Sider collapsible collapsed={collapsed} onCollapse={this.onCollapse}>
                    <div className="logo" />
                    <LeftMenu history={this.props.history} />
                </Layout.Sider>
                <Layout className="site-layout">
                    <Header />
                    <Content content={this.content()} />
                    <Footer />
                </Layout>
            </Layout>
        )
    }
}
