<template>
  <el-container class="el-container" >
    <el-header height="160px">
      <el-row>
        <el-col :span="5">
          <div class="logo-icon" @click="home()">
<!--            <img src="../assets/img/logo.png" alt="">-->
            <h1>基于Spring boot 的新闻检索系统的设计与实现</h1>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="search-box">

            <el-input v-model="queryParams.keyword" placeholder="搜索" @keyup.enter.native="search"></el-input>
          </div>
          <el-button class="search-btn" @click="search()">搜索</el-button>
          <a href="Javascript:void(0)" class="detail-search" @click="detailSearch()">高级检索</a>
          <el-row class="detail-search-input" v-show="detailDisplay">
            <el-date-picker type="date" placeholder="时间开始" v-model="queryParams.fromDate"
                            style="width: 150px; height:30px;"></el-date-picker>
            -
            <el-date-picker type="date" placeholder="时间结束" v-model="queryParams.endDate"
                            style="width: 150px; height:30px;"></el-date-picker>
            &nbsp;&nbsp;<el-input v-model="queryParams.source" placeholder="网站来源" style="width: 150px;"></el-input>
            &nbsp;&nbsp;<a href="Javascript:void(0)" class="detail-search">查询</a>
          </el-row>
        </el-col>
        <el-col :span="6">
          <div class="user-div">
            <div class="user-info">
              <div class="label-wang">
                <i class="label-wang-in"></i>
              </div>

              <el-dropdown>
                <span class="label-word">{{ loginInformation.userLoginName }}</span>
                <el-dropdown-menu slot="dropdown">
                  <div class="option-down">
                    <el-button type="text" class="personal-center-button">
                      <el-dropdown-item icon="el-icon-user-solid">
                        <router-link to="/personal" tag="font">个人中心</router-link>
                      </el-dropdown-item>
                    </el-button>
                  </div>
                  <div class="option-down">
                    <el-button type="text" class="personal-center-button">
                      <el-dropdown-item icon="el-icon-star-on">
                        <router-link to="/collection" tag="font">收藏</router-link>
                      </el-dropdown-item>
                    </el-button>
                  </div>
                  <div class="option-down">
                    <el-button type="text" class="personal-center-button">
                      <el-dropdown-item icon="el-icon-message">
                        <router-link to="/information" tag="font">评论</router-link>
                      </el-dropdown-item>
                    </el-button>
                  </div>
                  <div class="option-down" v-show="showUserManager">
                    <el-button type="text" class="personal-center-button">
                      <el-dropdown-item icon="el-icon-setting">
                        <router-link to="/manageUser" tag="font">用户管理</router-link>
                      </el-dropdown-item>
                    </el-button>
                  </div>
                </el-dropdown-menu>
              </el-dropdown>
              <router-link :to="{name:'login'}">
                <el-button type="text" v-if="!loginInformation.userId">登录</el-button>
              </router-link>
              <el-button type="text" v-if="loginInformation.userId" @click="logout(loginInformation)">退出</el-button>
            </div>
          </div>
        </el-col>
        <el-col :span="1"><p></p></el-col>
      </el-row>
      <el-row>
        <el-menu class="tab-menu" mode="horizontal">
          <el-menu-item index="1">要闻</el-menu-item>
          <el-menu-item index="2">财经</el-menu-item>
          <el-menu-item index="3">科技</el-menu-item>
          <el-menu-item index="4">娱乐</el-menu-item>
          <el-menu-item index="5">体育</el-menu-item>
        </el-menu>
      </el-row>
    </el-header>
    <el-main >
      <keep-alive>
        <router-view ref='main'></router-view>
      </keep-alive>
    </el-main>
    <el-backtop target=".el-container" :visibility-height=200></el-backtop>
  </el-container>
</template>
<script>
import {AddSearchHistory, logout, selectByUuid} from "@/api/search";

const {addHotWord} = require("@/api/search");

