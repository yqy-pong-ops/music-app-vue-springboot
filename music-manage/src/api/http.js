/*
 * @Author: Axiuxiu
 * @Date: 2022-06-11 16:52:10
 * @LastEditTime: 2022-06-16 11:00:00
 * @Description: 封装axios
 * @Todo: 
 */
import { ADMINTOKEN } from '@/global';
import axios from 'axios';
import { Message, Loading } from 'element-ui';
import _ from 'lodash';
import router from '../router';

var loadInst = null;
var loadCnt = 0;

// 打开加载
function openLoading() {
    if (loadCnt == 0)
        loadInst = Loading.service({
            lock: true,
            text: '拼命加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        });
    loadCnt++;
}

const toEndLoading = _.debounce(() => {
    loadInst.close();
    loadInst = null;
}, 300)

// 关闭加载
function closeLoading() {
    loadCnt--;
    if (loadCnt == 0)
        toEndLoading();
}

axios.interceptors.request.use(config => {
    // Do something before request is sent
    openLoading();

    const token = localStorage.getItem(ADMINTOKEN);
    // 带上验证头
    if (token != null)
        config.headers = {
            'Authorization': token,
        }

    return config;
}, error => {
    // Do something with request error
    return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    // Do something before response is sent
    closeLoading();
    // 判断自定义状态码
    const data = response.data;
    if (data.code != 200) {
        if (data.code != 500)
            Message.error(data.msg);
        throw new Error(response);
    }
    // 显示提示信息
    if (data.msg)
        Message.success(data.msg);
    return response;
}, error => {
    // Do something with response error
    closeLoading();
    switch (error.response.status) {
        case 401:
            Message.error('您尚未登录');
            router.replace({
                name: 'Login',
                query: {
                    // 当前完整url:包括查询参数等
                    next: router.currentRoute.fullPath,
                    // 调回来的时候也要用replace
                }
            })
            break;

        default:
            break;
    }

    return Promise.reject(error);
});

export function get(url, params) {
    return new Promise((resolve, reject) => {
        axios.get(url, { params })
            .then(res => {
                resolve(res.data);
            })
            .catch(err => {
                reject(err);
            })
    })
}

export function post(url, data) {
    return new Promise((resolve, reject) => {
        axios.post(url, data)
            .then(res => {
                resolve(res.data);
            })
            .catch(err => {
                reject(err);
            })
    })
}