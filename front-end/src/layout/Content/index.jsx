import { Component } from 'react'
import { Layout, Breadcrumb } from 'antd'

export default class Content extends Component {
    render() {
        return (
            <Layout.Content style={{ margin: '0 16px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>{this.props.view.navPath}</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                    {this.props.view.content}
                </div>
            </Layout.Content>
        )
    }
}
