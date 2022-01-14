import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemRole extends CheckLogin {
    content = () => {
        return { navPath: "系统管理>角色管理", content: "角色管理待施工..." };
    };

    render() {
        return <Content view={this.content()} />;
    }
}