export default {
  name: 'IndexView',
  data() {
    return {
      queryParams: {
        keyword: '',
        source: '',
        fromDate: '',
        endDate: '',
      },
      loginInformation: {
        userId: '', //登陆页面传过来 查询后台用户信息
        userLoginName: '',
        userToken: ''
      },
      detailDisplay: false,
      showUserManager: (sessionStorage.getItem('UserId')===this.My_Global.adminUserId)
    }
  },
  created() {
    this.createME()
  },
  methods: {
    search() {
      if (this.queryParams.keyword==='') {
        this.$message.warning({
          message:'请输入搜索内容',
          showClose: true,
          offset: 15
        })
        return}
      this.$router.push({path: '/search/result'}).then(this.handleSearch);
    },
    handleSearch() {
        this.$refs['main'].handleSearch(this.queryParams)
        this.add_hot_word(this.queryParams.keyword);
    },
    detailSearch() {
      if (this.detailDisplay) {
        this.detailDisplay = false;
      } else {
        this.detailDisplay = true;
      }
    },
    home() {
      this.$router.push({path: '/home'});
    },
    async createME() {
      this.loginInformation.userId = sessionStorage.getItem('UserId')
      if (this.loginInformation.userId) {
        selectByUuid(this.loginInformation.userId).then(resp => {
          resp.data.result.userToken = sessionStorage.getItem('TOKEN')
          this.loginInformation = resp.data.result
          // sessionStorage.removeItem('UserId')
        }).catch(error => {
          this.$notify.error('出错了')
        })
      } else console.log('未登录')
    },
    logout(data) {
      logout(data.userId).then(resp => {
        this.loginInformation = {}
        console.log('用户下线')

        sessionStorage.clear()
      }).catch(error => {
        this.$notify.error('出错了')
      })
    },
    /**
     * 添加搜索历史
     * @param data
     * 记录 创建人 创建时间
     */
    add_search_history(data) {
      const params = {
        words: data,
        createdBy: this.loginInformation.userName,
        createdTime: new Date().getDate()
      }
      AddSearchHistory(params).then(resp => {
      }).catch(err => {
        this.$notify.error('出错了')
      })
    },
    add_hot_word(data) {
      const param = {
        words: data,
        searchTime: new Date(),
        createdTime: new Date()
      }
      addHotWord(param).then(resp => {

      }).catch(err => {
        this.$notify.error('出错了')
      }).finally()
    },
    // 跳转路由
    openRouterPath(routerdata) {
      switch (routerdata) {
        case '个人中心':
          this.$router.push({path: '/personal'}).then();
          break
      }
    }
  }
}
</script>
<style>
.search-box {
  cursor: pointer;
  border: 1px solid rgb(77, 148, 199);
  border-radius: 10px;
  width: 350px;
  height: 40px;
  padding: 0 20px;
  margin-top: 30px;
  box-shadow: 0 0 15px #bcd1e4 inset;
  float: left;
}

.search-btn {
  margin: 30px 10px 15px 10px;
}

.search-box .el-input .el-input__inner {
  padding: 0;
  border: none;
  cursor: pointer !important;
  background-color: transparent !important;
}

.tab-menu {
  background-color: transparent !important;
  margin: 0px 280px;
  border-bottom: 0px !important;
  height: 45px;
}

.tab-menu .el-menu-item {
  background-color: transparent !important;
  height: 45px;
  line-height: 45px;
}

.detail-search {
  font-size: 12px;
}

.detail-search-input {
  height: 30px;
}

.detail-search-input .el-input__inner {
  height: 30px;
}

.detail-search-input .el-input__icon {
  line-height: 30px;
}

.user-div {
  margin-top: 30px;
  height: 44px;
  float: right;
}

.user-info {
  display: flex;
}

.label-wang {
  border-radius: 100%;
  border: 1px solid #ccc;
  background: #fff;
  width: 42px;
  height: 42px;
  background: url(../assets/img/user2.jpg) 0 0;
  background-size: 100% 100%;
}

.label-word {
  font-size: 16px;
  color: #303030;
  display: inline-block;
  text-align: center;
  line-height: 34px;
  padding: 5px;
  cursor: pointer;
  overflow: hidden;
  font-weight: bold;
  white-space: nowrap;
  position: relative;
}

.personal-center-button {
  margin: 0;
  padding: 0;
  width: 100%;
  text-align: left;
}
</style>
