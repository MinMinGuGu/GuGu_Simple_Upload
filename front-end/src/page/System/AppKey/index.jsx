import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";
import {
    Table,
    Form,
    Input,
    Button,
    Popconfirm,
    message,
    Modal,
    Select,
} from "antd";
import { doDel, doGet, doPost } from "../../../utils/requestUtil";
import apis from "../../../config/setting";

export default class SystemAppKey extends CheckLogin {
    addModalButton = null;

    UNSAFE_componentWillMount = () => {
        this.checkLogin();
        this.initAllUserName();
        this.initData(null);
    };

    initData = async (params) => {
        this.setState({ loading: true });
        let currPage = this.state.current || 1;
        let pageSize = this.state.pageSize || 10;
        const response = doGet(apis.appKeyApi, {
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
        addAppKeyVisible: false,
        addAppKeyVisiblem: false,
        addConfirmLoading: false,
        allUserName: [],
        dataSource: [],
        total: 0,
        pageSize: 10,
        current: 1,
    };

    formFinish = (values) => {
        this.initData(values.userName ? values : null);
    };

    postDeleteAppKey = (record) => {
        const response = doDel(apis.appKeyApi + "/" + record.id);
        response.then((result) => {
            if (result.code === 200) {
                message.success(`删除Key成功`);
            } else {
                message.error(`删除Key失败`);
            }
            this.setState({ loading: false });
            this.initData(null);
        });
    };

    generateOperationButton = (text, record, index) => {
        const { loading } = this.state;
        return (
            <Popconfirm
                title={`是否删除此Key?`}
                onConfirm={() => this.postDeleteAppKey(record)}
                okButtonProps={{ loading: loading }}
            >
                <Button type="primary" disabled={record.systemDefault} danger>
                    删除
                </Button>
            </Popconfirm>
        );
    };

    initAllUserName = async () => {
        const response = doGet(apis.userManageApi);
        let allUserName = [];
        await response.then((result) => {
            if (result.code === 200) {
                allUserName = result.data.map((item) => item.userName);
            }
        });
        this.setState({ allUserName });
    };

    postNewAppKeyData = (values) => {
        const response = doPost(apis.appKeyApi, values);
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

    generateAddAppKeyForm = () => {
        const { allUserName } = this.state;
        const { Option } = Select;
        let childOption = [];
        for (let i = 0; i < allUserName.length; i++) {
            const userName = allUserName[i];
            childOption.push(<Option value={userName}>{userName}</Option>);
        }
        return (
            <div>
                <Form
                    labelCol={{
                        span: 6,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}
                    initialValues={{
                        remember: true,
                    }}
                    onFinish={this.postNewAppKeyData}
                >
                    <Form.Item
                        label="用户"
                        name="userName"
                        rules={[
                            {
                                required: true,
                                message: "Please select userName!",
                            },
                        ]}
                    >
                        <Select>{childOption}</Select>
                    </Form.Item>
                    <Form.Item label="描述" name="description">
                        <Input />
                    </Form.Item>
                    <Form.Item style={{ display: "none" }}>
                        <Button
                            ref={(obj) => (this.addModalButton = obj)}
                            type="primary"
                            htmlType="submit"
                        >
                            Submit
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        );
    };

    getAppKeyData = (page, pageSize) => {
        this.setState({ loading: true });
        let currPage = page || this.state.current;
        pageSize = pageSize || this.state.pageSize;
        const response = doGet(`${apis.appKeyApi}`, {
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

    content = () => {
        const {
            loading,
            current,
            pageSize,
            total,
            dataSource,
            addAppKeyVisible,
            addConfirmLoading,
        } = this.state;
        const columns = [
            {
                title: "id",
                dataIndex: "id",
                key: "id",
            },
            {
                title: "用户名",
                dataIndex: "userName",
                key: "userName",
            },
            {
                title: "AppKey",
                dataIndex: "value",
                key: "value",
            },
            {
                title: "描述",
                dataIndex: "description",
                key: "description",
            },
            {
                title: "操作",
                key: "action",
                render: (text, record, index) => {
                    return this.generateOperationButton(text, record, index);
                },
            },
        ];
        return (
            <div>
                <Form layout="inline" onFinish={this.formFinish}>
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
                            htmlType="submit"
                            loading={loading}
                        >
                            搜索
                        </Button>
                    </Form.Item>
                    <Button
                        type="dashed"
                        loading={loading}
                        onClick={() =>
                            this.setState({ addAppKeyVisible: true })
                        }
                    >
                        新增
                    </Button>
                </Form>
                <Table
                    columns={columns}
                    dataSource={dataSource}
                    loading={loading}
                    pagination={{
                        current: current,
                        pageSize: pageSize,
                        total: total,
                        onChange: this.getAppKeyData,
                        hideOnSinglePage: true,
                        showTotal: (total) => `总共有 ${total} 项`,
                    }}
                />
                <Modal
                    title="新增AppKey"
                    visible={addAppKeyVisible}
                    onOk={() => this.addModalButton.click()}
                    confirmLoading={addConfirmLoading}
                    onCancel={() => this.setState({ addAppKeyVisible: false })}
                    maskClosable={false}
                    okText="确定"
                    cancelText="取消"
                    destroyOnClose={true}
                    keyboard={true}
                >
                    {this.generateAddAppKeyForm()}
                </Modal>
            </div>
        );
    };

    render() {
        return <Content view={this.content()} />;
    }
}
