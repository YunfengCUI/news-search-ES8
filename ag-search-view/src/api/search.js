import axios from 'axios'
import da from "element-ui/src/locale/lang/da";


/**
 * 查询新闻
 ElasticSearch
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 */
export function searchResult (data) {
  return axios.post('/esdocs/search', data)
}
/**
 * 查询单条新闻
 */
export  function getByUuid(uuid) {
  return axios.post('/esdocs/get/uuid',uuid)
}
/**
 * 后台查新闻
 * @param data
 *
 */
export function resultById (data) {
  return axios.post('/newsAbnp/getById', data)
}
/**
 *
 * @param data
 * @returns
 * @constructor
 * // 登录验证
 */
export function Login(data) {
  return axios.post('/sys/login',data)
}
/**
 * 登出
 * @returns {Promise<AxiosResponse<any>>}
 */
export function logout(data) {
  return axios.post('/sys/logout',data)
}
/**
 * 根据uuid 查询User
 * @param data
 * @returns {string}
 */
export function selectByUuid(data){
  const uuid = data
  return axios.get(`/user/selectByUuid/${uuid}`,uuid)
}
/**
 * 添加用户
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function AddUser(data){
  return axios.post('/user/add',data)
}
/**
 * 分页查询用户
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 */
export function queryPageUser(data) {
  return axios.get('/user/selectPage',
    {
      params:{
        pageNo : data.formNo ,
        pageSize : data.formSize
      }
    }
    )
}

/**
 * 编辑用户信息
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function EditUserInfo(data) {
  return axios.post('/user/updateByUuId',data)
}

/**
 * 删除用户
 * @param uuid
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function DelUser(uuid) {
  return axios.post(`/user/delete/${uuid}`,uuid)
}
/**
 * 增添搜索记录
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function AddSearchHistory(data){
  return axios.post('/wordHostory/add',data)
}
/**
 * 热门搜索词汇 热词
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function TopHotWord() {
  return axios.post('/HotWord/getAll')
}
/**
 * 添加热词
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function addHotWord(data) {
  return axios.post('/HotWord/add',data)
}
/**
 * 添加评论
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 */
export function AddComment(data) {
  return axios.post('/newsComment/add',data)
}
/**
 * 获取评论
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function LastelyComment(data) {
  return axios.post('/newsComment/lastely',data)
}
// 删除评论
export function DelComment(data) {
  return axios.post('/newsComment/delete',data)
}
//我的 评论
export function getMyClusm(data) {
  return axios.post('/newsComment/myComment',data)
}
//管理员管理评论
export function adminComment(data) {
  return axios.post('/newsComment/allComment',data)
}
//添加收藏
export function addCollection(data) {
  return axios.post('/collection/add',data)
}
//查询收藏
export function getCollection_Page(data) {
  return axios.post('/collection/selectByPage',data)
}
//删除收藏
export function removeCollection(data) {
  return axios.post('/collection/remove',data)
}
//检测收藏状态
export function checkCollectionStatus(data) {
  return axios.post('/collection/news_status',data)
}
