import React, { Component } from "react";
import { Alert } from "antd";
import "./index.css";
import { doPost } from "../../utils/requestUtil";
import apis from "../../config/setting";
import { withRouter } from "react-router-dom";

class Login extends Component {
    state = {
        msg: "",
    };

    postHandler = (event) => {
        event.preventDefault();
        const {
            username: { value: username },
            passwrod: { value: password },
            rememberMe: { checked: rememberMe },
        } = this;
        console.log(apis.loginApi);
        const result = doPost(apis.loginApi, {
            username,
            password,
            rememberMe,
        });
        result.then((data) => {
            if (data.code === 200) {
                this.props.history.push("/");
                return;
            }
            this.setState({ msg: data.message });
        });
    };

    showMsg = () => {
        if (this.state.msg !== "") {
            return (
                <Alert
                    message="登录失败"
                    description={this.state.msg}
                    type="error"
                    closable
                    style={{ textAlign: "center" }}
                    onClose={this.onClose}
                />
            );
        }
    };

    onClose = () => {
        this.setState({ msg: "" });
    };

    render() {
        return (
            <div>
                {this.showMsg()}
                <div id="login-bg" className="container-fluid"></div>
                <div className="container" id="login">
                    <div className="row justify-content-center">
                        <div className="col-lg-8">
                            <div className="login">
                                <h1>Login</h1>
                                <form>
                                    <div className="form-group">
                                        <input
                                            ref={(r) => (this.username = r)}
                                            autoFocus
                                            type="text"
                                            className="form-control"
                                            id="username"
                                            name="username"
                                            placeholder="Enter username"
                                        />
                                    </div>
                                    <div className="form-group">
                                        <input
                                            ref={(r) => (this.passwrod = r)}
                                            type="password"
                                            className="form-control"
                                            id="passwrod"
                                            placeholder="Password"
                                        />
                                    </div>
                                    <div className="form-check">
                                        <label className="switch">
                                            <input
                                                ref={(r) =>
                                                    (this.rememberMe = r)
                                                }
                                                type="checkbox"
                                                name="rememberMe"
                                            />
                                            <span className="slider round"></span>
                                        </label>
                                        <label
                                            className="form-check-label"
                                            htmlFor="exampleCheck1"
                                        >
                                            Remember Me
                                        </label>
                                    </div>
                                    <br />
                                    <button
                                        type="submit"
                                        onClick={this.postHandler}
                                        className="btn btn-lg btn-block btn-success"
                                    >
                                        Sign in
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(Login);
