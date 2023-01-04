import React from "react";
import Content from "../../../layout/Content";
import { Table, Button } from "antd";
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
                        type="primary"
                        onClick={this.downloadFile}
                        disabled={!hasSelected}
                        loading={loading}
                    >
                        下载
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
