import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        // admin基本信息
        admin: null,
    },
    mutations: {
        // 设置admin对象，
        setAdmin(state, admin) {
            state.admin = admin;
        },
    },
    actions: {
        // 参数为context, context.commit() 调用mutations
    }
})

export default store;

