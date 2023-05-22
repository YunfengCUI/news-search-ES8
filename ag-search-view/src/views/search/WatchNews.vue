<template>
  <div style="width: 100%;" class="main">
    <!--  新闻标题-->
    <el-row>
      <el-col :span="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="14">
        <div class="grid-content bg-purple-dark">
          <h1>{{ news.title }}</h1>
          </div>
      </el-col>
      <el-col :span="3">
        <div class="grid-content bg-purple" style="height: 64px">

          <div style="font-size: 20px;bottom: 0px;position: relative;margin-top: 20%;max-height: 24px" >

            收藏<i style="font-size: 30px;color: #f5e80a;position: relative;top: 5px" v-bind:class="isCollect ? 'el-icon-star-on':'el-icon-star-off'" @click.stop="addComment()" ></i>
            <br>
            近期访问  278 次
          </div>
        </div>
      </el-col>
      <el-col :span="3">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>
    <hr style="width: 70%">
    <el-row>
      <el-col :span="3">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="18">
        <div class="grid-content bg-purple-light">
          <!--  新闻内容-->
          <div style="margin:10px 20px;min-height: 500px" >
            <p v-html="news.content"></p>
<!--            <p v-html="news.content">{{ news.content }}</p>-->
          </div>
        </div>
      </el-col>
      <el-col :span="3">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="3">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :span="18">
        <hr>
        <div class="grid-content bg-purple-light">
          <el-row>
            <el-col :span="2">
              <div class="grid-content bg-purple">
                <div class="label-wang">
                  <i class="label-wang-in"></i>
                </div>
              </div>
            </el-col>
            <el-col :span="17">
              <div class="grid-content bg-purple">
                <!--  新闻评论功能-->
                <el-input
                  type="textarea"
                  placeholder="请输入内容"
                  v-model="comment_text"
                  maxlength="50"
                  show-word-limit
                >
                </el-input>
              </div>
            </el-col>
            <el-col :span="1">
              <div class="grid-content bg-purple">
              </div>
            </el-col>
            <el-col :span="2">
              <div class="grid-content bg-purple" >
                <el-button type="primary" @click="add_comment()" style="position: relative;top:10px">评论<i class="el-icon-position el-icon--right"></i>
                </el-button>
              </div>
            </el-col>
            <el-col :span="2">
              <div class="grid-content bg-purple">

              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="grid-content bg-purple-dark">
                <table>
                  <tr v-for="(item,index) in commentData" :key="index" v-show="commentData.length!==0"
                      style="width: 100%;display: inline-block">
                    <td>
                      <el-avatar icon="el-icon-user-solid"></el-avatar>
                    </td>
                    <td>
                      <p class="item-content" v-html="item.content"/>
<!--                      <tr v-for="(item1,index1) in item.son":key="index1">-->
<!--                          <el-avatar icon="el-icon-user-solid"></el-avatar>-->
<!--                          <td>{{item1.content}}</td>-->
<!--                      </tr>-->
                      <div>
                        <el-input
                          v-if="openReply==item.commentUuid"
                          type="textarea"
                          :autosize="{ minRows: 2, maxRows: 4}"
                          placeholder="请输入内容"
                          v-model="reply_to_others">
                        </el-input>
                      </div>
                      <span>
                  <p class="item-time" v-html="item.createdTime" style="display: inline-block"/>
                  <el-button style="display: inline" v-if="item.createdBy == userId || userId == 1"
                             @click="delComment(item.commentUuid)">
                    删除
                  </el-button>
                  <el-button style="display: inline" @click="replyComment(item)">
                    回复
                  </el-button>
                </span>
                    </td>
                  </tr>
                  <div v-show="commentData.length===0">
                    <div class="flex-column">
                    <pre>
                      还没有人评论哦！
                    </pre>
                    </div>
                  </div>
                </table>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="3">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>
      <el-backtop target=".main" :visibility-height=400></el-backtop>
  </div>
</template>

