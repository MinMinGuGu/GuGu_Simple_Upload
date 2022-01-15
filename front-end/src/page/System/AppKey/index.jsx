import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemAppKey extends CheckLogin {
    content = () => {
        return "AppKey设置待施工...";
    };

    render() {
        return <Content view={this.content()} />;
    }
}
