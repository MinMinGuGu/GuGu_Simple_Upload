import React, { Component } from 'react'
import Main from '../../layout/Main'

export default class Home extends Component {

    render() {
        // todo 验证是否以及登录
        return (
            <div>
                <Main history={this.props.history} />
            </div>
        )
    }
}
