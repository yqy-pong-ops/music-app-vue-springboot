<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="handleAdd">添加歌手</el-button>
                <!-- <el-button type="success" size="default" @click="getInfo">查询</el-button> -->
            </div>
        </div>

        <!-- 显示的数据表格 -->
        <el-table :data="tableData" border size="mini" style="width: 100%" height="500px">
            <el-table-column label="歌手图片" width="110" align="center">
                <template v-slot="scope">
                    <img :src="scope.row.pic" alt class="singer-pic" />
                    <el-upload
                        :action="getUploadUrl(scope.row.id)"
                        :before-upload="beforeUpload"
                        :on-success="handleUploadSuccess"
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
        </el-table>

        <!-- 弹出框 -->
        <el-dialog
            :title="dialog.title"
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
                    <el-button type="primary" @click="onSubmit">立即创建</el-button>
                    <el-button @click="diaClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import { getAllSinger, addSinger } from "@/api";
import { CODE, DATA } from '@/global';
const dayjs = require("dayjs");

export default {
    name: "Singer",
    data() {
        return {
            dialog: {
                title: "",
                vis: false,
            },
            dForm: {
                name: "",
                gender: 0,
                location: "",
                introduction: "",
                birth: "",
            },
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
            dRules: {
                name: [
                    {
                        required: true,
                        message: "请输入歌手名",
                        trigger: "blur",
                    },
                ],
            },
            locOpts: ["大陆", "港台", "海外"],

            tableData: [],
        };
    },
    methods: {
        handleAdd() {
            this.dialog.vis = true;
            this.dialog.title = "添加歌手";
            // this.$confirm("确定");
        },
        diaClose() {
            this.dialog.vis = false;

            this.$refs["dForm"].resetFields();
        },
        onSubmit() {
            this.$refs["dForm"].validate((valid) => {
                if (valid) {
                    // 处理日期参数
                    let d = this.dForm.birth;
                    this.dForm.birth = dayjs(d).format("YYYY-MM-DD");
                    // 发送数据到后端
                    addSinger(this.dForm)
                        .then(() => {
                            // console.log(data);
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
        getData() {
            getAllSinger()
                .then((data) => {
                    this.tableData = data[DATA];
                })
                .catch((err) => {
                    console.log(err);
                });
        },
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
        handleUploadSuccess(res) {
            if (res[CODE] == 200) {
                this.getData();
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
    },
    mounted() {
        this.getData();
    },
};
</script>

<style scoped>
/* .handle-box {
    margin-bottom: 20px;
} */

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
</style>