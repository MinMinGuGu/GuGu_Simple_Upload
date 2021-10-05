import React, { Component } from 'react'
import { BrowserRouter as Router, Route } from "react-router-dom";
import Login from './Login';
import Home from './Home'
import FileUpload from './File/Upload'
import FileList from './File/List'
import SystemAppKey from './System/AppKey'
import SystemManage from './System/Manage'
import SystemPermission from './System/Permission'
import SystemRole from './System/Role'
import SystemUser from './System/User'
import About from './About'

export default class Routers extends Component {
    render() {
        return (
            <Router>
                {/* todo 未进入 */}
                <Route exact={true} path='/' component={Home} />
                <Route exact={true} path='/login' component={Login} />
                <Route exact={true} path='/file/upload' component={FileUpload} />
                <Route exact={true} path='/file/list' component={FileList} />
                <Route exact={true} path='/system/user' component={SystemUser} />
                <Route exact={true} path='/system/role' component={SystemRole} />
                <Route exact={true} path='/system/permission' component={SystemPermission} />
                <Route exact={true} path='/system/manage' component={SystemManage} />
                <Route exact={true} path='/system/appKey' component={SystemAppKey} />
                <Route exact={true} path='/about' component={About} />
            </Router>
        )
    }
}
