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
            if (pass.hasOwnProperty.call(pass, pass)) {
                const route = pass[pass];
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
        return (
            <Menu theme="dark" mode="inline" onClick={this.menuItemClick}>
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
                    <Menu.Item key="6" path="/system/appKey">
                        AppKey设置
                    </Menu.Item>
                    <Menu.Item key="7" path="/system/appLog">
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

export default withRouter(LeftMenu);
