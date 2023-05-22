<template>
  <div style="height: 100%">
    <el-row>
      <!-- Form -->
      <el-button type="text" @click="dialogFormVisible = true">添加用户</el-button>
      <el-dialog title="添加账号" :visible.sync="dialogFormVisible" center>
        <el-form :model="UserInfo" :rules="rules" ref="ruleForm">
<!--          <el-form-item label="创建时间" :label-width="formLabelWidth">-->
<!--            <el-input v-model="UserInfo.createTime" autocomplete="off"  suffix-icon="el-icon-date" type="datetime"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="创建者" :label-width="formLabelWidth">-->
<!--            <el-input v-model="UserInfo.createUserId" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="更新时间" :label-width="formLabelWidth">-->
<!--            <el-input v-model="UserInfo.updateTime" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="更新者" :label-width="formLabelWidth">-->
<!--            <el-input v-model="UserInfo.updateUserId" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="用户名" :label-width="formLabelWidth" prop="userName">
            <el-input v-model="UserInfo.userName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="登录名" :label-width="formLabelWidth" prop="userLoginName">
            <el-input v-model="UserInfo.userLoginName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" :label-width="formLabelWidth" prop="userPassword">
            <el-input v-model="UserInfo.userPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="formLabelWidth" autocomplete="off">
            <el-select v-model="UserInfo.userSex" placeholder="请选择性别">
              <el-option label="男" value="1" ></el-option>
              <el-option label="女" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input v-model="UserInfo.userPhone" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addUser();dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </el-row>

    <el-row>
      <el-table :data="tableData" style="width: 100%" highlight-current-row stripe
                ref="multipleTable"  @row-click = "openRow"  >
        <el-table-column type="expand" >
            <template slot-scope="props" >
              <el-form label-position="left" class="demo-table-expand" :model="props" :rules="rules">
<!--                <el-form-item label="创建时间">-->
<!--                  <el-input v-model="props.row.createTime" :disabled="DisableEdit"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="创建者">-->
<!--                  <el-input v-model="props.row.createUserName" :disabled="DisableEdit"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="更新时间">-->
<!--                  <el-input v-model="props.row.updateTime" :disabled="DisableEdit"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="更新者">-->
<!--                  <el-input v-model="props.row.updateUserName" :disabled="DisableEdit"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="用户名">
                  <el-input v-model="props.row.userName" :disabled="DisableEdit"></el-input>
                </el-form-item>
                <el-form-item label="登录名">
                <el-input v-model="props.row.userLoginName" :disabled="DisableEdit"></el-input>
              </el-form-item>
                <el-form-item label="性别">
                  <el-input v-model="props.row.userSex" :disabled="DisableEdit" v-if="DisableEdit"></el-input>
                  <el-select v-model="props.row.userSex" placeholder="请选择性别" v-else>
                    <el-option label="男" value="1" ></el-option>
                    <el-option label="女" value="2"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="手机号" >
                  <el-input v-model="props.row.userPhone" :disabled="DisableEdit"></el-input>
                </el-form-item>
<!--                <el-form-item label="用户id">-->
<!--                  <el-input v-model="props.row.userId" :disabled="DisableEdit"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item size="large">
                  <el-button type="primary" @click="DisableEdit=false" v-if="DisableEdit===true" >编辑</el-button>
<!--                  取消禁用状态 开始编辑 隐藏编辑按钮显示提交-->
                  <el-button type="primary" v-else @click="ModifyUserInformation(props.row)">提交</el-button>
                  <el-button type="danger" @click="deleteChoose(props.row)">删除</el-button>
                </el-form-item>
              </el-form>

            </template>
        </el-table-column>
        <el-table-column label="用户名" prop="userName">
        </el-table-column>
        <el-table-column label="登录名" prop="userLoginName">
        </el-table-column>
        <el-table-column label="手机号" prop="userPhone">
        </el-table-column>
        <el-table-column label="性别">
          <template slot-scope="scope">
            <span v-if="scope.row.userSex===1"><i class="el-icon-male"></i></span>
            <span v-else-if="scope.row.userSex===2"><i class="el-icon-female"></i></span>
            <span v-else>???</span>
          </template>
        </el-table-column>
      </el-table>
    </el-row>

    <el-row>
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
    </el-row>
  </div>
</template>

<script>
const {DelUser} = require("@/api/search");
const {EditUserInfo} = require("@/api/search");
const {AddUser} = require("@/api/search");
const {queryPageUser} = require("@/api/search");
export default {
  name: "ManagementUser",
  data() {
    return {
      tableData: [],
      search: '',
      page: {
        total: 0,
        formNo: 1,
        formSize: 10,
      },
      DisableEdit: true ,// 编辑禁用状态 开启,
      dialogFormVisible: false,
      UserInfo: {
        createUserId : sessionStorage.getItem('UserId'),// 创建者   当前用户
        updateUserId : sessionStorage.getItem('UserId'),// 更新者    当然用户
        updateUserName : sessionStorage.getItem('UserName'), //更新者
        createUserName : sessionStorage.getItem('UserName'), //创建者
        userName: '',// 用户名    userName
        userLoginName: '',// 登录名  、、前台显示
        userPassword: '',// 密码
        userSex : '',// 性别
        userPhone: ''// 手机号     允许null
      },
      formLabelWidth: '150px',
      rules: {
        userName: [ //用户名
          {required: true, message: '请输入用户名', trigger:'blur'}
        ],
        userLoginName: [ //登录名
          {required: true, message: '请输入登录名', trigger:'blur'}
        ],
        userPassword: [ //密码
          {required: true, message: '请输入密码', trigger:'blur'}
        ]
      }
    }
  },
  methods: {
    handleSizeChange(val) {
      // `每页 ${val} 条`
    },
    handleCurrentChange(val) {
      this.page.formNo = val
      this.getUser(this.page)
      // `当前页: ${val}`
    },
    getUser(data) {
      queryPageUser(data).then(resp => {
        this.tableData = resp.data.result.records
        this.page.total = resp.data.result.total
      }).catch(err => {
        this.$notify.error('出错了')
      })
    },
    deleteChoose(data){
      let uuid = data.userId
      this.$confirm('此操作将永久删除该对象, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        DelUser(uuid).then(resp=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.page.total % this.page.formSize === 1 ? this.page.formNo -= 1 : this.page.formNo
          this.getUser(this.page) // 获取数据
        }).catch(err=>{
          this.$message({
            type: 'error',
            message: '删除失败!'
          });
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    openRow(row,column,event){
      this.DisableEdit = true // 开启编辑禁用
      this.$refs['multipleTable'].toggleRowExpansion(row)
    },
    ModifyUserInformation(data) {
      // 将当前用户修改为更新者
      data.updateUserName = sessionStorage.getItem('UserName')
      data.updateUserId = sessionStorage.getItem('UserId')
      EditUserInfo(data).then(resp=>{
        this.$notify.success({
          title: '成功',
          message: '更新成功',
          position: 'bottom-right'
        })
        this.getUser(this.page)
      }).catch(err=>{this.$notify.error('出错了')
      })
    },
    addUser() {
      AddUser(this.UserInfo).then(resp=>{
        this.$notify.success({
          title: '成功',
          message: '添加成功',
          position: 'bottom-right'
        })
        this.getUser(this.page) // 刷新表格
      }).catch(err=>{this.$notify.error('出错了')
      })
    }
  },
  created() {
    this.getUser(this.page)
  }
}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-left: 40px;
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
