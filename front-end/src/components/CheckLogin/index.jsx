import { Component } from "react";
import { doGet } from "../../utils/requestUtil";
import apis from "../../config/setting";

export default class CheckComponent extends Component {
    UNSAFE_componentWillMount = () => {
        this.checkLogin();
    };

    checkLogin = () => {
        const result = doGet(apis.loginApi);
        result.then((data) => {
            if (data.code !== 200) {
                // 清除sessionStorage
                sessionStorage.removeItem("account");
                console.log("check login failed");
                this.props.history.push("/login");
            } else {
                // 存储数据至sessionStorage
                sessionStorage.setItem("account", JSON.stringify(data.data));
                console.log("login verification passed...");
            }
        });
    };
}
