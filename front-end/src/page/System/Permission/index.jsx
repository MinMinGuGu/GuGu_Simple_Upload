import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemPermission extends CheckLogin {
    content = () => {
        return { navPath: "系统管理>权限管理", content: "权限管理待施工..." };
    };

    render() {
        return <Content view={this.content()} />;
    }
}
