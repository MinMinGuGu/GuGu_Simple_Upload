import React, { Component } from 'react'
import Main from '../../../layout/Main'
import apis from '../../../config/setting'

import { Upload, message } from 'antd'
import { InboxOutlined } from '@ant-design/icons'

const uploadProps = {
    name: 'file',
    multiple: true,
    action: apis.fileApi,
    onChange(info) {
        const { status } = info.file;
        if (status !== 'uploading') {
            console.log(info.file, info.fileList);
        }
        if (status === 'done') {
            message.success(`${info.file.name} file uploaded successfully.`);
        } else if (status === 'error') {
            message.error(`${info.file.name} file upload failed.`);
        }
    },
    onDrop(e) {
        console.log('Dropped files', e.dataTransfer.files);
    },
}

export default class FileUpload extends Component {

    generateComponent = () => {
        return (
            <div>
                <Upload {...uploadProps}>
                    <p className="ant-upload-drag-icon">
                        <InboxOutlined />
                    </p>
                    <p className="ant-upload-text">Click or drag file to this area to upload</p>
                    <p className="ant-upload-hint">
                        Support for a single or bulk upload. Strictly prohibit from uploading company data or other
                        band files
                    </p>
                </Upload>
            </div>
        )
    }

    content = () => {
        return {
            navPath: '文件管理/文件上传', content: this.generateComponent()
        }
    }


    render() {
        return <Main history={this.props.history} view={this.content()} />
    }
}
