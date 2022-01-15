import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemPermission extends CheckLogin {
    content = () => {
        return "权限管理待施工...";
    };

    render() {
        return <Content view={this.content()} />;
    }
}
