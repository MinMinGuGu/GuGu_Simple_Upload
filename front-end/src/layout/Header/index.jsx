import React, { Component } from 'react'
import { Layout } from 'antd'
import './index.css'

export default class Header extends Component {
    render() {
        return (
            <Layout.Header className="site-layout-background site-layout-cents" style={{ padding: 0 }} >
                GuGu Simple Upload System
            </Layout.Header>
        )
    }
}
