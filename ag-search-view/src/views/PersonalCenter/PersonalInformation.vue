<template>
  <div>
    <el-row>
      <el-table
        :data="tableData.filter(data => !search || data.title.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">
        <el-table-column
          align="center"
          label="日期"
          prop="createdTime">
        </el-table-column>
        <el-table-column
          align="center"
          label="标题"
          prop="title">
        </el-table-column>
        <el-table-column
          align="center"
          label="内容"
          prop="content">
        </el-table-column>
        <el-table-column
          align="right">
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入标题搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
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
const {adminComment} = require("@/api/search");
const {getMyClusm, DelComment} = require("@/api/search");
export default {
  name: "PersonalInformation",
  data() {
    return {
      tableData: [],
      search: '',
      page: {
        total: 0,
        formNo: 0,
        formSize: 10,
      }
    }
  },
  methods: {
    handleEdit(index, row) {
    },
    handleDelete(index, row) {
      this.$confirm('此操作将删除该评论, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.delComment(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
      this.page.total % this.page.formSize ===1? this.page.formNo -=1 : this.page.formNo
      this.getComment()
    },
    getComment(data) {
      const params = {
        uuid: sessionStorage.getItem('UserId'),
        formNo: this.page.formNo,
        formSize: this.page.formSize
      }
      if (params.uuid ==='1'){ //管理员 uuid
        adminComment(params).then(resp=>{
          console.log(resp)
          this.page.total = resp.data.length
          this.tableData = resp.data
        }).catch(err=>{this.$notify.error('出错了')})
      }else { //非管理员
        getMyClusm(params).then(resp => {
          this.page.total = resp.data.length
          this.tableData = resp.data
        }).catch(err => {this.$notify.error('出错了')})
      }

    },
    delComment(data) {
      const params={
        commentUuid : data.commentUuid
      }
      DelComment(params).then(resp => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          position: 'bottom-right',
          type: "success"
        });
        this.getComment()
      }).catch(err => {
        this.$notify.error({
          title: '错误',
          message: '删除失败',
          position: 'bottom-right'
        })
      })
    },
    handleSizeChange(val) {
// `每页 ${val} 条`
    },
    handleCurrentChange(val) {
      this.page.formNo = val
      this.getComment()
      // `当前页: ${val}`
    }


  },
  created() {
    this.getComment()
  }
}
</script>

<style scoped>

</style>
