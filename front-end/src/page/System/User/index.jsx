import React from "react";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";
import {
    Form,
    Input,
    Button,
    Table,
    Space,
    Modal,
    Switch,
    Select,
    message,
    Popconfirm,
} from "antd";
import { doGet, doPost, doPut, doDel } from "../../../utils/requestUtil";
import apis from "../../../config/setting";

export default class SystemUser extends CheckLogin {
    addModalButton = null;
    updateModalButton = null;

    UNSAFE_componentWillMount = () => {
        this.checkLogin();
        this.getAllRole();
        this.getAllUserData(null);
    };

    state = {
        tableData: [],
        roles: [],
        tableDataLoading: true,
        searchLoading: true,
        addUserVisible: false,
        addUserConfirmLoading: false,
        updateUserVisibles: [],
        updateUserConfirmLoading: false,
        detelePopconfirmVisibles: [],
        deleteconfirmLoading: false,
    };

    getAllRole = () => {
        const request = doGet(apis.systemApi + "/roles");
        request.then((response) => {
            this.setState({ roles: response.data });
        });
    };

    onFinish = (values) => {
        this.setState({ tableDataLoading: true, searchLoading: true });
        this.getAllUserData(values);
    };

    getAllUserData = (params) => {
        const { userManageApi } = apis;
        const responst = doGet(userManageApi, params);
        responst.then((responstData) => {
            let passupdateUserVisibles = [];
            let passdetelePopconfirmVisibles = [];
            for (const record in responstData.data) {
                if (Object.hasOwnProperty.call(responstData.data, record)) {
                    const element = responstData.data[record];
                    passupdateUserVisibles.push({
                        id: element.id,
                        visible: false,
                    });
                    passdetelePopconfirmVisibles.push({
                        id: element.id,
                        visible: false,
                    });
                }
            }
            /* passdetelePopconfirmVisibles = JSON.parse(
                JSON.stringify(passupdateUserVisibles)
            ); */
            this.setState({
                tableData: responstData.data,
                tableDataLoading: false,
                searchLoading: false,
                updateUserVisibles: passupdateUserVisibles,
                detelePopconfirmVisibles: passdetelePopconfirmVisibles,
            });
        });
    };

    openAddUserModal = (event) => {
        this.setState({ addUserVisible: true });
    };

    postNewUserData = (values) => {
        this.setState({ addUserConfirmLoading: true });
        const { roles } = this.state;
        values.roleId = roles
            .filter((item) => item.name === values.role)
            .map((item) => item.id)[0];
        console.log("values", values);
        const response = doPost(apis.userManageApi, values);
        response.then((item) => {
            if (item && item.code === 200) {
                message.success("新增用户成功");
            } else {
                message.error("新增用户失败, 请查看系统日志");
            }
            this.setState({
                addUserConfirmLoading: false,
                addUserVisible: false,
            });
            this.getAllUserData(null);
        });
    };

    generateAddUserForm = () => {
        const { roles } = this.state;
        const { Option } = Select;
        const childOption = roles.map((item) => (
            <Option value={item.name}>{item.name}</Option>
        ));
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
                    onFinish={this.postNewUserData}
                >
                    <Form.Item
                        label="用户名"
                        name="username"
                        rules={[
                            {
                                required: true,
                                message: "Please input username!",
                                max: 100,
                            },
                        ]}
                    >
                        <Input
                            ref={(input) => {
                                if (input) {
                                    input.focus();
                                }
                            }}
                        />
                    </Form.Item>
                    <Form.Item
                        label="密码"
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: "Please input password!",
                                max: 200,
                            },
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>
                    <Form.Item label="角色" name="role" initialValue="用户">
                        <Select defaultValue="用户" style={{ width: 120 }}>
                            {childOption}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        label="是否启用"
                        name="enable"
                        initialValue={true}
                    >
                        <Switch defaultChecked={true} />
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

    closerAddUserModal = () => {
        this.setState({ addUserVisible: false });
    };

    generateFormAndAction = () => {
        const {
            searchLoading,
            addUserVisible: showAddModalVisible,
            addUserConfirmLoading,
        } = this.state;
        return (
            <div>
                <Form layout="inline" onFinish={this.onFinish}>
                    <Form.Item name="searchName" label="用户名">
                        <Input placeholder="input username" allowClear={true} />
                    </Form.Item>
                    <Form.Item>
                        <Button
                            type="primary"
                            htmlType="submit"
                            loading={searchLoading}
                        >
                            搜索
                        </Button>
                    </Form.Item>
                    <Button
                        type="dashed"
                        loading={searchLoading}
                        onClick={this.openAddUserModal}
                    >
                        新增
                    </Button>
                </Form>
                <Modal
                    title="新增用户"
                    visible={showAddModalVisible}
                    onOk={() => this.addModalButton.click()}
                    confirmLoading={addUserConfirmLoading}
                    onCancel={this.closerAddUserModal}
                    maskClosable={false}
                    okText="确定"
                    cancelText="取消"
                    destroyOnClose={true}
                    keyboard={true}
                >
                    {this.generateAddUserForm()}
                </Modal>
            </div>
        );
    };

    openUpdateModal = (updateUserVisibles, index) => {
        console.log("openUpdateModal");
        updateUserVisibles[index].visible = true;
        this.setState({ updateUserVisibles });
    };

