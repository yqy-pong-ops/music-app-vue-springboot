/*
 * @Author: Axiuxiu
 * @Date: 2022-06-12 17:02:03
 * @LastEditTime: 2022-06-14 17:29:37
 * @Description: 工具函数
 * @Todo: 
 */

// export const getFullUrl = (path) => this.$store.state.HOST + path;

// 性别显示函数
export const getGenderStr = (g) => {
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
}