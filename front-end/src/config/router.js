import Login from '../page/Login';
import Home from '../page/Home'
import FileUpload from '../page/File/Upload'
import FileList from '../page/File/List'
import SystemAppKey from '../page/System/AppKey'
import SystemManage from '../page/System/Manage'
import SystemPermission from '../page/System/Permission'
import SystemRole from '../page/System/Role'
import SystemUser from '../page/System/User'
import About from '../page/About'
import SystemAppLog from '../page/System/AppLog';

const routers = [
    {
        path: '/',
        component: Home,
        exact: true,
    },
    {
        path: '/login',
        component: Login,
        exact: true,
    },
    {
        path: '/file/upload',
        component: FileUpload,
        exact: true,
    },
    {
        path: '/file/list',
        component: FileList,
        exact: true,
    },
    {
        path: '/system/user',
        component: SystemUser,
        exact: true,
    },
    {
        path: '/system/role',
        component: SystemRole,
        exact: true,
    },
    {
        path: '/system/permission',
        component: SystemPermission,
        exact: true,
    },
    {
        path: '/system/manage',
        component: SystemManage,
        exact: true,
    },
    {
        path: '/system/appKey',
        component: SystemAppKey,
        exact: true,
    },
    {
        path: '/system/appLog',
        component: SystemAppLog,
        exact: true,
    },
    {
        path: '/about',
        component: About,
        exact: true,
    },
]

export default routers