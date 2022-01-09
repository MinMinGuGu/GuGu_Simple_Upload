import React, { Component } from "react";
import { Menu } from "antd";
import {
    PieChartOutlined,
    ProfileOutlined,
    FormOutlined,
    AppstoreAddOutlined,
} from "@ant-design/icons";

const { SubMenu } = Menu;

export default class LeftMenu extends Component {
    menuItemClick = (object) => {
        const menuItem = object.item.props;
        // 手动路由跳转
        this.props.history.push(menuItem.path);
    };

    getSelectedItem = () => {
        switch (this.props.history.location.pathname) {
            case "/":
                return ["1"];
            case "/file/upload":
                return ["2"];
            case "/file/list":
                return ["3"];
            case "/system/user":
                return ["4"];
            case "/system/role":
                return ["5"];
            case "/system/permission":
                return ["6"];
            case "/system/manage":
                return ["7"];
            case "/system/appKey":
                return ["8"];
            case "/system/appLog":
                return ["9"];
            case "/about":
                return ["10"];
            default:
                return ["1"];
        }
    };

    getOpenKeys = () => {
        const pathname = this.props.history.location.pathname;
        if (pathname.indexOf("file") !== -1) {
            return ["sub1"];
        } else if (pathname.indexOf("system") !== -1) {
            return ["sub2"];
        }
        return null;
    };

    render() {
        return (
            <Menu
                theme="dark"
                defaultSelectedKeys={this.getSelectedItem()}
                defaultOpenKeys={this.getOpenKeys()}
                mode="inline"
                onClick={this.menuItemClick}
            >
                <Menu.Item key="1" icon={<PieChartOutlined />} path="/">
                    概览
                </Menu.Item>
                <SubMenu key="sub1" icon={<ProfileOutlined />} title="文件管理">
                    <Menu.Item key="2" path="/file/upload">
                        文件上传
                    </Menu.Item>
                    <Menu.Item key="3" path="/file/list">
                        文件列表
                    </Menu.Item>
                </SubMenu>
                <SubMenu key="sub2" icon={<FormOutlined />} title="系统管理">
                    <Menu.Item key="4" path="/system/user">
                        用户管理
                    </Menu.Item>
                    <Menu.Item key="5" path="/system/role">
                        角色管理
                    </Menu.Item>
                    <Menu.Item key="6" path="/system/permission">
                        权限管理
                    </Menu.Item>
                    <Menu.Item key="7" path="/system/manage">
                        系统设置
                    </Menu.Item>
                    <Menu.Item key="8" path="/system/appKey">
                        AppKey设置
                    </Menu.Item>
                    <Menu.Item key="9" path="/system/appLog">
                        系统日志
                    </Menu.Item>
                </SubMenu>
                <Menu.Item
                    key="10"
                    icon={<AppstoreAddOutlined />}
                    path="/about"
                >
                    关于
                </Menu.Item>
            </Menu>
        );
    }
}
