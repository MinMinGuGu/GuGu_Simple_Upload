import Home from '../page/Home';
import Login from '../page/Login';
import FileList from '../page/File/List'
import SystemAppKey from '../page/System/AppKey'
import SystemRole from '../page/System/Role'
import SystemUser from '../page/System/User'
import About from '../page/About'
import SystemAppLog from '../page/System/AppLog';
import FileManage from '../page/System/FileManage';

const routers = [
    {
        path: '/',
        component: Home,
        exact: true,
        title: "根目录"
    },
    {
        path: '/home',
        component: Home,
        exact: true,
        title: "概览"
    },
    {
        path: '/login',
        component: Login,
        exact: true,
        title: "登录"
    },
    {
        path: '/file/list',
        component: FileList,
        exact: true,
        title: "文件列表"
    },
    {
        path: '/system/fileManage',
        component: FileManage,
        exact: true,
        title: "文件管理"
    },
    {
        path: '/system/user',
        component: SystemUser,
        exact: true,
        title: "用户管理"
    },
    {
        path: '/system/role',
        component: SystemRole,
        exact: true,
        title: "角色管理"
    },
    {
        path: '/system/appKey',
        component: SystemAppKey,
        exact: true,
        title: "AppKey设置"
    },
    {
        path: '/system/appLog',
        component: SystemAppLog,
        exact: true,
        title: "系统日志"
    },
    {
        path: '/about',
        component: About,
        exact: true,
        title: "关于"
    },
]

export default routers