const env = process.env.APP_ENV;
const apiPrefix = "/api";

const apis = {
    loginApi: apiPrefix + "/login",
    fileApi: apiPrefix + "/file",
    systemApi: apiPrefix + "/system",
    userManageApi: apiPrefix + "/system/account",
    env
}

export default apis