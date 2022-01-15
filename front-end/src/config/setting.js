console.log('process.env', process.env.REACT_APP_ENV)
// 接口前缀  不要修改
const apiPrefix = "/api"
// 接口配置
let apis = {
    devPort: 80,
    proPort: 8848,
    backendUri: 'http://localhost',
    loginApi: apiPrefix + "/login",
    fileApi: apiPrefix + "/file",
    systemApi: apiPrefix + "/system",
}

export default apis