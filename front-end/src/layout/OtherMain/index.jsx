import React, { Component } from 'react'
import { Layout } from 'antd'
import "antd/dist/antd.css"
import Header from '../Header'
import LeftMenu from '../LeftMenu'
import Content from '../Content'
import Footer from '../Footer'

export default class OtherMain extends Component {

    state = {
        collapsed: false,
    }

    onCollapse = collapsed => {
        console.log(collapsed)
        this.setState({ collapsed })
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
                    <Content content={this.props.content} />
                    <Footer />
                </Layout>
            </Layout>
        )
    }
}
