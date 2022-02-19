import React, { Component } from "react";
import { Layout } from "antd";
import Header from "../Header";
import LeftMenu from "../LeftMenu";
import Footer from "../Footer";
import Routers from "../../page/Routers";
import { withRouter } from "react-router-dom";
import Login from "../../page/Login";

class Main extends Component {
    state = {
        collapsed: false,
    };

    onCollapse = (collapsed) => {
        console.log(collapsed);
        this.setState({ collapsed });
    };

    render() {
        if (this.props.location.pathname === "/login") {
            return <Login />;
        }
        const { collapsed } = this.state;
        return (
            <Layout style={{ minHeight: "100vh" }}>
                <Layout.Sider
                    collapsible
                    collapsed={collapsed}
                    onCollapse={this.onCollapse}
                >
                    <div className="logo" />
                    <LeftMenu />
                </Layout.Sider>
                <Layout className="site-layout">
                    <Header />
                    <Routers />
                    <Footer />
                </Layout>
            </Layout>
        );
    }
}

export default withRouter(Main);
