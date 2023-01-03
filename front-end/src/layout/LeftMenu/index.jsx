import React, { Component } from "react";
import { Menu } from "antd";
import {
    PieChartOutlined,
    ProfileOutlined,
    FormOutlined,
    AppstoreAddOutlined,
} from "@ant-design/icons";
import { withRouter } from "react-router-dom";
import { getRoute } from "../../utils/urlUtil";
import routers from "../../config/router";

const { SubMenu } = Menu;

class LeftMenu extends Component {
    UNSAFE_componentWillMount = () => {
        let localRoute = getRoute();
        for (const pass in routers) {
            if (Object.hasOwnProperty.call(routers, pass)) {
                const route = routers[pass];
                if (route.path === localRoute) {
                    // todo 根据redirect跳转过来时菜单能够正确显示
                }
            }
        }
    };

    menuItemClick = (object) => {
        const menuItem = object.item.props;
        this.props.history.push(menuItem.path);
    };

    render() {
        const account = JSON.parse(sessionStorage.getItem("account"));
        let renderContxt = [
            <Menu.Item key="概览" icon={<PieChartOutlined />} path="/">
                概览
            </Menu.Item>,
        ];
        const { permissionList } = account;
        for (let i = 0; i < permissionList.length; i++) {
            let permission = permissionList[i];
            if (permission.name === "upload" || permission.name === "all") {
                renderContxt.push(
                    <SubMenu
                        key="文件管理"
                        icon={<ProfileOutlined />}
                        title="文件管理"
                    >
                        <Menu.Item key="文件上传" path="/file/upload">
                            文件上传
                        </Menu.Item>
                        <Menu.Item key="文件列表" path="/file/list">
                            文件列表
                        </Menu.Item>
                    </SubMenu>
                );
            }
            if (permission.name === "manage" || permission.name === "all") {
                renderContxt.push(
                    <SubMenu
                        key="系统管理"
                        icon={<FormOutlined />}
                        title="系统管理"
                    >
                        <Menu.Item key="用户管理" path="/system/user">
                            用户管理
                        </Menu.Item>
                        <Menu.Item key="角色管理" path="/system/role">
                            角色管理
                        </Menu.Item>
                        <Menu.Item key="AppKey设置" path="/system/appKey">
                            AppKey设置
                        </Menu.Item>
                        <Menu.Item key="操作日志" path="/system/appLog">
                            操作日志
                        </Menu.Item>
                    </SubMenu>
                );
            }
        }
        renderContxt.push(
            <Menu.Item key="关于" icon={<AppstoreAddOutlined />} path="/about">
                关于
            </Menu.Item>
        );
        return (
            <Menu theme="dark" mode="inline" onClick={this.menuItemClick}>
                {renderContxt}
            </Menu>
        );
    }
}

export default withRouter(LeftMenu);
