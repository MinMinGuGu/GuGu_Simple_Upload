import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemManage extends CheckLogin {
    content = () => {
        return "系统管理待施工...";
    };

    render() {
        return <Content view={this.content()} />;
    }
}
