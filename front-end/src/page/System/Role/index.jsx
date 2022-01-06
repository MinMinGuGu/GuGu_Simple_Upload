import React from "react";
import Main from "../../../layout/Main";
import CheckLogin from "../../../components/CheckLogin";

export default class SystemRole extends CheckLogin {
    content = () => {
        return { navPath: "系统管理>角色管理", content: "角色管理待施工..." };
    };

    render() {
        return (
            <div>
                <Main history={this.props.history} view={this.content()} />
            </div>
        );
    }
}
