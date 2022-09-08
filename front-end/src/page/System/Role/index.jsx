import React from "react";
import {
    Table,
    Form,
    Input,
    Button,
    Space,
    Popconfirm,
    message,
    Modal,
    Transfer,
    Switch,
} from "antd";
import CheckLogin from "../../../components/CheckLogin";
import Content from "../../../layout/Content";
import { doGet, doMethod, doPost, doPut } from "../../../utils/requestUtil";
import apis from "../../../config/setting";

export default class SystemRole extends CheckLogin {
    addModalButton = null;

    UNSAFE_componentWillMount = () => {
        this.checkLogin();
        this.initData(null);
    };

    state = {
        dataSource: [],
        total: 0,
        pageSize: 10,
        current: 1,
        loading: false,
        modalVisible: false,
        modalComponsele: null,
        permissionData: [],
        selectedKeys: [],
        targetKeys: [],
        modalRoleId: null,
        addRoleVisible: false,
        addConfirmLoading: false,
    };

    initData = async (params) => {
        this.setState({ loading: true });
        let currPage = this.state.current || 1;
        let pageSize = this.state.pageSize || 10;
        this.setState({ loading: true });
        const roleDataResponse = doGet(`${apis.roleManageApi}`, {
            ...params,
            currPage,
            pageSize,
        });
        await roleDataResponse.then((result) => {
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
        // allPermissionData
        const permissionDataResponse = doGet(`${apis.permissionApi}s`);
        await permissionDataResponse.then((result) => {
            if (result.code === 200) {
                let passData = [];
                for (const key in result.data) {
                    if (Object.hasOwnProperty.call(result.data, key)) {
                        const element = result.data[key];
                        element.key = element.id;
                        passData.push(element);
                    }
                }
                this.setState({ permissionData: passData });
            }
        });
        this.setState({ loading: false });
    };

    getRoleData = (page, pageSize) => {
        this.setState({ loading: true });
        let currPage = page || this.state.current;
        pageSize = pageSize || this.state.pageSize;
        this.setState({ loading: true });
        const response = doGet(`${apis.roleManageApi}`, {
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
                });
            }
        });
    };

    formFinish = (values) => {
        this.initData(values.searchName ? { name: values.searchName } : null);
    };

    postDeleteRole = (record) => {
        const response = doMethod(
            `${apis.roleManageApi}/${record.id}`,
            "DELETE"
        );
        response.then((result) => {
            if (result.code === 200) {
                message.success(`删除 ${record.name} 角色成功`);
            } else {
                message.error(`删除 ${record.name} 角色失败`);
            }
            this.setState({ loading: false });
            this.initData(null);
        });
    };

    getRolePermission = async (record) => {
        // 发出请求初始化selectedKeys,targetKeys
        this.setState({ loading: true, modalRoleId: record.id });
        const { permissionData } = this.state;
        const response = doGet(`${apis.roleManageApi}/permission/${record.id}`);
        await response.then((result) => {
            if (result.code === 200) {
                const data = result.data;
                console.log(`data`, data);
                let selfPermission = data.map((item) => item.permissionId);
                console.log(`selfPermission`, selfPermission);
                let unSelfPermission = [];
                let passSelfPermission = new Set(selfPermission);
                for (const key in permissionData) {
                    if (Object.hasOwnProperty.call(permissionData, key)) {
                        const element = permissionData[key];
                        const permissionId = element.id;
                        if (!passSelfPermission.has(permissionId)) {
                            // selfPermission no has permissionId
                            unSelfPermission.push(permissionId);
                        }
                    }
                }
                console.log(`unSelfPermission`, unSelfPermission);
                this.setState({
                    targetKeys: selfPermission,
                    modalVisible: true,
                });
            } else {
                message.error("获取角色授权信息失败");
            }
        });
        this.setState({ loading: false });
    };

    generateOperationButton = (text, record, index) => {
        const { loading } = this.state;
        return (
            <Space size="middle">
                <Button
                    type="primary"
                    disabled={record.name === "超级管理员"}
                    onClick={() => this.getRolePermission(record)}
                >
                    授权
                </Button>
                <Popconfirm
                    title={`是否删除 ${record.name} 角色`}
                    onConfirm={() => this.postDeleteRole(record)}
                    okButtonProps={{ loading: loading }}
                    onCancel={this.closerDeletePopconfirm}
                    disabled={record.systemDefault}
                >
                    <Button
                        type="primary"
                        disabled={record.systemDefault}
                        danger
                    >
                        删除
                    </Button>
                </Popconfirm>
            </Space>
        );
    };

    onChange = (nextTargetKeys, direction, moveKeys) => {
        console.log("targetKeys:", nextTargetKeys);
        console.log("direction:", direction);
        console.log("moveKeys:", moveKeys);
        this.setState({ targetKeys: nextTargetKeys });
    };

    onSelectChange = (sourceSelectedKeys, targetSelectedKeys) => {
        console.log("sourceSelectedKeys:", sourceSelectedKeys);
        console.log("targetSelectedKeys:", targetSelectedKeys);
        this.setState({
            selectedKeys: [...sourceSelectedKeys, ...targetSelectedKeys],
        });
    };

    modalOk = (modalRoleId) => {
        const { targetKeys } = this.state;
        console.log(`modal onOk modalRoleId`, modalRoleId);
        console.log(`modal onOk targetKeys`, targetKeys);
        // todo 提交权限数据 targetKeys permissionId
        const response = doPost(
            `${apis.rolePermissionManageApi}/${modalRoleId}`,
            targetKeys
        );
        response.then((responseData) => {
            if (responseData && responseData.code === 200) {
                message.success("角色权限更新成功");
            } else {
                message.success("角色权限更新失败, 请查看系统日志");
            }
            this.setState({
                modalVisible: false,
                selectedKeys: [],
                targetKeys: [],
            });
        });
    };

    postNewRoleData = (values) => {
        this.setState({ addConfirmLoading: true });
        const response = doPost(apis.roleManageApi, values);
        response.then((responseBody) => {
            if (responseBody && responseBody.code === 200) {
                message.success("新增角色成功");
            } else {
                message.error("新增角色失败, 请查看系统日志");
            }
            this.setState({
                addConfirmLoading: false,
                addRoleVisible: false,
            });
            this.initData(null);
        });
    };

    generateAddRoleForm = () => {
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
                    onFinish={this.postNewRoleData}
                >
                    <Form.Item
                        label="角色名"
                        name="name"
                        rules={[
                            {
                                required: true,
                                message: "Please input rolename!",
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

    updateRoleStatus = (record, enable) => {
        record = { ...record, enable };
        const response = doPut(apis.roleManageApi, record);
        response.then((responseData) => {
            if (responseData && responseData.code === 200) {
                message.success("角色状态已更新");
            } else {
                message.error("更新角色状态失败, 请查看系统日志");
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
            modalVisible,
            permissionData,
            selectedKeys,
            targetKeys,
            modalRoleId,
            addRoleVisible,
            addConfirmLoading,
        } = this.state;
        const columns = [
            {
                title: "id",
                dataIndex: "id",
                key: "id",
            },
            {
                title: "角色名",
                dataIndex: "name",
                key: "name",
            },
            {
                title: "状态",
                dataIndex: "enable",
                key: "enable",
                render: (text, record, index) => {
                    return (
                        <Switch
                            checkedChildren="启用"
                            unCheckedChildren="停用"
                            disabled={record.systemDefault}
                            defaultChecked={record.enable}
                            onChange={(enable) =>
                                this.updateRoleStatus(record, enable)
                            }
                        />
                    );
                },
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
                    <Form.Item name="searchName" label="角色名">
                        <Input
                            placeholder="input role name"
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
                        onClick={() => this.setState({ addRoleVisible: true })}
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
                        onChange: this.getRoleData,
                        hideOnSinglePage: true,
                        showTotal: (total) => `总共有 ${total} 项`,
                    }}
                />
                <Modal
                    title="角色授权"
                    visible={modalVisible}
                    onOk={() => this.modalOk(modalRoleId)}
                    onCancel={() => {
                        this.setState({
                            modalVisible: false,
                            selectedKeys: [],
                            targetKeys: [],
                        });
                    }}
                >
                    <Transfer
                        dataSource={permissionData}
                        selectedKeys={selectedKeys}
                        targetKeys={targetKeys}
                        titles={["权限列表", "已有权限"]}
                        onChange={this.onChange}
                        onSelectChange={this.onSelectChange}
                        render={(item) => item.name}
                    />
                </Modal>
                <Modal
                    title="新增角色"
                    visible={addRoleVisible}
                    onOk={() => this.addModalButton.click()}
                    confirmLoading={addConfirmLoading}
                    onCancel={() => this.setState({ addRoleVisible: false })}
                    maskClosable={false}
                    okText="确定"
                    cancelText="取消"
                    destroyOnClose={true}
                    keyboard={true}
                >
                    {this.generateAddRoleForm()}
                </Modal>
            </div>
        );
    };

    render() {
        return <Content view={this.content()} />;
    }
}
