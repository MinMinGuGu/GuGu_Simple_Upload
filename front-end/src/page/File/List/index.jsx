import React from 'react'
import Main from '../../../layout/Main'
import { Table, Button } from 'antd'
import apis from '../../../config/setting'
import { doGet, doPost, doMethod } from '../../../utils/requestUtil'
import CheckComponent from '../../../components/CheckLogin'

export default class FileList extends CheckComponent {

    state = {
        selectedRowKeys: [],
        loading: false,
        tableData: [],
    }

    downloadFile = () => {
        this.setState({ loading: true });
        setTimeout(() => {
            this.setState({
                selectedRowKeys: [],
                loading: false,
            });
            this.loadFileListData()
        }, 1000);
        const { selectedRowKeys } = this.state
        for (const index in selectedRowKeys) {
            const fileId = selectedRowKeys[index]
            doPost(apis.fileApi + '/' + fileId)
        }
    }

    deleteFile = () => {
        this.setState({ loading: true });
        setTimeout(() => {
            this.setState({
                selectedRowKeys: [],
                loading: false,
            });
            this.loadFileListData()
        }, 1000);
        const { selectedRowKeys } = this.state
        for (const index in selectedRowKeys) {
            const fileId = selectedRowKeys[index]
            doMethod(apis.fileApi + '/' + fileId, 'delete')
        }
    }

    onSelectChange = selectedRowKeys => {
        this.setState({ selectedRowKeys });
    }

    UNSAFE_componentWillMount = () => {
        this.checkLogin()
        this.loadFileListData()
    }

    loadFileListData = () => {
        const response = doGet(apis.fileApi)
        response.then(result => {
            const data = result.data
            const resultData = []
            for (const index in data) {
                const item = data[index]
                resultData.push({ ...item, key: item.id })
            }
            this.setState({ tableData: resultData })
        })
    }

    columns = [
        {
            title: '编号',
            dataIndex: 'id',
            key: 'id'
        },
        {
            title: '文件名',
            dataIndex: 'fileOriginal',
            key: 'fileOriginal'
        },
        {
            title: '文件大小',
            dataIndex: 'fileSize',
            key: 'fileSize',
        },
        {
            title: '上传者',
            dataIndex: 'uploader',
            key: 'uploader',
        },
        {
            title: '上传日期',
            dataIndex: 'createTime',
            key: 'createTime',
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
                    <Button type="primary" onClick={this.downloadFile} disabled={!hasSelected} loading={loading}>
                        下载
                    </Button>
                    <Button style={{ marginLeft: 8 }} type="primary" onClick={this.deleteFile} disabled={!hasSelected} loading={loading}>
                        删除
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
