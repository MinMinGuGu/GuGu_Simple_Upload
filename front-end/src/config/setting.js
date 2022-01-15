const env = process.env.APP_ENV
const apiPrefix = "/api";
const rootFlag = "//"
const port = env === 'dev' ? 80 : 8848;
const requestHost = rootFlag + "localhost" + ":" + port;
// 接口配置
let apis = {
    devPort: 80,
    proPort: 8848,
    loginApi: requestHost + apiPrefix + "/login",
    fileApi: requestHost + apiPrefix + "/file",
    systemApi: requestHost + apiPrefix + "/system",
    env
}

export default apis