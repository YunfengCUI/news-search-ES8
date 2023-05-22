import axios from 'axios'

export function request (config) {
  // 1.创建axios的实例
  const instance = axios.create({
    // 设置基础的url配置项，这样接口处的url前面就不用写url:'http://127.0.0.1:8000/api/home'，直接写成 url:'/api/home', 就可以了
    baseURL: 'http://localhost:8080/',
    // 设置请求超时时间
    timeout: 5000
  })
  // 2.axios的拦截器，用不到的可以忽略这节
  // 2.1.请求拦截的作用
  instance.interceptors.request.use(config => {
    return config
  }, err => {
    console.log('请求拦截err: ' + err)
  })

  // 2.2.响应拦截
  instance.interceptors.response.use(res => {
    return res.data
  }, err => {
    console.log('响应拦截err: ' + err)
  })

  // 3.发送真正的网络请求
  return instance(config)
}
