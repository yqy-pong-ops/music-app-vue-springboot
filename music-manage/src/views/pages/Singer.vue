<!--
 * @Author: Axiuxiu
 * @Date: 2022-06-08 21:15:01
 * @LastEditTime: 2022-06-16 16:48:01
 * @Description: 
 * @Todo: 实现分页+搜索功能
-->
<template>
    <div class="table singer-page">
        <div class="container">
            <!-- 歌手名筛选输入框 -->
            <el-input
                v-model="input"
                placeholder="根据歌手名筛选"
                size="mini"
                clearable
                @keyup.enter.native="handleQuery"
            ></el-input>

            <el-button type="primary" size="mini" @click="handleAdd">添加歌手</el-button>
        </div>

        <!-- 显示的数据表格 -->
        <el-table :data="tableData" border size="mini" style="width: 100%" height="420px">
            <el-table-column type="selection" width="40"></el-table-column>

            <el-table-column label="歌手图片" width="110" align="center">
                <template v-slot="scope">
                    <img :src="scope.row.pic" alt class="singer-pic" />
                    <el-upload
                        :action="getUploadUrl(scope.row.id)"
                        :before-upload="beforeUpload"
                        :on-success="(resp) => handleUploadSuccess(resp, scope.row.id)"
                        :show-file-list="false"
                    >
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column label="歌手" prop="name" width="120" align="center"></el-table-column>
            <el-table-column label="性别" width="100" align="center">
                <template v-slot="scope">{{getGenderStr(scope.row.gender)}}</template>
            </el-table-column>
            <el-table-column label="生日" prop="birth" width="120" align="center"></el-table-column>
            <el-table-column label="地区" prop="location" width="100" align="center"></el-table-column>
            <el-table-column label="简介">
                <template v-slot="scope">
                    <p class="intro">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="200">
                <template v-slot="scope">
                    <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="danger" size="mini" @click="handleDel(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
            :page-size="pagin.pageSize"
            :current-page.sync="pagin.curPage"
            :pager-count="pagin.pagerCnt"
            :total="queryData.length"
            @current-change="setTable"
            background
            layout="total, prev, pager, next, jumper"
        ></el-pagination>

        <!-- 弹出框 -->
        <el-dialog
            :title="dialog.opt?'编辑歌手':'添加歌手'"
            :visible.sync="dialog.vis"
            width="400px"
            @close="diaClose"
            center
        >
            <!-- 歌手表格：name, gender, location, introduction, birth -->
            <el-form :model="dForm" ref="dForm" :rules="dRules" label-width="80px" :inline="false">
                <el-form-item label="歌手名" size="mini" prop="name">
                    <el-input v-model="dForm.name"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <el-radio-group v-model="dForm.gender">
                        <el-radio
                            v-for="item in gOpts"
                            :key="item.label"
                            :label="item.label"
                        >{{item.title}}</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="生日" size="mini">
                    <el-date-picker
                        v-model="dForm.birth"
                        type="date"
                        size="mini"
                        placeholder="选择日期时间"
                    ></el-date-picker>
                </el-form-item>

                <el-form-item label="地区" size="mini">
                    <el-select v-model="dForm.location" clearable filterable>
                        <el-option
                            v-for="(item, index) in locOpts"
                            :key="index"
                            :label="item"
                            :value="item"
                        ></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="简介" size="mini">
                    <el-input v-model="dForm.introduction" size="mini" type="textarea" clearable></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="onSubmit">{{ dialog.opt === 0?'添加':'修改'}}</el-button>
                    <el-button @click="diaClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import { getAllSinger, addSinger, updateSinger, deleteSinger } from "@/api";
import { CODE, DATA } from "@/global";
const dayjs = require("dayjs");

