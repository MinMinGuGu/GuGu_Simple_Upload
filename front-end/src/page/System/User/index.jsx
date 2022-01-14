import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemUser extends CheckLogin {
    content = () => {
        return { navPath: "系统管理>用户管理", content: "用户管理待施工..." };
    };

    render() {
        return <Content view={this.content()} />;
    }
}