<script>
const {checkCollectionStatus} = require("@/api/search");
const {addCollection} = require("@/api/search");
const {DelComment} = require("@/api/search");
const {LastelyComment} = require("@/api/search");
const {AddComment} = require("@/api/search");
const {getByUuid} = require("@/api/search");
export default {
  name: "WatchNews",
  data() {
    return {
      //新闻实体
      news: {
        title: '',
        uuid: '',
        content: ''
      },
      comment_text: '',
      reply_to_others: '',
      commentData: [],
      userId: sessionStorage.getItem('UserId'),
      openReply: '',
      isCollect : false
    }
  },
  methods: {
    getNews(uuid) {
      const docs = {
        uuid: uuid
      }
      getByUuid(docs).then(resp => {
        this.news = resp.data
      })
    },
    add_comment() {
      const params = {
        content: this.comment_text,
        createdBy: sessionStorage.getItem('UserId'),
        createdTime: new Date(),
        objUuid: this.news.uuid, //新闻uuid
        topLevel: 0
      }
      AddComment(params).then(resp => {
        this.comment_text = ''
        this.lastelyComment(this.news.uuid)
      }).catch(err => {this.$notify.error('出错了')
      })
    },
    // 一级评论
    lastelyComment(uuid) {
      const params = {
        obj_uuid: uuid,
        formNo: 1,
        formSize: 10
      }
      LastelyComment(params).then(resp => {
        this.commentData = resp.data.result
      }).catch(err => {this.$notify.error('出错了')
      })
    },
    //子评论
    replyOthers(uuid) {
      const params = {
        obj_uuid: uuid,
        formNo: 1,
        formSize: 10
      }
      LastelyComment(params).then(resp => {
        this.$set(this.commentData[0], 'son', resp.data.result)
      }).catch(err => {this.$notify.error('出错了')
      })
    },
    // 删除评论
    delComment(commentUuid) {
      const params = {
        commentUuid: commentUuid
      }
      DelComment(params).then(resp => {
        alert('remove success')
        this.lastelyComment(this.news.uuid)
      }).catch(err => {this.$notify.error('出错了')
      })
    },
    // 回复别人
    replyComment(data) {
      if (this.openReply == data.commentUuid) {
        const params = {
          objUuid: data.commentUuid,
          content: this.reply_to_others,
          createdBy: sessionStorage.getItem('UserId'),
          createdTime: new Date()
        }
        AddComment(params).then(resp => {
          this.reply_to_others = ''
          this.replyOthers(params.objUuid)
          alert("success reply")
        }).catch(err => {this.$notify.error('出错了')
        })
      } else this.openReply = data.commentUuid
    },
    // 添加收藏
    addComment(){
      const params={
        arricleTitle : this.news.title,
        arrricleId : this.news.uuid,
        userUid : this.userId
      }
      if (params.userUid ===null){
        this.$router.push({name:'login'})
        return
      }
      if (this.isCollect !=true){
        addCollection(params).then(resp=>{
          this.checkCollectionStatus()
          this.$message({
            message: '收藏成功',
            type: 'success'
          });
        }).catch(err=>{
          this.$notify.error('出错了')
        })
      }else {
        this.$message({
          message: '该文章已被收藏',
          type: 'success'
        });
      }

    },
    //检查新闻是否被收藏
    checkCollectionStatus() {
      const params = {
        arrricleId: this.news.uuid,
        userUid: this.userId
      }
      checkCollectionStatus(params).then(resp=>{
        this.isCollect = resp.data
      }).catch(err=>{
        this.$notify.error('出错了')
      })
    }
  }
  ,
  created() {
    // 显示标题
    this.news.title = this.$route.query.title
    this.news.uuid = this.$route.query.uuid
    // 显示内容
    this.getNews(this.$route.query.uuid)
    // 显示评论
    this.lastelyComment(this.news.uuid)
  //检查是否被收藏
    this.checkCollectionStatus()
  },
  watch: {},
  computed: {}
}
</script>

<style scoped>
.el-row {
  /*margin-bottom: 20px;*/

&
:last-child {
  margin-bottom: 0;
}

}
.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  /*background: #99a9bf;*/
}

.bg-purple {
  /*background: #f30e42;*/
}

.bg-purple-light {
  /*background: #e5e9f2;*/
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.mp0 {
  margin: 0;
  padding: 0;
}

.label-wang {
  border-radius: 100%;
  border: 1px solid #ccc;
  background: #fff;
  width: 42px;
  height: 42px;
  background: url(../../assets/img/user.png) 0 0;
  background-size: 100% 100%;
}

.item-time {
  margin-block-start: 5px !important;
  margin-block-end: 2px !important;
  font-size: 13px !important;
  color: #8c939d;
}
p {
  white-space: pre-wrap;
}
</style>
