<template>
  <el-container>
    <el-aside width="200px" style="height: 100%;">
      <el-row>
        <h1 style="font-size: 30px;margin-top: 20px;">个人中心</h1>
      </el-row>
      <el-row>
        <el-button type="text" size="medium ">
          <h2>
            <router-link to="/information" append>
              <el-button class="navigation-buttons" round>
                评论
              </el-button>
            </router-link>
          </h2>
        </el-button>
      </el-row>
      <el-row>
        <el-button type="text" size="medium ">
          <h2>
            <router-link to="/collection" append>
              <el-button class="navigation-buttons" round>
                收藏
              </el-button>
            </router-link>
          </h2>
        </el-button>
      </el-row>
      <!--        非管理员不可见-->
      <el-row v-show="showUserManager">
        <el-button type="text" size="medium ">
          <h2>
            <router-link to="/manageUser" append>
              <el-button class="navigation-buttons" round>
                用户管理
              </el-button>
            </router-link>
          </h2>
        </el-button>
      </el-row>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "PersonalCenter",
  data() {
    return{
      showUserManager: (sessionStorage.getItem('UserId')===this.My_Global.adminUserId)
    }
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  /*background-color: #d3dce6;*/
  /*color: #333;*/
  text-align: center;
  /*line-height: 100px;*/
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  /*line-height: 160px;*/
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.el-button {
  padding: 0;
}

.navigation-buttons {
  font-size: 30px;
  background: none;
  border-width: 0
}
</style>
