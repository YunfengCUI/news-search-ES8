<template>
  <div>
    <el-row>
    <el-table
      :data="tableData.filter(data => !search || data.arricleTitle.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%;background: none"
      :header-row-style="{
      height:'50px'
    }"
      :header-cell-style="{
      overflow: 'hiddenColumns',
      padding: '0',
      textAlign: 'center',
      background: 'none'
    }"
      :cell-style="{
        background: 'none'
      }"
    >
        <el-table-column
          label="新闻标题"
          prop="arricleTitle"
          align="center"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <div class="blue-font-color" @click="openNews( scope.row)">{{ scope.row.arricleTitle }}</div>
          </template>

        </el-table-column>
      <el-table-column
        align="right">
        <template slot="header" slot-scope="scope">
          <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"/>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    </el-row>
    <el-row>
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="page.formNo"
        :page-size="page.formSize"
        layout="total, prev, pager, next"
        :total="page.total">
      </el-pagination>
    </div>
    </el-row>

  </div>
</template>

<script>
const { removeCollection } = require("@/api/search");
const { getCollection_Page } = require("@/api/search");
export default {
  name: "PersonalCollection",
  data() {
    return {
      tableData: [],
      search: '',

      currentPage1 :0,
      page:{
        total: 0,
        formNo: 0,
        formSize: 10,
      }
    }
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
      alert('开发中')
    },
    handleDelete(index, row) {
      this.removeCollection(row)
      // 删除完判断 若是最后一页 只有一个数据 如下判定 调整页号
      this.page.total % this.page.formSize ==1? this.page.formNo -=1 : this.page.formNo
      this.getCollection()
    },
    getCollection(){
      const params={
        user_uid : sessionStorage.getItem('UserId'),
        formNo : this.page.formNo,
        formSize : this.page.formSize
      }
      getCollection_Page(params).then(resp=>{
        this.page.total = resp.data.total
        this.tableData = resp.data.records
      }).catch(err=>{
        this.$notify.error('出错了')
      })
    },
    removeCollection(data){
      removeCollection(data).then(resp=>{
        this.$notify.success({
          title: '成功',
          message: '删除成功',
          position: 'bottom-right'
        })
        this.getCollection()
      }).catch(err=>{
        this.$notify.error({
          title: '错误',
          message: '删除失败',
          position: 'bottom-right'
        })
      })
    },
    openNews(data){
      const item={
        title : data.arricleTitle,
        uuid  : data.arrricleId  // 新闻uid
      }
      const { href } = this.$router.resolve({
        // name: val
        name: 'watch',
        query:{
          title: item.title,
          uuid: item.uuid   //新闻uid
        }
      })
      window.open(href, '_parent')
    },
    handleSizeChange(val) {
// `每页 ${val} 条`
    },
    handleCurrentChange(val) {
      this.page.formNo = val
      this.getCollection()
      // `当前页: ${val}`
    }
  },
  created() {
    this.getCollection()
  }
}
</script>

<style scoped>
.el-table {
  background: none;
}
</style>
