/*
 * @Author: Axiuxiu
 * @Date: 2022-06-17 09:36:14
 * @LastEditTime: 2022-06-18 22:43:54
 * @Description: mixins文件
 * @Todo: 
 */
import { CODE, DATA } from '@/global';

export const DataTableMixin = {
    methods: {
        getGenderStr(g) {
            let str;
            switch (g) {
                case 0:
                    str = '女';
                    break;
                case 1:
                    str = '男';
                    break;
                case 2:
                    str = '组合';
                    break;
                case 3:
                    str = '不明';
                    break;
                default:
                    break;
            }
            return str;
        },

        // 头像上传
        beforeUpload(file, typeList, fileSize) {
            // console.log(file);
            const isType = typeList.includes(file.type);
            if (!isType) {
                this.$message({
                    showClose: true,
                    type: "error",
                    message: "上传文件格式错误",
                });
                return false;
            }
            const fitSize = file.size / 1024 / 1024 < fileSize;
            if (!fitSize) {
                this.$message({
                    showClose: true,
                    type: "error",
                    message: "上传文件大小不符合要求",
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
                console.log(res);
                this.$message({
                    showClose: true,
                    type: "error",
                    message: "头像更新失败",
                });
            }
        },
        onUploadExceed() {
            this.$message({
                showClose: true,
                type: 'warning',
                message: '所选文件超出个数限制',
            });
        }
    },
}