const env = process.env.APP_ENV;
const apiPrefix = "/api";

let apis = {
    loginApi: apiPrefix + "/login",
    fileApi: apiPrefix + "/file",
    systemApi: apiPrefix + "/system",
    env
}

export default apis