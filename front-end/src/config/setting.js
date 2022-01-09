// 接口前缀  不要修改
const apiPrefix = "/api"
// 接口配置
let apis = {
    defaultPort: 80,
    devPort: 80,
    proPort: 8841,
    backendUri: 'http://localhost',
    loginApi: apiPrefix + "/login",
    fileApi: apiPrefix + "/file",
    systemApi: apiPrefix + "/system",
}

export default apis