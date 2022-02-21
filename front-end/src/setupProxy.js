const proxy = require('http-proxy-middleware')

const env = process.env.REACT_APP_ENV;
const portEnv = {
    "dev": 80,
    "pro": 8848
};
const port = portEnv[env];
const httpArgee = "http://";
const requestHost = httpArgee + "localhost:" + port;
console.log("requestHost", requestHost)

module.exports = function (app) {
    app.use(
        proxy('/api', {// 前缀请求触发
            target: requestHost, // 转发地址
            changeDrigin: true, // 控制服务器收到的响应头中Host字段的值
            pathRewrite: { '^/api': '/' }
        })
    )
}