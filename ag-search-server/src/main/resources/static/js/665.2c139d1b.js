"use strict";(self["webpackChunkag_search_view"]=self["webpackChunkag_search_view"]||[]).push([[665],{665:function(e,t,s){s.r(t),s.d(t,{default:function(){return l}});var a=function(){var e=this,t=e._self._c;return t("div",{staticClass:"page login-page"},[t("div",{staticClass:"container d-flex align-items-center"},[t("div",{staticClass:"form-holder has-shadow"},[t("el-row",{staticStyle:{width:"100%"}},[t("el-col",{attrs:{span:2}},[t("p")]),t("el-col",{attrs:{span:12}},[t("div",{staticClass:"info d-flex align-items-center"},[t("div",{staticClass:"content"},[t("img",{staticStyle:{width:"635px"},attrs:{src:s(2244)}})])])]),t("el-col",{staticClass:"login-form",attrs:{span:8}},[t("div",{staticClass:"form d-flex align-items-center login-form-item"},[t("div",{staticClass:"content login-form-content"},[t("div",{staticClass:"login-logo"},[t("img",{staticStyle:{width:"250px"},attrs:{src:s(5080)}})]),t("form",{staticClass:"form-validate",attrs:{method:"post",id:"loginFrom"}},[t("div",{staticClass:"form-group"},[t("el-input",{staticClass:"input-material",attrs:{placeholder:"请输入登录名"},model:{value:e.RegisterParam.userLoginName,callback:function(t){e.$set(e.RegisterParam,"userLoginName",t)},expression:"RegisterParam.userLoginName"}})],1),t("div",{staticClass:"form-group"},[t("el-input",{staticClass:"input-material",attrs:{id:"login-username",placeholder:"请输入用户名"},model:{value:e.RegisterParam.userName,callback:function(t){e.$set(e.RegisterParam,"userName",t)},expression:"RegisterParam.userName"}})],1),t("div",{staticClass:"form-group"},[t("el-input",{staticClass:"input-material",attrs:{id:"login-password",placeholder:"请输入密码"},model:{value:e.RegisterParam.userPassword,callback:function(t){e.$set(e.RegisterParam,"userPassword",t)},expression:"RegisterParam.userPassword"}})],1),t("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.register(e.RegisterParam)}}},[e._v("注册")])],1),t("br")])])]),t("el-col",{attrs:{span:2}},[t("p")])],1)],1)])])},A=[],r=s(8185),i={name:"Register",data(){return{RegisterParam:{userLoginName:"",userName:"",userPassword:""},LoginParam:{username:"",password:""}}},watch:{RegisterParam:{handler(e,t){this.LoginParam.username=e.userLoginName,this.LoginParam.password=e.userPassword},deep:!0}},created(){},mounted(){},destroyed(){},methods:{register(e){const t=this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});(0,r.AddUser)(this.RegisterParam).then((e=>{200===e.data.code&&(0,r.Login)(this.LoginParam).then((e=>{sessionStorage.setItem("UserId",e.data.result.userId),sessionStorage.setItem("TOKEN",e.data.result.token),t.close(),this.$router.replace("/home")})).catch((e=>{this.$notify.error("出错了")}))})).catch((e=>{this.$notify.error("出错了")}))}}},o=i,n=s(1001),c=(0,n.Z)(o,a,A,!1,null,"4702a913",null),l=c.exports},5080:function(e){e.exports="data:image/png;base64,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"},2244:function(e,t,s){e.exports=s.p+"img/word-cloud.d21d316b.png"}}]);
//# sourceMappingURL=665.2c139d1b.js.map