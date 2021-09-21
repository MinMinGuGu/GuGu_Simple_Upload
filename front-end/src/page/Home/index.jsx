import React, { Component } from 'react'
import Main from '../../layout/Main'

export default class Home extends Component {

    componentWillMount() {
        console.log('Home componentWillMount...')
    }

    render() {
        // todo 验证是否以及登录
        return (
            <div>
                <Main />
            </div>
        )
    }
}