export default {
    name: "Singer",
    data() {
        return {
            // 弹出框
            dialog: {
                title: "",
                vis: false,
                opt: 0, // 0 -> add, 1 -> edit
            },
            dForm: {
                id: 0,
                name: "",
                gender: 0,
                location: "",
                introduction: "",
                birth: "",
            },
            dRules: {
                name: [
                    {
                        required: true,
                        message: "请输入歌手名",
                        trigger: "blur",
                    },
                ],
            },

            // 地区选项
            locOpts: ["大陆", "港台", "海外"],

            // 性别选项
            gOpts: [
                {
                    label: 0,
                    title: "女",
                },
                {
                    label: 1,
                    title: "男",
                },
                {
                    label: 2,
                    title: "组合",
                },
                {
                    label: 3,
                    title: "不明",
                },
            ],

            // 分页+查找
            input: "",
            tableData: [],
            allData: [],
            queryData: [],
            pagin: {
                curPage: 1,
                pagerCnt: 5,
                pageSize: 5,
            },
        };
    },
    methods: {
        initDiaForm() {
            this.dForm = {
                id: 0,
                name: "",
                gender: 0,
                location: "",
                introduction: "",
                birth: "",
            };
        },

        // 增改弹出框
        handleAdd() {
            this.dialog.vis = true;
            this.dialog.opt = 0;
            // this.$confirm("确定");
        },
        diaClose() {
            this.dialog.vis = false;
            this.initDiaForm();
        },
        onSubmit() {
            this.$refs["dForm"].validate((valid) => {
                if (valid) {
                    // 处理日期参数
                    let d = this.dForm.birth;
                    this.dForm.birth = dayjs(d).format("YYYY-MM-DD");

                    if (this.dialog.opt === 0)
                        addSinger(this.dForm)
                            .then((res) => {
                                // 更新表格
                                let newItem = res[DATA];
                                this.allData.push(newItem);
                                this.handleQuery();

                                this.diaClose();
                            })
                            .catch((err) => {
                                console.log(err);
                            });
                    else
                        updateSinger(this.dForm)
                            .then((res) => {
                                // 更新表格
                                let singer = res[DATA];
                                this.allData.forEach((item) => {
                                    if (item.id !== singer.id) return;

                                    for (let key in item)
                                        item[key] =
                                            singer[key] !== null
                                                ? singer[key]
                                                : item[key];
                                });
                                this.handleQuery();

                                this.diaClose();
                            })
                            .catch((err) => {
                                console.log(err);
                            });
                } else {
                    console.log("form error");
                    return false;
                }
            });
        },

        // 获取后台数据
        getData() {
            getAllSinger()
                .then((data) => {
                    this.allData = data[DATA];
                    this.handleQuery();
                })
                .catch((err) => {
                    console.log(err);
                });
        },

        // 头像上传
        getUploadUrl(id) {
            return "/manage/singer/updateSingerPic" + "?id=" + id;
        },
        // 上传图片之前的校验
        beforeUpload(file) {
            const isPhoto =
                file.type == "image/jpeg" || file.type == "image/png";
            if (!isPhoto) {
                this.$message({
                    showClose: true,
                    type: "error",
                    message: "上传头像格式只能是jpg或png格式",
                });
                return false;
            }
            const isLt5M = file.size / 1024 / 1024 < 5;
            if (!isLt5M) {
                this.$message({
                    showClose: true,
                    type: "error",
                    message: "上传头像图片不得超过5M",
                });
                return false;
            }
            return true;
        },
        handleUploadSuccess(res, id) {
            if (res[CODE] == 200) {
                // 更新表格
                let pic = res[DATA];
                this.allData.forEach((item) => {
                    if (item.id !== id) return;
                    item.pic = pic;
                });
                this.handleQuery();

                this.$message({
                    showClose: true,
                    type: "success",
                    message: "头像更新成功",
                });
            } else {
                this.$message({
                    showClose: true,
                    type: "success",
                    message: "头像更新失败",
                });
            }
        },

        // 分页+查找
        setTable(page) {
            this.tableData = this.queryData.slice(
                (page - 1) * this.pagin.pageSize,
                page * this.pagin.pageSize
            );
        },
        getQueryData() {
            this.queryData = this.allData.filter((item) => {
                return item.name.indexOf(this.input) !== -1; // 可以直接使用includes
            });
        },
        handleQuery() {
            // allData -> tableData
            this.getQueryData();
            let total = this.queryData.length;
            if ((this.pagin.curPage - 1) * this.pagin.pageSize >= total)
                this.pagin.curPage =
                    total === 0
                        ? 1
                        : Math.floor((total - 1) / this.pagin.pageSize) + 1;
            console.log(this.pagin.curPage);
            this.setTable(this.pagin.curPage);
        },

        // 表格编辑删除操作
        handleEdit(row) {
            for (let key in this.dForm) {
                this.dForm[key] = row[key];
            }
            this.dialog.vis = true;
            this.dialog.opt = 1;
        },
        handleDel(id) {
            // 发送
            // console.log(row);
            this.$confirm("确认删除？")
                .then(() => {
                    deleteSinger({ id })
                        .then(() => {
                            // 更新表格
                            this.allData = this.allData.filter((item) => {
                                return item.id !== id;
                            });
                            this.handleQuery();
                        })
                        .catch((err) => {
                            console.log(err);
                        });
                })
                .catch((err) => {
                    console.log(err);
                });
        },
    },
    mounted() {
        this.getData();
    },
};
</script>

<style scoped>
.singer-page {
    padding-bottom: 0;
}

.singer-page .container > * {
    display: inline-block;
}
.singer-page .container .el-input {
    width: 220px;
}
.singer-page .container .el-button {
    margin-left: 10px;
}

.singer-pic {
    width: 100%;
    height: 80px;
    border-radius: 5px;
    margin-bottom: 5px;
}

p.intro {
    width: 100%;
    height: 100px;
    overflow: auto;
}

.el-pagination {
    text-align: right;
}
</style>