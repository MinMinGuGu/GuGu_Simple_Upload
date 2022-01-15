const proxy = require('http-proxy-middleware')

const env = process.env.APP_ENV;
const httpArgee = "http://";
const portEnv = {
    "dev": 80,
    "pro": 8848
};
const port = portEnv[env];
const requestHost = httpArgee + "localhost:" + port;

module.exports = function (app) {
    app.use(
        proxy('/api', {// 前缀请求触发
            target: requestHost, // 转发地址
            changeDrigin: true, // 控制服务器收到的响应头中Host字段的值
            pathRewrite: { '^/api': '/' }
        })
    )
}