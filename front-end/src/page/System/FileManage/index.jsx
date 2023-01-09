import React from "react";
import Content from "../../../layout/Content";
import {
    Table,
    Button,
    message,
    Form,
    Radio,
    Row,
    Col,
    Input,
    Space,
    Popconfirm,
    Modal,
} from "antd";
import { DeleteOutlined } from "@ant-design/icons";
import apis from "../../../config/setting";
import { doGet, doMethod } from "../../../utils/requestUtil";
import CheckComponent from "../../../components/CheckLogin";

export default class FileManage extends CheckComponent {
    state = {
        selectedRowKeys: [],
        selectedRowFileNames: [],
        loading: false,
        tableData: [],
        tableDataLoading: false,
        delFileVisible: false,
    };

    deleteFile = async (fileId) => {
        this.setState({ loading: true });
        await doMethod(apis.fileApi + "/" + fileId, "delete");
        message.success("删除成功");
        this.loadFileListData();
    };

    batchDeleteFile = async () => {
        this.setState({ loading: true, delFileVisible: false });
        const { selectedRowKeys } = this.state;
        for (const index in selectedRowKeys) {
            const fileId = selectedRowKeys[index];
            this.deleteFile(fileId);
        }
        this.setState({
            selectedRowKeys: [],
            selectedRowFileNames: [],
        });
    };

    onSelectChange = (selectedRowKeys) => {
        this.setState({ selectedRowKeys });
        const { tableData } = this.state;
        let selectedRowFileNames = [];
        selectedRowKeys.forEach((value, index) => {
            selectedRowFileNames.push(tableData[index].fileOriginal);
        });
        this.setState({ selectedRowFileNames });
    };

    UNSAFE_componentWillMount = () => {
        this.checkLogin();
        this.loadFileListData();
    };

    loadFileListData = (values) => {
        this.setState({
            tableDataLoading: true,
            selectedRowKeys: [],
            selectedRowFileNames: [],
            loading: false,
            delFileVisible: false,
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

    formFinish = (values) => {
        if (!values.searchType) {
            values = { ...values, searchType: "fileName" };
        }
        this.loadFileListData(values);
    };

    generateSelectFile = () => {
        const { selectedRowKeys, tableData } = this.state;
        selectedRowKeys.forEach((value, index) => {
            const row = tableData[index];
            console.log(row);
        });
        return 233;
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
                title: "action",
                render: (value, row, index) => (
                    <Popconfirm
                        title="是否要删除文件?"
                        onConfirm={() => this.deleteFile(value.id)}
                    >
                        <DeleteOutlined />
                    </Popconfirm>
                ),
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
            delFileVisible,
            selectedRowFileNames,
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
                                onClick={() =>
                                    this.setState({ delFileVisible: true })
                                }
                                disabled={!hasSelected}
                                loading={loading}
                            >
                                删除
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
                    title="确认删除选中文件"
                    visible={delFileVisible}
                    onOk={() => this.batchDeleteFile()}
                    confirmLoading={loading}
                    onCancel={() => this.setState({ delFileVisible: false })}
                    maskClosable={true}
                    okText="确定"
                    cancelText="取消"
                    destroyOnClose={true}
                    keyboard={true}
                >
                    <Row>
                        以下为选中删除的{selectedRowFileNames.length}个文件:
                    </Row>
                    {selectedRowFileNames.map((name) => (
                        <Row>{name}</Row>
                    ))}
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
