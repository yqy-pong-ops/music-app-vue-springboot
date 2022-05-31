import axios from 'axios';
import { Message, Loading } from 'element-ui';
// import router from './router';
import _ from 'loadsh';

var loadInst = null;
var loadInstCnt = 0;

function startLoading() {
    if (!loadInst) {
        loadInst = Loading.service({
            lock: true,
            text: '拼命加载中...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        })
    }
    loadInstCnt++;
}

function endLoading() {
    loadInstCnt--;
    if (loadInstCnt == 0) {
        toEndLoading();
    }
}

const toEndLoading = _.debounce(() => {
    loadInst.close();
    loadInst = null;
}, 300)

axios.interceptors.request.use(config => {
    // Do something before request is sent
    startLoading();

    return config;
}, error => {
    // Do something with request error
    return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    // Do something before response is sent
    endLoading();

    return response;
}, error => {
    // Do something with response error
    endLoading();

    // const status = error.response.status;

    Message.error({
        showClose: true,
        message: error.response.data,
    })
    // 处理对应status

    return Promise.reject(error);
});

export default axios;