import React from "react";
import apis from "../../../config/setting";
import CheckComponent from "../../../components/CheckLogin";

import { Upload, message } from "antd";
import { InboxOutlined } from "@ant-design/icons";
import Content from "../../../layout/Content";

const uploadProps = {
    name: "file",
    multiple: true,
    action: apis.fileApi,
    onChange(info) {
        const { status } = info.file;
        if (status !== "uploading") {
            console.log(info.file, info.fileList);
        }
        if (status === "done") {
            message.success(`${info.file.name} 文件上传成功`);
        } else if (status === "error") {
            message.error(`${info.file.name} 文件上传失败`);
        }
    },
    onDrop(e) {
        console.log("Dropped files", e.dataTransfer.files);
    },
};

export default class FileUpload extends CheckComponent {
    generateComponent = () => {
        return (
            <Upload.Dragger {...uploadProps}>
                <p className="ant-upload-drag-icon">
                    <InboxOutlined />
                </p>
                <p className="ant-upload-text">点击上传</p>
                <p className="ant-upload-hint">支持拖拽上传</p>
            </Upload.Dragger>
        );
    };

    content = () => {
        return this.generateComponent();
    };

    render() {
        return <Content view={this.content()} />;
    }
}
