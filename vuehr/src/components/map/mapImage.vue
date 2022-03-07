<template>
  <div>
    <div class="container" @click.self="close" >
      <div class="amap-wrapper" v-loading="loading">
        <div class="header">
          <el-amap-search-box
              class="search-box"
              :searchOption="searchOption"
              :onSearchResult ="onSearchResult"
          />
          <span>{{name}} <i @click="editName" class="el-icon-edit-outline"></i> </span>
          <div class="btn-box">
            <el-button v-if="text===''" @click="editText('')" type="primary" icon="el-icon-plus">添加标记文本</el-button>
            <el-button v-else type="warning" @click="editText(text)" icon="el-icon-edit">修改标记文本</el-button>
            <el-button v-show="isEdited" @click="save" type="success" icon="el-icon-document-checked">保存修改</el-button>
            <el-button v-show="isAddMap" @click="add" type="success" icon="el-icon-document-checked">确认新建</el-button>
            <el-button @click="close" type="info" icon="el-icon-circle-close">退出</el-button>
          </div>
        </div>
        <el-amap ref="GdMap" class="amap-box" :vid="'amap-vue'"
                 :amap-manager="amapManager"
                 :zoom="zoom"
                 :events="events"
                 :plugin="plugin"
                 :center="center">
          <el-amap-marker
              v-for="(marker,index) in markers"
              :key ="'marker'+index"
              :position ="marker"
          > </el-amap-marker>
          <el-amap-text
              :text="text"
              :position="markers[0]"
              :offset="[-10,-50]">

          </el-amap-text>

        </el-amap>

      </div>
    </div>
  </div>
</template>

<script>
import {AMapManager} from "vue-amap"
let amapManager = new AMapManager()
export default {
  name: "MapImage",
  props:{
    handlePreview: {
      type: Function,
    },
    updateMapMarker: {
      type: Function,// 修改父组件列表数据
    },
    addMapMarker:{
      type: Function,//像父组件列表中添加数据
    },
    isAddMap:{
      type: Boolean,
      default: false////标记是否是新建地图模式
    },
    selected: {
      type: Object,
      default: null
    }
  },
  data(){
    const self = this
    return {
      id: -1,
      name: "默认标题",
      create_time: new Date(),
      show: false,
      img_url: '',
      amapManager,
      loaded:false,
      zoom: 17,
      center: [116.24737026556781,40.21962964350059],
      markers: [[116.24737026556781,40.21962964350059]],          //标记
      text: "",
      isEdited: false,//标记是否修改过
      loading: false,
      searchOption: {
        city: "全国",
        citylimit: false,
      },
      events:{
        //左键单击定位中心点
        click: (e)=>{
          let {lat,lng} = e.lnglat
          this.center=[lng,lat]
        },
        //右键单机标记地点
        rightclick:(e)=>{
          this.isEdited = true
          let {lat,lng} = e.lnglat
          this.center=[lng,lat]
          this.markers=[];
          this.markers.push(this.center)
        },
        //
        zoomchange:(e)=>{
          this.isEdited = true
          this.zoom = this.$refs.GdMap.$$getInstance().getZoom()
        }
      },
      plugin:[
        {

          enableHighAccuracy:true,   //是否使用高精度定位，默认true
          timeout:100,               //超过10秒后停止定位，默认：无穷大
          convert:true,          //自动偏移后的坐标为高德坐标，默认：true
          showButton:true,       //显示定位按钮，默认：true
          buttonPosition:'RB',  //定位按钮停靠位置，默认'LB'，左下角
          showMarker:true,      //定位成功后在定位到的位置显示标记，默认：true
          showCircle:true,      //定位成功后用圆圈表示定位精度范围，默认：true
          panToLocation:true,   //定位成功后将定位到的位置作为地图中心点，默认true
          zoomToAccuracy:true,   //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
          extensions:"all",
          pName:"Geolocation",     //AMap-Geolocation 定位插件
        }]
    }
  },
  mounted() {
    if(!this.isAddMap){
      //init
      this.id = this.selected.id
      this.name = this.selected.name
      this.text = this.selected.text
      this.markers[0][0] = Number(this.selected.marker_lat)
      this.markers[0][1] = Number(this.selected.marker_lng)
      this.center = this.markers[0]
      this.zoom = this.selected.zoom
      this.create_time = this.selected.create_time
    }
  },
  methods: {
    close(){
      if(this.isEdited){
        this.$confirm('修改未保存？是否继续退出', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handlePreview(false);
        }).catch(() => {

        });
      }else{
        this.handlePreview(false);
      }
    },
    //保存修改
    save(){
      this.isEdited = false
      let {markers,text,zoom} = this
      let obj = {
        id: this.id,
        name: this.name,
        text: this.text,
        marker_lat: String(this.markers[0][0]),
        marker_lng: String(this.markers[0][1]),
        zoom: this.zoom,
        create_time: this.create_time
      }
      //先上传
      this.loading = true
      this.putRequest("/map/manage/",obj).then(data=>{
        if(data.status!==200){
          throw new Error()
        }
        // 然后更新列表
        this.updateMapMarker(obj)
      }).catch(e=>{
        console.log(2,e)
      }).finally(()=>{
        this.loading = false
      })
    },
    //新建
    add(){
      this.isEdited = false
      let obj = {
        id: null,
        name: this.name,
        text: this.text,
        marker_lat: String(this.markers[0][0]),
        marker_lng: String(this.markers[0][1]),
        zoom: this.zoom,
        create_time: this.create_time
      }
      //上传后端
      this.loading = true
      this.postRequest("/map/manage/",obj).then(data=>{
        if(data.status!==200){
          throw new Error()
        }
        //更新界面
        this.addMapMarker(obj);
        this.handlePreview(false)
      }).catch(err=>{

      }).finally(()=>{
        this.loading = false
      })
    },

    //点击搜索后触发的事件
    onSearchResult(pois){
      this.center = [pois[0].lng,pois[0].lat]        //选择了第一个值
    },
    //编辑标记文本
    editText(old){
      this.$prompt('请输入内容', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: String(old)
      }).then(({ value }) => {
        this.text = value
        this.isEdited = true
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    //修改标题
    editName(){
      let old = this.name
      this.$prompt('请输入标题', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: old
      }).then(({ value }) => {
        if(value.trim()==="")throw new Error("标题不能为空")
        this.name = value
        this.isEdited = true
        this.$message({
          type: 'success',
          message: ' 修改成功 '
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
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
.img-box{
  min-width: 300px;
  min-height: 300px;
  background-color: #409eff;
  max-width: 80vw;
  max-height: 80vh;
  border-radius: 20px;
  padding: 10px;
}
.header{
  background-color: #ccdcec;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header .btn-box{
  margin-right: 0;
}
.amap-wrapper {
  width: 85%;
  height: 80%;
  margin: 0 auto;
}
</style>