import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";

export default class SystemRole extends CheckLogin {
    content = () => {
        return "角色管理待施工...";
    };

    render() {
        return <Content view={this.content()} />;
    }
}
