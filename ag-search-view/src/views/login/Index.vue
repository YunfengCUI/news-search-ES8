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
                      <el-input id="login-username" v-model='LoginParam.username' placeholder="请输入用户名" class="input-material"></el-input>
                    </div>
                    <div class="form-group">
                      <el-input id="login-password" v-model='LoginParam.password' placeholder="请输入密码" class="input-material"></el-input>
                    </div>
                    <el-button id="login" type="submit" @click="login(LoginParam)" >登录</el-button>
                    <div style="margin-top: -40px;">
                    	<!-- <input type="checkbox"  id="check1"/>&nbsp;<span>记住密码</span>
                    	<input type="checkbox" id="check2"/>&nbsp;<span>自动登录</span> -->
                    	<div class="custom-control custom-checkbox " style="float: right;">
                            <input type="checkbox" class="custom-control-input" id="check2" >
                            <label class="custom-control-label" for="check2">自动登录</label>
                        </div>
                        <div class="custom-control custom-checkbox " style="float: right;">
                            <input type="checkbox" class="custom-control-input" id="check1" >
                            <label class="custom-control-label" for="check1">记住密码&nbsp;&nbsp;</label>
                        </div>
                    </div>
                  </form>
                  <br />
                  <small>没有账号?</small><router-link :to="{name:'register'}"><a href="register.html" class="signup">&nbsp;注册</a></router-link>
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
import{ Login } from "@/api/search";
import {Message} from "element-ui";

export default {
    name: "Login",
    data() {
        return {
          LoginParam:{
            username:'',
            password:''
          }
        }
    },
    watch: {
    },
    created() {
    },
    mounted() {
    },
    destroyed() {
    },
    methods: {
         login(data){
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

            Login(data).then(resp=>{
              /**
               * 获得返回数据
               */
              if (resp.data.success){
//                 Message({
//                   message: `<strong>登陆成功 如未跳转请手动执行</strong>
// <button href="javascript:void(0);"
// onclick="(function (){
//       window.open('/', '_parent')
// })()">点击跳转</button>`,
//                   dangerouslyUseHTMLString : true
//                 })
                sessionStorage.setItem('UserId',resp.data.result.userId)
                sessionStorage.setItem('UserName',this.LoginParam.username)
                sessionStorage.setItem('TOKEN',resp.data.result.token)
                this.$router.go(-1)
              }
              else {
                Message.error(resp.data.message)
              }
            }).catch(error=>{
              Message.error(error)
            }).finally(fin=>{
              loading.close()
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