    postUpdateUserData = (values) => {
        this.setState({ updateUserConfirmLoading: true });
        const { roles } = this.state;
        values.roleId = roles
            .filter((item) => item.name === values.role)
            .map((item) => item.id)[0];
        console.log("values", values);
        const response = doPut(apis.userManageApi, values);
        response.then((item) => {
            if (item && item.code === 200) {
                message.success("修改用户成功");
                this.getAllUserData(null);
            } else {
                message.error("修改用户失败, 请查看系统日志");
            }
            this.closerUpdateModal();
            this.setState({
                updateUserConfirmLoading: false,
            });
        });
    };

    generateUpdateUserForm = (record) => {
        const { roles } = this.state;
        const { Option } = Select;
        const childOption = roles.map((item) => (
            <Option value={item.name}>{item.name}</Option>
        ));
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
                    onFinish={this.postUpdateUserData}
                >
                    <Form.Item
                        label="用户id"
                        name="id"
                        initialValue={record.id}
                        hidden={true}
                    />
                    <Form.Item
                        label="用户名"
                        name="username"
                        initialValue={record.userName}
                    >
                        <Input disabled />
                    </Form.Item>
                    <Form.Item
                        label="密码"
                        name="password"
                        rules={[{ required: false, max: 200 }]}
                    >
                        <Input.Password defaultValue="********" />
                    </Form.Item>
                    <Form.Item
                        label="角色"
                        name="role"
                        initialValue={record.roleName}
                    >
                        <Select
                            defaultValue={record.roleName}
                            style={{ width: 120 }}
                            disabled={record.systemDefault}
                        >
                            {childOption}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        label="是否启用"
                        name="enable"
                        initialValue={record.enable}
                    >
                        <Switch
                            disabled={record.systemDefault}
                            defaultChecked={record.enable}
                        />
                    </Form.Item>
                    <Form.Item style={{ display: "none" }}>
                        <Button
                            ref={(obj) => (this.updateModalButton = obj)}
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

    closerUpdateModal = () => {
        const { updateUserVisibles } = this.state;
        for (const key in updateUserVisibles) {
            if (Object.hasOwnProperty.call(updateUserVisibles, key)) {
                const element = updateUserVisibles[key];
                element.visible = false;
            }
        }
        this.setState({ updateUserVisibles });
    };

    closerDeletePopconfirm = () => {
        const { detelePopconfirmVisibles } = this.state;
        for (const key in detelePopconfirmVisibles) {
            if (Object.hasOwnProperty.call(detelePopconfirmVisibles, key)) {
                const element = detelePopconfirmVisibles[key];
                element.visible = false;
            }
        }
        this.setState({ detelePopconfirmVisibles });
    };

    openDeletePopconfirm = (detelePopconfirmVisibles, index) => {
        console.log("openDeletePopconfirm");
        detelePopconfirmVisibles[index].visible = true;
        this.setState({ detelePopconfirmVisibles });
    };

    postDeleteUserData = (record) => {
        this.setState({ deleteconfirmLoading: true });
        const response = doDel(apis.userManageApi, { id: record.id });
        response.then((responseData) => {
            if (responseData.code === 200) {
                message.success("删除用户成功");
                this.getAllUserData(null);
            } else {
                message.success("删除用户失败, 请查看系统日志");
            }
            this.closerDeletePopconfirm();
            this.setState({ deleteconfirmLoading: false });
        });
    };

    generateOperationButton = (text, record, index) => {
        const {
            updateUserVisibles,
            updateUserConfirmLoading,
            detelePopconfirmVisibles,
            deleteconfirmLoading,
        } = this.state;
        return (
            <Space size="middle">
                <Button
                    type="primary"
                    onClick={() =>
                        this.openUpdateModal(updateUserVisibles, index)
                    }
                >
                    修改
                </Button>
                <Popconfirm
                    title={`是否删除 ${record.userName}`}
                    visible={detelePopconfirmVisibles[index].visible}
                    onConfirm={() => this.postDeleteUserData(record)}
                    okButtonProps={{ loading: deleteconfirmLoading }}
                    onCancel={this.closerDeletePopconfirm}
                >
                    <Button
                        type="primary"
                        disabled={record.systemDefault}
                        danger
                        onClick={() => {
                            this.openDeletePopconfirm(
                                detelePopconfirmVisibles,
                                index
                            );
                        }}
                    >
                        删除
                    </Button>
                </Popconfirm>
                <Modal
                    title="修改用户"
                    visible={updateUserVisibles[index].visible}
                    onOk={() => this.updateModalButton.click()}
                    confirmLoading={updateUserConfirmLoading}
                    onCancel={this.closerUpdateModal}
                    maskClosable={false}
                    okText="修改"
                    cancelText="取消"
                    destroyOnClose={true}
                    keyboard={true}
                >
                    {this.generateUpdateUserForm(record)}
                </Modal>
            </Space>
        );
    };

    generateTable = () => {
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
                title: "角色",
                dataIndex: "roleName",
                key: "roleName",
            },
            {
                title: "状态",
                dataIndex: "enable",
                key: "enable",
                render: (record) => (record ? "启用" : "停用"),
            },
            {
                title: "创建时间",
                dataIndex: "createTime",
                key: "createTime",
            },
            {
                title: "修改时间",
                dataIndex: "updateTime",
                key: "updateTime",
            },
            {
                title: "操作",
                key: "action",
                render: (text, record, index) => {
                    return this.generateOperationButton(text, record, index);
                },
            },
        ];
        const { tableData, tableDataLoading } = this.state;
        return (
            <Table
                columns={columns}
                dataSource={tableData}
                loading={tableDataLoading}
            />
        );
    };

    content = () => {
        return (
            <div>
                {this.generateFormAndAction()}
                {this.generateTable()}
            </div>
        );
    };

    render() {
        return <Content view={this.content()} />;
    }
}
