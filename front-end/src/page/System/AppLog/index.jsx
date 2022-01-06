import React from "react";
import Main from "../../../layout/Main";
import CheckLogin from "../../../components/CheckLogin";

export default class SystemAppLog extends CheckLogin {
    content = () => {
        return {
            navPath: "系统管理>系统日志",
            content: "系统日志待施工...",
        };
    };

    render() {
        return (
            <div>
                <Main history={this.props.history} view={this.content()} />
            </div>
        );
    }
}
