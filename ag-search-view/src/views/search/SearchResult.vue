<template>
  <div>
    <!--    搜索和显示-->
    <div class="conter">
      <!-- 资料清单 -->
      <div >
        <div v-if="showFile" style="margin-bottom: 10px; margin-left: 20px">共检索到{{ tableData.length }}个文件</div>
        <div v-if="showFile" class="file-top-style">
          <div class="file-style">
            <table>
              <tr v-for="(item,index) in tableData" :key="index" v-show="tableData.length!==0" style="width: 100%;display: inline-block">
                <td style="padding: 0 5%;width: 90%;display: inline-block;">
                  <div style="display:flex;position: relative">
                      <el-button type="text" class="item-title" @click="handleRouterBlank(item)">{{ item.title }}</el-button>
                  </div>
                  <p class="item-content" v-html="item.content" />
                  <div class="item-info">
                      {{ item.createdBy }}&nbsp;&nbsp;{{ item.publishTime }} &nbsp;&nbsp;
                    <a :href=item.source target="_blank">源网页</a>
                  </div>
                </td>
              </tr>
              <div v-show="tableData.length===0" class="search-nothing">
                <div class="flex-column">
                    <pre>
                      没有找到和您查询内容相符的资料，建议：
                    </pre>
                  <pre>
                      请检查输入字词优速错误。
                      请尝试其他常见查询词
                      请尝试其他查询条件
                    </pre>
                </div>
              </div>
            </table>
          </div>

          <el-pagination
            class="file-page-style"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="list_query.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="tableData.length"
          />
        </div>
      </div>
      <!-- 分页 end-->

    </div>
    <!--搜索和显示完毕-->
    <router-view></router-view>
  </div>

</template>

<script>
import { searchResult, resultById } from '@/api/search'
export default {
  name: 'SearchResult',
  data () {
    return {
      // activeIndex: '1',
      EsType: '',
      list_query: {
        pageNo: 1,
        pageSize: 10,
        dataTitle: '' // 筛选
      },
      checkList: [],
      // 分页
      currentPage: 1,
      // 返回数据书作序
      tableData: [],
      showFile: true,
      loading: false
    }
  },
  created() {
  },
  methods: {
    // 搜索标签
    handleSearch (queryParams) {
      // this.EsType = key
      // 像后端发送搜索
      const params = {
        content: queryParams.keyword,
        source: queryParams.source,
        fromDate: queryParams.fromDate,
        endDate: queryParams.endDate,
        formNo: this.list_query.pageNo,
        formSize: this.list_query.pageSize
      }
      searchResult(params).then((resp) => {
        this.tableData = resp.data
      })
    },
    open (obj) {
      this.$alert(obj.HtmlCode, obj.Title, {
        dangerouslyUseHTMLString: true
      })
    },
    // getById (obj) {
    //   getNewsById(obj.Uuid).then((resp) => {
    //   })
    // },
    // 打开新页面
    handleRouterBlank (item) {
      this.$router.push({name:'watch',query:{title:item.title,uuid: item.uuid}})
    }
  }
}
</script>

<style scoped>
.dlag_conter >>> .el-input.is-disabled .el-input__inner {
  color: #606266 !important;
  background: #f5f7fa !important;
  opacity: 0.8;
}
/* 文件检索 */
.file-retrieval{
  padding-bottom: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
}
/* 文件检索输入框 */
.file-search{
  padding:6px 10px ;
  width: 350px;
  background-color: #ebeef1;
  margin: 0px 10px;
}
.checkbox{
  padding-bottom: 10px;
  display: flex ;
  justify-content: center;
  background-color: #fff;
}
.title-left{
  width: 105px;
}

.table-top{
  margin: 15px;
  padding: 15px ;
  background-color: #fff;
}
.file-top-style{
  padding: 10px 20px;
  width: 70%;
}
.file-style{
  background-color: #fff;
  height: 100%;
}
.file-page-style{
  background-color: #fff;
  float: right;
}
.file-inside{
  position: absolute;
  width: calc(100% - 5%);
  padding-bottom: 10px;
  border-bottom: 1px solid rgb(245,245,245);
}

.fileList >>> .el-dialog {
  min-width: 900px;
}
.dlag_conter {
  overflow-y: auto;
}

.min_height >>> .el-table {
  -ms-overflow-style: scrollbar !important;
}
.min_height {
  padding-top: 10px;
}
.min_height >>> .el-table__header {
  margin-top: 0 !important;
}

