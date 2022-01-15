import { Component } from "react";
import { Layout } from "antd";

export default class Content extends Component {
    render() {
        return (
            <Layout.Content style={{ margin: 20 }}>
                <div
                    className="site-layout-background"
                    style={{ padding: 10, minHeight: 300 }}
                >
                    {this.props.view.content}
                </div>
            </Layout.Content>
        );
    }
}
