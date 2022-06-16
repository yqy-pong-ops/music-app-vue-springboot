import Vue from 'vue'
import App from './App.vue'
// 引入router
import router from './router';
// 引入store
import store from './store';
// 引入element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// 引入v-charts
import VCharts from 'v-charts'
// 引入全局样式
import './assets/css/main.css'
import 'babel-polyfill'
// 引入自定义axios
import { getGenderStr } from '@/utils';

Vue.use(ElementUI);
Vue.use(VCharts);

Vue.config.productionTip = false;

Vue.prototype.jumpTo = function (ruoterName) {
  this.$router.push({
    name: ruoterName,
  })
}
Vue.prototype.getGenderStr = getGenderStr;

new Vue({
  router,
  store,
  render: h => h(App),
  // 安装全局事件总线
  beforeCreate() {
    Vue.prototype.$bus = this;
  },
}).$mount('#app')
