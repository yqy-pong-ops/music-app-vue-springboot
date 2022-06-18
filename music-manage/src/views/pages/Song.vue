<!--
 * @Author: Axiuxiu
 * @Date: 2022-06-08 21:15:01
 * @LastEditTime: 2022-06-18 22:59:13
 * @Description: 
 * @Todo: 实现分页+搜索功能
-->
<template>
    <div class="table song-page">
        <div class="title">{{singerName}}</div>
        <div class="container">
            <!-- 歌手名筛选输入框 -->
            <el-input
                v-model="input"
                prefix-icon="el-icon-search"
                placeholder="根据歌曲名筛选"
                size="mini"
                clearable
                @keyup.enter.native="handleQuery"
            ></el-input>

            <el-button type="primary" size="mini" @click="handleAdd">添加歌曲</el-button>
            <el-button type="danger" size="mini" @click="delBatch">批量删除</el-button>
        </div>

        <!-- 显示的数据表格 -->
        <el-table
            :data="tableData"
            ref="table"
            border
            size="mini"
            style="width: 100%"
            height="380px"
            @selection-change="handleSelect"
        >
            <el-table-column type="selection" width="40"></el-table-column>

            <el-table-column label="歌曲图片" width="110" align="center">
                <template v-slot="scope">
                    <img :src="scope.row.pic" alt class="song-pic" />
                    <el-upload
                        :action="getUploadUrl(scope.row.id)"
                        :before-upload="(file)=>beforeUpload(file, ['image/png','image/jpeg', 'image/webp'], 10)"
                        :on-success="(resp) => handleUploadSuccess(resp, scope.row.id)"
                        :show-file-list="false"
                    >
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column label="歌曲" prop="name" width="120" align="center"></el-table-column>

            <el-table-column label="专辑" prop="introduction" width="100" align="center"></el-table-column>
            <el-table-column label="歌词" align="center">
                <template v-slot="scope">
                    <p class="intro">{{scope.row.lyrics}}</p>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime" width="200" align="center"></el-table-column>
            <el-table-column label="更新时间" prop="updateTime" width="200" align="center"></el-table-column>

            <el-table-column label="操作" align="center" width="160">
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
            :title="dialog.opt?'编辑歌曲':'添加歌曲'"
            :visible.sync="dialog.vis"
            width="400px"
            @close="diaClose"
            center
        >
            <!-- 歌手表格：name, gender, location, introduction, birth -->
            <el-form :model="dForm" ref="dForm" :rules="dRules" label-width="80px" :inline="false">
                <el-form-item label="歌曲名" size="mini" prop="name">
                    <el-input v-model.trim="dForm.name"></el-input>
                </el-form-item>

                <el-form-item label="专辑" size="mini" prop="introduction">
                    <el-input v-model="dForm.introduction"></el-input>
                </el-form-item>

                <el-form-item label="歌词" size="mini">
                    <el-input v-model="dForm.lyrics" type="textarea" clearable></el-input>
                </el-form-item>

                <el-form-item label="作品上传" size="mini" prop="mp3">
                    <el-upload
                        :auto-upload="false"
                        :on-change="getMp3"
                        action
                        multiple
                        :limit="1"
                        :on-exceed="onUploadExceed"
                    >
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传mp3文件，且不超过20MB</div>
                    </el-upload>
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
import {
    deleteSinger,
    delBatchSingers,
    addSong,
    getSongsBySingerId,
} from "@/api";
import { DATA } from "@/global";
import { DataTableMixin } from "@/mixins/index";

