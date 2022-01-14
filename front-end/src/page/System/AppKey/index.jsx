import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemAppKey extends CheckLogin {
    content = () => {
        return {
            navPath: "系统管理>AppKey设置",
            content: "AppKey设置待施工...",
        };
    };

    render() {
        return <Content view={this.content()} />;
    }
}
