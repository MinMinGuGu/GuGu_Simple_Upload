import React from "react";
import Main from "../../layout/Main";
import CheckLogin from "../../components/CheckLogin";
import { Line } from "@ant-design/plots";
import { doGet } from "../../utils/requestUtil";
import { getDateFormat } from "../../utils/dateUtil";
import apis from "../../config/setting";
import { Row, Col, Divider, Card, Statistic } from "antd";

export default class Home extends CheckLogin {
    state = {
        weekUploadData: [],
        weekUploadDataLoading: true,
        fileUploadInfoData: {
            systemFileCount: 0,
            userFileUploadCount: 0,
        },
        fileUploadInfoLoading: true,
    };

    UNSAFE_componentWillMount = () => {
        this.checkLogin();
        this.initWeekUploadData();
        this.initFileUploadInfoData();
    };

    initFileUploadInfoData = async () => {
        const fileUploadInfoData = {
            systemFileCount: 0,
            userFileUploadCount: 0,
        };
        const systemResponse = doGet(apis.systemApi + "/fileUpload/info");
        await systemResponse.then((result) => {
            const { data } = result;
            if (data) {
                const { systemFileCount } = data;
                fileUploadInfoData.systemFileCount = systemFileCount;
            }
        });
        const accountResponse = doGet(
            apis.systemApi + "/account/fileUpload/info"
        );
        await accountResponse.then((result) => {
            const { data } = result;
            if (data) {
                const { userFileUploadCount } = data;
                fileUploadInfoData.userFileUploadCount = userFileUploadCount;
            }
        });
        this.setState({ fileUploadInfoData, fileUploadInfoLoading: false });
    };

    getDefaultData = () => {
        let passData = [];
        let currDate = new Date();
        for (let i = 6; i >= 0; i--) {
            let dateSub = 24 * 60 * 60 * 1000 * i;
            let dateTime = currDate.getTime() - dateSub;
            let dataFormat = getDateFormat(new Date(dateTime));
            passData.push({
                createTime: dataFormat,
                fileUploadCount: 0,
            });
        }
        return passData;
    };

    initWeekUploadData = async () => {
        let handledData = this.getDefaultData();
        const response = doGet(apis.systemApi + "/fileUpload/week/info");
        await response.then((result) => {
            const { data } = result;
            if (data && data.length) {
                data.forEach((item) => {
                    for (let i = 0; i < handledData.length; i++) {
                        const { createTime: defaultCreateTime } =
                            handledData[i];
                        const {
                            createTime: responseCreateTime,
                            fileUploadCount,
                        } = item;
                        if (responseCreateTime === defaultCreateTime) {
                            handledData[i] = {
                                createTime: responseCreateTime,
                                fileUploadCount,
                            };
                            break;
                        }
                    }
                });
            }
        });
        this.setState({
            weekUploadData: handledData,
            weekUploadDataLoading: false,
        });
    };

    generateWeekUploadInfo() {
        const { weekUploadData, weekUploadDataLoading } = this.state;
        const config = {
            data: weekUploadData,
            xField: "createTime",
            yField: "fileUploadCount",
            meta: {
                fileUploadCount: { alias: "上传文件数" },
            },
            label: {},
            point: {
                size: 5,
                shape: "diamond",
                style: {
                    fill: "white",
                    stroke: "#5B8FF9",
                    lineWidth: 2,
                },
            },
            smooth: false,
            tooltip: {
                showMarkers: false,
            },
            state: {
                active: {
                    style: {
                        shadowBlur: 4,
                        stroke: "#000",
                        fill: "red",
                    },
                },
            },
            interactions: [
                {
                    type: "marker-active",
                },
            ],
        };
        return (
            <div style={{ textAlign: "center" }}>
                <Card
                    title="系统近7天文件上传数据表"
                    loading={weekUploadDataLoading}
                >
                    <Line {...config} />
                </Card>
            </div>
        );
    }

    generateUserUploadInfo = () => {
        const { userFileUploadCount, systemFileCount } =
            this.state.fileUploadInfoData;
        const { fileUploadInfoLoading } = this.state;
        return (
            <div style={{ textAlign: "center" }}>
                <Row>
                    <Col>
                        <Card loading={fileUploadInfoLoading}>
                            <Statistic
                                title="今日已上传的文件"
                                value={userFileUploadCount}
                                valueStyle={{ color: "#87CEFA" }}
                            />
                            <Statistic
                                title="系统上传的总文件"
                                value={systemFileCount}
                                valueStyle={{ color: "#1E90FF" }}
                            />
                        </Card>
                    </Col>
                </Row>
            </div>
        );
    };

    generateComponent = () => {
        return (
            <div>
                <Row gutter={12}>
                    <Col span={24}>{this.generateUserUploadInfo()}</Col>
                    <Divider plain>数据图表</Divider>
                    <Col span={24}>{this.generateWeekUploadInfo()}</Col>
                </Row>
            </div>
        );
    };

    content = () => {
        return { navPath: "概览", content: this.generateComponent() };
    };

    render() {
        return (
            <div>
                <Main history={this.props.history} view={this.content()} />
            </div>
        );
    }
}
