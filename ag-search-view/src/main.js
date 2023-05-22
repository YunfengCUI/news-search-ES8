import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './assets/css/common.css'
//全局引入echarts
import * as echarts from 'echarts';
//需要挂载到Vue原型上
import My_Global from '@/store/global.variable'
Vue.prototype.$echarts = echarts
axios.defaults.baseURL = '/api'
Vue.prototype.axios = axios
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.prototype.My_Global = My_Global
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
