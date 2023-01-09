import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";
import {
    Table,
    Form,
    Input,
    Button,
    message,
    Select,
    Modal,
    Row,
    Col,
    Space,
} from "antd";
import { doDel, doGet } from "../../../utils/requestUtil";
import apis from "../../../config/setting";
import { download } from "../../../utils/downloadUtil";

export default class SystemAppLog extends CheckLogin {
    form = null;

    UNSAFE_componentWillMount = () => {
        this.checkLogin();
        this.initData(null);
    };

    initData = async (params) => {
        this.setState({ loading: true });
        let currPage = this.state.current || 1;
        let pageSize = this.state.pageSize || 10;
        const response = doGet(apis.operationLogApi, {
            ...params,
            currPage,
            pageSize,
        });
        await response.then((result) => {
            if (result.code === 200) {
                const data = result.data;
                this.setState({
                    dataSource: data.records,
                    total: data.total,
                    pageSize: data.size,
                    current: data.current,
                });
            }
        });
        this.setState({ loading: false });
    };

    state = {
        loading: false,
        allUserName: [],
        dataSource: [],
        delOperationLogVisible: false,
        confirmLoading: false,
        total: 0,
        pageSize: 10,
        current: 1,
    };

    formFinish = (values) => {
        if (values.operationName && values.operationName === "所有操作") {
            values.operationName = null;
        }
        this.initData(values);
    };

    postNewAppKeyData = (values) => {
        const response = doGet(apis.appKeyApi, values);
        response.then((responseBody) => {
            if (responseBody && responseBody.code === 200) {
                message.success("新增Key成功");
            } else {
                message.error("创建Key失败, " + responseBody.message);
            }
            this.setState({
                addConfirmLoading: false,
                addAppKeyVisible: false,
            });
            this.initData(null);
        });
    };

    getOperationLogData = (page, pageSize) => {
        this.setState({ loading: true });
        let currPage = page || this.state.current;
        pageSize = pageSize || this.state.pageSize;
        const response = doGet(`${apis.operationLogApi}`, {
            currPage,
            pageSize,
        });
        response.then((result) => {
            if (result.code === 200) {
                const data = result.data;
                this.setState({
                    dataSource: data.records,
                    total: data.total,
                    pageSize: data.size,
                    current: data.current,
                    loading: false,
                });
            }
        });
    };

    delOperationLog = () => {
        this.setState({ confirmLoading: true });
        const response = doDel(apis.operationLogApi);
        response.then((responseBody) => {
            if (responseBody && responseBody.code === 200) {
                message.success("清空操作日志成功");
            } else {
                message.error("清空操作日志失败, " + responseBody.message);
            }
            this.setState({
                confirmLoading: false,
                delOperationLogVisible: false,
            });
            this.initData(null);
        });
    };

    exportLog = () => {
        this.setState({ loading: true });
        download(apis.operationLogApi + "/export");
        this.setState({ loading: false });
    };

    content = () => {
        const {
            loading,
            current,
            pageSize,
            total,
            dataSource,
            delOperationLogVisible,
            confirmLoading,
        } = this.state;
        const { Option } = Select;

        const columns = [
            {
                title: "id",
                dataIndex: "id",
                key: "id",
            },
            {
                title: "ip地址",
                dataIndex: "ipAddress",
                key: "ipAddress",
            },
            {
                title: "操作",
                dataIndex: "operationName",
                key: "operationName",
            },
            {
                title: "用户",
                dataIndex: "userName",
                key: "userName",
            },
            {
                title: "内容",
                dataIndex: "context",
                key: "context",
            },
            {
                title: "创建时间",
                dataIndex: "createTime",
                key: "createTime",
            },
        ];
        return (
            <div>
                <Row>
                    <Col>
                        <Form
                            layout="inline"
                            onFinish={this.formFinish}
                            ref={(obj) => (this.form = obj)}
                        >
                            <Form.Item name="ipAddress" label="ip地址">
                                <Input
                                    placeholder="input ipAddress"
                                    allowClear={true}
                                    disabled={loading}
                                />
                            </Form.Item>
                            <Form.Item name="operationName" label="操作类型">
                                <Select
                                    disabled={loading}
                                    defaultValue="所有操作"
                                >
                                    <Option value="所有操作">所有操作</Option>
                                    <Option value="未知操作">未知操作</Option>
                                    <Option value="用户登录">用户登录</Option>
                                    <Option value="文件上传">文件上传</Option>
                                    <Option value="文件删除">文件删除</Option>
                                    <Option value="新增用户">新增用户</Option>
                                    <Option value="修改用户">修改用户</Option>
                                    <Option value="删除用户">删除用户</Option>
                                    <Option value="新增角色">新增角色</Option>
                                    <Option value="修改角色">修改角色</Option>
                                    <Option value="删除角色">删除角色</Option>
                                    <Option value="新增AppKey">
                                        新增AppKey
                                    </Option>
                                    <Option value="删除AppKey">
                                        删除AppKey
                                    </Option>
                                </Select>
                            </Form.Item>
                            <Form.Item name="userName" label="用户名">
                                <Input
                                    placeholder="input userName"
                                    allowClear={true}
                                    disabled={loading}
                                />
                            </Form.Item>
                            <Form.Item>
                                <Button
                                    type="primary"
                                    loading={loading}
                                    onClick={() => this.form.resetFields()}
                                >
                                    清空条件
                                </Button>
                            </Form.Item>
                            <Form.Item>
                                <Button
                                    type="primary"
                                    htmlType="submit"
                                    loading={loading}
                                >
                                    搜索
                                </Button>
                            </Form.Item>
                        </Form>
                    </Col>
                    <Col>
                        <Space>
                            <Button
                                type="dashed"
                                loading={loading}
                                onClick={() =>
                                    this.setState({
                                        delOperationLogVisible: true,
                                    })
                                }
                                danger={true}
                            >
                                清空日志
                            </Button>
                            <Button
                                type="primary"
                                loading={loading}
                                onClick={() => this.exportLog()}
                            >
                                导出日志
                            </Button>
                        </Space>
                    </Col>
                </Row>
                <Table
                    columns={columns}
                    dataSource={dataSource}
                    loading={loading}
                    pagination={{
                        current: current,
                        pageSize: pageSize,
                        total: total,
                        onChange: this.getOperationLogData,
                        hideOnSinglePage: true,
                        showTotal: (total) => `总共有 ${total} 项`,
                    }}
                />
                <Modal
                    title="清空日志"
                    visible={delOperationLogVisible}
                    onOk={() => this.delOperationLog()}
                    confirmLoading={confirmLoading}
                    onCancel={() =>
                        this.setState({ delOperationLogVisible: false })
                    }
                    maskClosable={true}
                    okText="确定"
                    cancelText="取消"
                    destroyOnClose={true}
                    keyboard={true}
                >
                    确定要清空所有操作日志吗?
                </Modal>
            </div>
        );
    };

    render() {
        return <Content view={this.content()} />;
    }
}
