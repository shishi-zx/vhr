<template>
  <div @click.self="close" class="container">
    <div class="form-box">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="图片名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="上传图片">
          <el-upload
              class="upload-demo"
              action="http://localhost:8080/Map/manage/upload"
              multiple
              name="img"
              :before-upload="fn"
              :limit="1"
              :file-list="form.file">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
export default {
  name: "addImageView",
  props: {
    handleShowAddImageView: {
      type: Function
    }
  },
  data(){
    return {
      form: {
        name: '',
        file: [],
      }
    }
  },
  methods:{
    close() {
      this.handleShowAddImageView(false)
    },
    fn(file){
      console.log(file)
    }
  }


}
</script>

<style scoped>
.container{
  width: 100vw;
  height: 100vh;
  background-color: rgba(0,0,0,.6);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
}
.form-box{
  width: 350px;
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #FFFFFF;
  border-radius: 20px;
}
</style>