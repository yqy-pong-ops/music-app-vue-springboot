import Vue from 'vue'
import App from './App.vue'
import router from './router';
// 引入element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// 引入v-charts
import VCharts from 'v-charts'
// 引入全局样式
import './assets/css/main.css'
import 'babel-polyfill'
// 引入自定义axios
import axios from './http';

Vue.use(ElementUI);
Vue.use(VCharts);

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;

Vue.prototype.$jumpTo = function (ruoterName) {
  this.$router.push({
    name: ruoterName,
  })
}

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
