import React from 'react'
import Main from '../../layout/Main'
import CheckLogin from '../../components/CheckLogin'

export default class Home extends CheckLogin {

    content = () => {
        return { navPath: '概览', content: '概览待施工...' }
    }

    render() {
        return (
            <div>
                <Main history={this.props.history} view={this.content()} />
            </div>
        )
    }
}
