import React from "react";
import CheckLogin from "../../components/CheckLogin";
import Content from "../../layout/Content";

export default class About extends CheckLogin {
    content = () => {
        return { navPath: "关于", content: "关于待施工..." };
    };

    render() {
        return <Content view={this.content()} />;
    }
}