export default {
    name: "Singer",
    mixins: [DataTableMixin],
    data() {
        const mp3Check = (rule, value, callback) => {
            if (this.dForm.mp3 === "" || this.dForm.mp3 === null)
                return callback(new Error("请选择文件"));
            callback();
        };

        return {
            // 弹出框
            dialog: {
                vis: false,
                opt: 0, // 0 -> add, 1 -> edit
            },
            dForm: {
                id: 0,
                singerId: 0,
                name: "",
                lyrics: "",
                introduction: "",
                mp3: "",
            },
            dRules: {
                name: [
                    {
                        required: true,
                        message: "请输歌曲名",
                        trigger: "blur",
                    },
                ],
                mp3: [
                    {
                        validator: mp3Check,
                        trigger: "blur",
                    },
                ],
            },
            // mp3文件上传
            mp3List: [],

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

            // 多选
            multiSelect: [],
        };
    },
    methods: {
        initDiaForm() {
            this.dForm = {
                id: 0,
                singerId: 0,
                name: "",
                lyrics: "",
                introduction: "",
                mp3: "",
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
                    if (this.dialog.opt === 0) {
                        // 数据转换
                        let form = new FormData();
                        console.log(this.dForm.mp3);
                        this.dForm.singerId = this.$route.query.id;

                        for (let key in this.dForm)
                            form.append(key, this.dForm[key]);

                        // 数据转换
                        addSong(form)
                            .then((data) => {
                                console.log(data);
                                // 更新表格
                                const newItem = data[DATA];
                                this.allData.push(newItem);
                                this.handleQuery();
                                this.diaClose();
                            })
                            .catch((err) => {
                                console.log(err);
                            });
                    } else {
                        // 修改
                        console.log("update song");
                    }
                } else {
                    console.log("form error");
                    return false;
                }
            });
        },

        // mp3文件上传
        getMp3(file, fileList) {
            if (file.status !== "ready") return;
            const typeList = ["audio/mpeg", "audio/wav"];
            if (!typeList.includes(file.raw.type)) {
                this.$message({
                    showClose: true,
                    type: "warning",
                    message: "文件格式不正确",
                });
                fileList.pop();
                return;
            }
            this.dForm.mp3 = file.raw;
        },

        // 获取后台数据
        getData() {
            getSongsBySingerId(this.singerId)
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
            return "/manage/song/updatePic" + "?id=" + id;
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
                return item.name.includes(this.input); // 可以直接使用includes
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
        delRow(id) {
            /**
             * 删除并更新表格
             */
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
        },
        handleDel(id) {
            // 发送
            // console.log(row);
            this.$confirm("确认删除？")
                .then(() => {
                    this.delRow(id);
                })
                .catch((err) => {
                    console.log(err);
                });
        },

        // 多行选取和删除操作
        handleSelect(selection) {
            this.multiSelect = selection;
        },
        delBatch() {
            delBatchSingers(this.multiSelect.map((item) => item.id))
                .then(() => {
                    // 更新表格和属性
                    console.log(this.multiSelect);
                    console.log(this.allData);
                    this.allData = this.allData.filter((item) => {
                        for (let j of this.multiSelect) {
                            if (item.id !== j.id) continue;
                            return false;
                        }
                        return true;
                    });
                    console.log(this.allData);

                    this.handleQuery();
                    this.$refs["table"].clearSelection();
                })
                .catch((err) => {
                    console.log(err.data);
                });
        },
        handleDelBatch() {
            this.$confirm("确认删除所选项吗？")
                .then(() => {
                    this.delBatch();
                })
                .catch((err) => {
                    console.log(err);
                });
        },

        // 跳转操作
        songEdit(id, name) {
            this.$router.push({
                name: "Song",
                query: {
                    id,
                    name,
                },
            });
        },
    },
    computed: {
        singerName() {
            return this.$route.query.name;
        },
        singerId() {
            return this.$route.query.id;
        },
    },
    mounted() {
        this.getData();
    },
};
</script>

<style scoped>
.title {
    font-size: 22px;
    font-weight: bold;
    line-height: 40px;
    margin-bottom: 10px;
}

.song-page {
    padding-bottom: 0;
    padding-top: 0;
}

.song-page .container > * {
    display: inline-block;
}
.song-page .container .el-input {
    width: 220px;
}
.song-page .container .el-button {
    margin-left: 10px;
}

.song-pic {
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