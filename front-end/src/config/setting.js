const env = process.env.APP_ENV;
const apiPrefix = "/api";

const apis = {
    loginApi: apiPrefix + "/login",
    fileApi: apiPrefix + "/file",
    systemApi: apiPrefix + "/system",
    userManageApi: apiPrefix + "/system/account",
    roleManageApi: apiPrefix + "/system/role",
    rolePermissionManageApi: apiPrefix + "/system/role/permission",
    permissionApi: apiPrefix + "/system/permission",
    appKeyApi: apiPrefix + "/system/appKey",
    operationLogApi: apiPrefix + "/system/operationLog",
    env
}

export default apis