>>> .el-dialog--center .el-dialog__body {
  padding: 0 !important;
}
>>> .foot .el-button {
  font-weight: normal;
}
>>> .el-dialog--center .el-dialog__body .el-form-item__label {
  font-size: 14px;
}
.conter {
  box-sizing: border-box;
}
.conter >>>.el-tabs__nav-scroll{
  padding-left: 20px;
}
.dlag_conter {
  text-align: center;
}
.dlag_conter >>> .el-form-item--medium,
.dlag_conter >>> .el-form-item__content {
  flex: inherit !important;
}
.dlag_conter >>> .el-input__inner::placeholder {
  color: #c1c1c1 !important;
}
.titleMes {
  padding: 10px;
  box-sizing: border-box;
}
/* search */
.projectTab >>> .el-button,
.search >>> .el-input__inner {
  border-radius: 0px;
}
.search {
  display: flex;
  align-items: center;
  position: relative;
  margin-bottom: 25px;

}
.search >>> .el-input__inner {
  width: 220px !important;
}

.search >>> .search_icon {

}
.search >>> .el-button {
  margin-left: -8px;
  /* background: #1371cc !important; */
}

/* search :end */

/* 分页 */
.page {
  width: 100%;
  padding: 20px 10px;
  display: flex;
  justify-content: flex-end;
}

/* 分页 end */

/* 弹窗 */
.dlag_conter {
  padding: 20px;
  box-sizing: border-box;
}

.dlag_conter >>> .el-textarea .el-textarea__inner {
  resize: none;
}
.dlag_conter >>> .el-form-item {
  margin-bottom: 20px !important;
  display: flex;
  justify-content: center;
}
.dlag_conter >>> .el-form-item__content {
  margin-left: 0 !important;
}
.dlag_conter >>> .el-input,
.dlag_conter >>> .el-textarea {
  width: 240px;
}

/* 附件管理 */
.list_conter {
  display: flex;
}
.left_data_list {
  min-width: 300px;
  padding: 10px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  box-sizing: border-box;
}
.el-tree {
  background: transparent !important;
}

.el-tree-node__expand-icon.is-leaf {
  background: none;
}
.el-icon-folder-opened {
  color: #efae4a !important;
}
.content-style img {
  width: 15px;
  margin-right: 5px;
}
/* 更换图标库 */
/* .pageTree >>> [class*=" el-icon-"],
[class^="el-icon-"] {
  font-family: "FontAwesome" !important;
} */
/*  动画取消 */
/* .pageTree >>> .el-tree-node__expand-icon.expanded {
  -webkit-transform: rotate(0deg);
  transform: rotate(0deg);
} */
/*  收起 */
.pageTree >>> .el-icon-caret-right:before {
  content: '+';
  font-weight: bold;
  font-size: 16px;
}
/* 展开 */
.pageTree >>> .el-tree-node__expand-icon.expanded.el-icon-caret-right:before {
  content: '-';
  font-size: 16px;
  font-weight: bold;
}

/* 资料树默认选择样式 */
.pageTree >>> .is-current {
  background: #f4fafe;
}
.pageTree >>> .is-current span {
  font-weight: 800;
}

.pageTree >>> .el-tree-node__expand-icon.expanded {
  transform: rotate(0deg) !important;
}
.content-style span {
  box-sizing: border-box;
  display: flex;
  align-items: center;
}
.right_table {
  flex: 1;
  padding: 0 10px 0 20px;
  box-sizing: border-box;
}
.btn_type {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}
.btn_type >>> .el-col {
  margin-left: 10px;
}

.fileList >>> .el-dialog {
  min-width: 850px;
}

.product-buyer-name {
  padding: 0;
  margin: 0;
  max-width: 30%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.search-nothing {
  width: 100%;
  padding: 10%;
  display: flex;
  justify-content: center;
  align-items: center
}
.text-button{
  padding: 0;
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  text-align: left;
  text-decoration: none;
  font-size: 16px;
  cursor: pointer;
}
.item-title {
  font-size: 16px;
  color: #000;
  padding: 15px 10px 5px 10px !important;
}
.item-content {
  margin-block-start: 5px !important;
  margin-block-end: 2px !important;
  font-size: 13px !important;
}

.item-info{
  position: relative;
  font-size: 12px;
  color: #666;
}
</style>
