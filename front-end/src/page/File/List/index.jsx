import React from "react";
import Content from "../../../layout/Content";
import {
    Table,
    Button,
    Space,
    Modal,
    Upload,
    message,
    Form,
    Input,
    Radio,
    Row,
    Col,
} from "antd";
import { InboxOutlined } from "@ant-design/icons";
import apis from "../../../config/setting";
import { doGet } from "../../../utils/requestUtil";
import { download, batchDownloadToZip } from "../../../utils/downloadUtil";
import CheckComponent from "../../../components/CheckLogin";

export default class FileList extends CheckComponent {
    state = {
        selectedRowKeys: [],
        downloading: false,
        tableData: [],
        tableDataLoading: false,
        addFileVisible: false,
        addFileFlag: false,
    };

    downloadSelectFile = () => {
        const { selectedRowKeys } = this.state;
        this.setState({ downloading: true });
        if (selectedRowKeys.length === 1) {
            const fileId = selectedRowKeys[0];
            download(apis.fileApi + "/" + fileId);
            this.setState({ downloading: false });
        } else {
            const downloadLinks = selectedRowKeys.map(
                (fileId) => apis.fileApi + "/" + fileId
            );
            batchDownloadToZip(downloadLinks, "GET", () =>
                this.setState({ downloading: false })
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

    loadFileListData = (values) => {
        this.setState({
            tableDataLoading: true,
            selectedRowKeys: [],
            addFileFlag: false,
        });
        const response = doGet(apis.fileApi, values);
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

    formFinish = (values) => {
        if (!values.searchType) {
            values = { ...values, searchType: "fileName" };
        }
        this.loadFileListData(values);
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
                render: (value, row, index) => {
                    return (
                        <a href={`${apis.fileApi}/${value.id}`}>
                            {value.fileOriginal}
                        </a>
                    );
                },
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
            downloading,
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
                <Row>
                    <Col>
                        <Form layout="inline" onFinish={this.formFinish}>
                            <Form.Item label="搜索类型" name="searchType">
                                <Radio.Group
                                    disabled={tableDataLoading}
                                    defaultValue="fileName"
                                >
                                    <Radio.Button value="fileName">
                                        文件名
                                    </Radio.Button>
                                    <Radio.Button value="uploader">
                                        上传者
                                    </Radio.Button>
                                </Radio.Group>
                            </Form.Item>
                            <Form.Item name="value" label="值">
                                <Input
                                    placeholder="input value"
                                    allowClear={true}
                                    disabled={tableDataLoading}
                                />
                            </Form.Item>
                            <Form.Item>
                                <Button
                                    type="primary"
                                    htmlType="submit"
                                    loading={tableDataLoading}
                                >
                                    搜索
                                </Button>
                            </Form.Item>
                        </Form>
                    </Col>
                    <Col>
                        <Space>
                            <Button
                                type="primary"
                                onClick={() => {
                                    this.setState({ addFileVisible: true });
                                }}
                                loading={downloading}
                            >
                                上传
                            </Button>
                            <Button
                                type="primary"
                                onClick={this.downloadSelectFile}
                                disabled={!hasSelected}
                                loading={downloading}
                            >
                                下载
                            </Button>
                            <span>
                                {hasSelected
                                    ? `已选择 ${selectedRowKeys.length} 个文件`
                                    : ""}
                            </span>
                        </Space>
                    </Col>
                </Row>
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
