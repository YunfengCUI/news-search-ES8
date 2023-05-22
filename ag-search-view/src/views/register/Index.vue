<template>
  <div class="page login-page">
      <div class="container d-flex align-items-center">
        <div class="form-holder has-shadow">
          <el-row style="width:100%">
            <el-col :span="2"><p></p></el-col>
            <!-- Logo & Information Panel-->
            <el-col :span="12">
              <div class="info d-flex align-items-center ">
                <div class="content ">
                  <img src="../../assets/img/word-cloud.png" style="width:635px"/>
                </div>
              </div>
            </el-col>
            <!-- Form Panel    -->
            <el-col :span="8" class="login-form">
              <div class="form d-flex align-items-center login-form-item">
                <div class="content login-form-content">
                  <div class="login-logo">
                    <img src="../../assets/img/logo.png" style="width:250px"/>
                  </div>
                  <form method="post" class="form-validate" id="loginFrom">
                    <div class="form-group">
                      <el-input  v-model='RegisterParam.userLoginName' placeholder="请输入登录名" class="input-material"></el-input>
                    </div>
                    <div class="form-group">
                      <el-input id="login-username" v-model='RegisterParam.userName' placeholder="请输入用户名" class="input-material"></el-input>
                    </div>
                    <div class="form-group">
                      <el-input id="login-password" v-model='RegisterParam.userPassword' placeholder="请输入密码" class="input-material"></el-input>
                    </div>
                    <el-button  type="primary" @click="register(RegisterParam)" >注册</el-button>
                  </form>
                  <br />
<!--                  <small>没有账号?</small>-->
<!--                  <a href="register.html" class="signup">&nbsp;注册</a>-->
                </div>
              </div>
            </el-col>
            <el-col :span="2"><p></p></el-col>
		  </el-row>
        </div>
      </div>
    </div>
</template>

<script>

import {AddUser, Login} from "@/api/search";

export default {
    name: "Register",
    data() {
        return {
          RegisterParam:{
            userLoginName: '',
            userName: '',
            userPassword: '',
          }, //登录信息
          LoginParam:{
          username: '',
          password: ''
      }
        }
    },
    watch: {
      RegisterParam: {
        handler(newVal, oldVal) {
          this.LoginParam.username = newVal.userLoginName
          this.LoginParam.password = newVal.userPassword
        },
        // 开启深度监听
        deep: true
      }
    },
    created() {
    },
    mounted() {
    },
    destroyed() {
    },
    methods: {
         register(data){

           /**
            * 加载动画开始
            * @type {ElLoadingComponent}
            */
           const loading = this.$loading({
             lock: true,
             text: 'Loading',
             spinner: 'el-icon-loading',
             background: 'rgba(0, 0, 0, 0.7)'
           });
           // 登录信息

            AddUser(this.RegisterParam).then(resp=>{
              if (resp.data.code === 200){
                Login(this.LoginParam).then(resp=>{
                  /**
                   * 获得返回数据
                   */
                  sessionStorage.setItem('UserId',resp.data.result.userId)
                  sessionStorage.setItem('TOKEN',resp.data.result.token)
                  loading.close();//关闭加载动画
                  this.$router.replace('/home');
                }).catch(error=>{this.$notify.error('出错了')
                })
              }
            }).catch(err=>{this.$notify.error('出错了')
            })
        }
    }
  };
</script>

<style scoped>
.login-page {
  position: relative;
}

.login-page::before {
  content: '';
  width: 100%;
  height: 100%;
  display: block;
  z-index: -1;
  /* background: url(../img/p_big3.jpg); */
  background-size: cover;
  -webkit-filter: blur(10px);
  filter: blur(10px);
  z-index: 1;
  position: absolute;
  top: 0;
  right: 0;
}

.login-page .container {
  min-height: 100vh;
  z-index: 999;
  padding: 20px;
  position: relative;
}

.login-page .form-holder {
  width: 100%;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 50px;
  margin-top: 100px;
}

.login-page .form-holder .info, .login-page .form-holder .form {
  min-height: 50vh;
  padding: 40px;
  height: 100%;
}

.login-page .form-holder div[class*='col-'] {
  padding: 0;
}

.login-page .form-holder .info {
}

.login-page .form-holder .info h1 {
  font-size: 2.5em;
  font-weight: 600;
}

.login-page .form-holder .info p {
  font-weight: 300;
}

.login-page .form-holder .form .form-group {
  position: relative;
  margin-bottom: 30px;
}

.login-page .form-holder .form .content {
  width: 80%;
}

.login-page .form-holder .form form {
  width: 100%;
  max-width: 400px;
}

.login-page .form-holder .form #login, .login-page .form-holder .form #register {
  margin-bottom: 20px;
  cursor: pointer;
}

.login-page .form-holder .form a.forgot-pass, .login-page .form-holder .form a.signup {
  font-size: 0.9em;
  color: #85b4f2;
}

.login-page .form-holder .form small {
  color: #aaa;
}

.login-page .form-holder .form .terms-conditions label {
  cursor: pointer;
  color: #aaa;
  font-size: 0.9em;
}

.login-page .copyrights {
  width: 100%;
  z-index: 9999;
  position: absolute;
  bottom: 0;
  left: 0;
  color: #fff;
}
.login-form{
    box-shadow: 6px 5px 14px 3px rgb(147 194 228 / 56%);
    border-radius: 10px;
}
.login-form-item {
    padding: 0px !important;
    background: #fff;
    border-radius: 10px;
}
.login-form-content{
    padding: 10% !important;
}
.login-logo{
    text-align: center;
    padding-bottom: 20px;
}
</style>
