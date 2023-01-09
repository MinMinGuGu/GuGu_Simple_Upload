import React from "react";
import Content from "../../../layout/Content";
import { Table, Button, message } from "antd";
import apis from "../../../config/setting";
import { doGet, doMethod } from "../../../utils/requestUtil";
import CheckComponent from "../../../components/CheckLogin";

export default class FileManage extends CheckComponent {
    state = {
        selectedRowKeys: [],
        loading: false,
        tableData: [],
        tableDataLoading: true,
    };

    deleteFile = () => {
        this.setState({ loading: true });
        setTimeout(() => {
            this.setState({
                selectedRowKeys: [],
                loading: false,
            });
            message.success("删除成功");
            this.loadFileListData();
        }, 1000);
        const { selectedRowKeys } = this.state;
        for (const index in selectedRowKeys) {
            const fileId = selectedRowKeys[index];
            doMethod(apis.fileApi + "/" + fileId, "delete");
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
        // todo 重写 参考 ant 表单
        const { loading, selectedRowKeys, tableData, tableDataLoading } =
            this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;
        return (
            <div>
                <div style={{ marginBottom: 16 }}>
                    <Button
                        style={{ marginLeft: 8 }}
                        type="primary"
                        onClick={this.deleteFile}
                        disabled={!hasSelected}
                        loading={loading}
                    >
                        删除
                    </Button>
                    <span style={{ marginLeft: 8 }}>
                        {hasSelected
                            ? `已选择 ${selectedRowKeys.length} 个文件`
                            : ""}
                    </span>
                </div>
                <Table
                    rowSelection={rowSelection}
                    columns={columns}
                    dataSource={tableData}
                    loading={tableDataLoading}
                />
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