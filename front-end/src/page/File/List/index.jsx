import React, { Component } from 'react'
import Main from '../../../layout/Main'

export default class FileList extends Component {

    generateComponent = () => {
        return "文件列表正在施工..."
    }

    content = () => {
        return {
            navPath: '文件管理/文件列表', content: this.generateComponent()
        }
    }

    render() {
        return <Main history={this.props.history} view={this.content()} />
    }
}
