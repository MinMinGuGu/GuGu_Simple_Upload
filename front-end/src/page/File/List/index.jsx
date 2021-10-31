import React from 'react'
import Main from '../../../layout/Main'
import { Table, Button } from 'antd'
import apis from '../../../config/setting'
import { doGet } from '../../../utils/requestUtil'
import CheckComponent from '../../../components/CheckLogin'

export default class FileList extends CheckComponent {

    state = {
        selectedRowKeys: [],
        loading: false,
        tableData: [],
    }

    start = () => {
        this.setState({ loading: true });
        setTimeout(() => {
            this.setState({
                selectedRowKeys: [],
                loading: false,
            });
        }, 1000);
    }

    onSelectChange = selectedRowKeys => {
        console.log('selectedRowKeys changed: ', selectedRowKeys);
        this.setState({ selectedRowKeys });
    }

    UNSAFE_componentWillMount = () => {
        this.checkLogin()
        const response = doGet(apis.fileApi)
        response.then(result => {
            this.setState({ tableData: result.data })
        })
    }

    columns = [
        {
            title: 'id',
            dataIndex: 'id',
        },
        {
            title: '文件名',
            dataIndex: 'fileOriginal',
        },
        {
            title: '文件大小',
            dataIndex: 'fileSize',
        },
        {
            title: '上传者',
            dataIndex: 'uploader',
        },
        {
            title: '上传日期',
            dataIndex: 'createTime',
        },
        {
            title: '操作',
            dataIndex: 'id',
        },
    ]

    generateComponent = () => {
        const { loading, selectedRowKeys, tableData } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;
        return (
            <div>
                <div style={{ marginBottom: 16 }}>
                    <Button type="primary" onClick={this.start} disabled={!hasSelected} loading={loading}>
                        下载
                    </Button>
                    <span style={{ marginLeft: 8 }}>
                        {hasSelected ? `已选择 ${selectedRowKeys.length} 个文件` : ''}
                    </span>
                </div>
                <Table rowSelection={rowSelection} columns={this.columns} dataSource={tableData} />
            </div>
        )
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
