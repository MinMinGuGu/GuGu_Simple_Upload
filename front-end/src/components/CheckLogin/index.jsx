import { Component } from 'react'
import { doGet } from '../../utils/requestUtil'
import apis from '../../config/setting'

export default class CheckComponent extends Component {

    UNSAFE_componentWillMount = () => {
        this.checkLogin()
    }

    checkLogin = () => {
        console.log("check login...")
        const result = doGet(apis.loginApi)
        result.then(data => {
            if (data.code !== 200) {
                this.props.history.push('/login')
            }
        })
    }
}
