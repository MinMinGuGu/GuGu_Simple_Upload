const proxy = require('http-proxy-middleware')

// todo 思考如何在前端自定义环境变量
const env = process.env.NODE_env;
let serverPort;
if (env === 'dev') {
    serverPort = 80
} else {
    serverPort = 8841
}

module.exports = function (app) {
    app.use(
        proxy('/api', {// 前缀请求触发
            target: 'http://localhost:80', // 转发地址
            changeDrigin: true, // 控制服务器收到的响应头中Host字段的值
            pathRewrite: { '^/api': '/' }
        })
    )
}