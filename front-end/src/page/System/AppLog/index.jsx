import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemAppLog extends CheckLogin {
    content = () => {
        return {
            navPath: "系统管理>系统日志",
            content: "系统日志待施工...",
        };
    };

    render() {
        return <Content view={this.content()} />;
    }
}
