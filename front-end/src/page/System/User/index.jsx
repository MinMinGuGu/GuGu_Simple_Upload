import React from "react";
import Main from "../../../layout/Main";
import CheckLogin from "../../../components/CheckLogin";

export default class SystemUser extends CheckLogin {
    content = () => {
        return { navPath: "系统管理>用户管理", content: "用户管理待施工..." };
    };

    render() {
        return (
            <div>
                <Main history={this.props.history} view={this.content()} />
            </div>
        );
    }
}
