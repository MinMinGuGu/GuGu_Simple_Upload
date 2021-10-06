import React, { Component } from 'react'
import Main from '../../../layout/Main'
import "antd/dist/antd.css"

export default class FileUpload extends Component {

    content = () => {
        return { navPath: '文件管理/文件上传', content: '文件上传待施工...' }
    }

    render() {
        return <Main history={this.props.history} view={this.content()} />
    }
}
