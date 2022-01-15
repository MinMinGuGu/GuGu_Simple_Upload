import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemAppLog extends CheckLogin {
    content = () => {
        return "系统日志待施工...";
    };

    render() {
        return <Content view={this.content()} />;
    }
}
