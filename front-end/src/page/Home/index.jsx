import React, { Component } from 'react'
import Main from '../../layout/Main'

export default class Home extends Component {

    content = () => {
        return { navPath: '概览', content: '概览待施工...' }
    }

    render() {
        // todo 验证是否以及登录
        return (
            <div>
                <Main history={this.props.history} view={this.content()} />
            </div>
        )
    }
}
