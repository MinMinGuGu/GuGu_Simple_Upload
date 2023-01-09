import React from "react";
import Content from "../../../layout/Content";
import { Table, Button, Space, Modal, Upload, message } from "antd";
import { InboxOutlined } from "@ant-design/icons";
import apis from "../../../config/setting";
import { doGet, doMethodByDownload } from "../../../utils/requestUtil";
import { creteALinkDownload } from "../../../utils/downloadUtil";
import CheckComponent from "../../../components/CheckLogin";

export default class FileList extends CheckComponent {
    state = {
        selectedRowKeys: [],
        loading: false,
        tableData: [],
        tableDataLoading: true,
        addFileVisible: false,
        addFileFlag: false,
    };

    downloadFile = async () => {
        const { selectedRowKeys } = this.state;
        for (const index in selectedRowKeys) {
            const fileId = selectedRowKeys[index];
            const response = doMethodByDownload(
                apis.fileApi + "/" + fileId,
                "GET"
            );
            this.setState({ loading: true });
            creteALinkDownload(response, () =>
                this.setState({ loading: false })
            );
        }
    };

    onSelectChange = (selectedRowKeys) => {
        this.setState({ selectedRowKeys });
    };

    UNSAFE_componentWillMount = () => {
        this.checkLogin();
        this.loadFileListData();
    };

    loadFileListData = () => {
        const response = doGet(apis.fileApi);
        response.then((result) => {
            const data = result.data;
            const resultData = [];
            for (const index in data) {
                const item = data[index];
                resultData.push({ ...item, key: item.id });
            }
            this.setState({ tableData: resultData, tableDataLoading: false });
        });
    };

    checkUploadFile = () => {
        this.setState({ addFileVisible: false });
        const { addFileFlag } = this.state;
        if (addFileFlag) {
            this.loadFileListData();
        }
    };

    generateComponent = () => {
        const columns = [
            {
                title: "编号",
                dataIndex: "id",
                key: "id",
            },
            {
                title: "文件名",
                dataIndex: "fileOriginal",
                key: "fileOriginal",
            },
            {
                title: "文件大小",
                dataIndex: "fileSize",
                key: "fileSize",
            },
            {
                title: "上传者",
                dataIndex: "uploader",
                key: "uploader",
            },
            {
                title: "更新日期",
                dataIndex: "updateTime",
                key: "updateTime",
            },
        ];
        const {
            loading,
            selectedRowKeys,
            tableData,
            tableDataLoading,
            addFileVisible,
        } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;
        return (
            <div>
                <div style={{ marginBottom: 16 }}>
                    <Space>
                        <Button
                            type="primary"
                            onClick={() => {
                                this.setState({ addFileVisible: true });
                            }}
                            loading={loading}
                        >
                            上传
                        </Button>
                        <Button
                            type="primary"
                            onClick={this.downloadFile}
                            disabled={!hasSelected}
                            loading={loading}
                        >
                            下载
                        </Button>
                        <span>
                            {hasSelected
                                ? `已选择 ${selectedRowKeys.length} 个文件`
                                : ""}
                        </span>
                    </Space>
                </div>
                <Table
                    rowSelection={rowSelection}
                    columns={columns}
                    dataSource={tableData}
                    loading={tableDataLoading}
                />
                <Modal
                    title="上传文件"
                    visible={addFileVisible}
                    onCancel={this.checkUploadFile}
                    onOk={this.checkUploadFile}
                    maskClosable={false}
                >
                    <Upload.Dragger
                        name="file"
                        multiple={true}
                        action={apis.fileApi}
                        directory={false}
                        onChange={(info) => {
                            const { status } = info.file;
                            if (status !== "uploading") {
                                console.log(info.file, info.fileList);
                            }
                            if (status === "done") {
                                message.success(
                                    `${info.file.name} 文件上传成功`
                                );
                                this.setState({ addFileFlag: true });
                            } else if (status === "error") {
                                message.error(`${info.file.name} 文件上传失败`);
                            }
                        }}
                        onDrop={(e) => {
                            console.log("Dropped files", e.dataTransfer.files);
                        }}
                    >
                        <p className="ant-upload-drag-icon">
                            <InboxOutlined />
                        </p>
                        <p className="ant-upload-text">点击上传</p>
                        <p className="ant-upload-hint">支持拖拽上传</p>
                    </Upload.Dragger>
                </Modal>
            </div>
        );
    };

    content = () => {
        return this.generateComponent();
    };

    render() {
        return <Content view={this.content()} />;
    }
}
