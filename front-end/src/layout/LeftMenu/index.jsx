import React, { Component } from 'react'
import { Menu } from 'antd'
import { doGet } from '../../utils/requestUtil'
import { notLogin, requestError } from '../../utils/constant'

const { SubMenu } = Menu

export default class LeftMenu extends Component {

    state = {
        menuData: []
    }

    UNSAFE_componentWillMount() {
        this.loadMenu()
    }

    loadMenu = () => {
        const response = doGet('/menu')
        response.then(result => {
            if (result.code === 200) {
                this.setState({ menuData: result.data })
            } else if (result.code === notLogin || result.code === requestError) {
                window.location = '/login'
            } else {
                // tudo 发送错误  需要将错误信息传递到Header组件中展示  需要使用到组件传递state的框架
            }
        })
    }

    genreateMenu = (menuData) => {
        let count = 1
        const outMenu = menuData.map(item => {
            const { path, name, children } = item
            if (children.length > 1) {
                return (
                    <SubMenu key={'sub' + (count++)} title={name}>
                        {
                            children.map(child => <Menu.Item key={(count++)} path={child.path}>
                                {child.name}
                            </Menu.Item>)
                        }
                    </SubMenu>
                )
            } else {
                return (
                    <Menu.Item key={(count++)} path={path}>
                        {name}
                    </Menu.Item>
                )
            }
        })
        return outMenu
    }

    menuItemClick = (object) => {
        const newLocal = object.item.props
        // 手动路由跳转
        this.props.history.push(newLocal.path)
    }

    render() {
        const { menuData } = this.state
        return (
            <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" onClick={this.menuItemClick}>
                {
                    this.genreateMenu(menuData)
                }
                {/* <Menu.Item key="1" icon={<PieChartOutlined />}>
                    概览
                </Menu.Item>
                <SubMenu key="sub1" icon={<ProfileOutlined />} title="文件管理">
                    <Menu.Item key="2">文件上传</Menu.Item>
                    <Menu.Item key="3">文件列表</Menu.Item>
                </SubMenu>
                <SubMenu key="sub2" icon={<FormOutlined />} title="系统管理">
                    <Menu.Item key="4">用户管理</Menu.Item>
                    <Menu.Item key="5">角色管理</Menu.Item>
                    <Menu.Item key="6">权限管理</Menu.Item>
                    <Menu.Item key="7">系统设置</Menu.Item>
                    <Menu.Item key="8">AppKey设置</Menu.Item>
                </SubMenu>
                <Menu.Item key="9" icon={<AppstoreAddOutlined />}>
                    关于
                </Menu.Item> */}
            </Menu>
        )
    }
}
