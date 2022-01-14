import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemManage extends CheckLogin {
    content = () => {
        return { navPath: "系统管理>系统管理", content: "系统管理待施工..." };
    };

    render() {
        return <Content view={this.content()} />;
    }
}
