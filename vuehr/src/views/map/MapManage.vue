<template>
    <div>
      <div class="header">
        <div>
          <el-input placeholder="请输入地图名进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                    clearable
                    style="width: 350px;margin-right: 10px" v-model="keyword"
          ></el-input>
          <el-button icon="el-icon-search" type="primary" >
            搜索
          </el-button>
        </div>
        <div>
          <el-button type="primary" icon="el-icon-plus" @click="handleShowAddImageView">
            添加地图
          </el-button>
        </div>
      </div>
      <el-table v-loading="list_loading"
          :data="tableData"
          style="width: 100%"
          @selection-change="handleSelectionChange"
          :default-sort = "{prop: 'create_time', order: 'descending'}"
          stripe>
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="create_time"
            label="创建时间"
            sortable
            width="180">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.create_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="地图名称"
            width="180">
        </el-table-column>
        <el-table-column
            prop="text"
            label="地图描述"
            width="300">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="handlePreview(true,scope.row)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div v-loading="list_loading" class="block" style="margin-top: 20px">
        <span class="demonstration">调整每页显示条数</span>
        <el-pagination
            :page-sizes="[20, 50, 100]"
            :page-size="20"
            layout="sizes, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
      <MapImage :handlePreview="handlePreview"
                :updateMapMarker="updateMapMarker"
                :addMapMarker="addMapMarker"
                :isAddMap="isAddMap"
                :selected="selected" v-if="show_img"></MapImage>
    </div>
</template>

<script>
import MapImage from "../../components/map/mapImage";
import AddImageView from "../../components/map/addImageView";
  export default {
    name: "MapManage",
    components: {MapImage,AddImageView},
      data(){
        return {
          tableData: [{
            create_time: '2016-05-02',
            name: '示例',
            text: "示例",
            id: 1,
            marker_lat: 0,
            marker_lng: 0,
            zoom: 3
          }],
          show_img: false,
          keyword: null,//搜索狂内容,
          isAddMap: false,
          list_loading: true,
          total: 1,
          selected: null
        }
      },
    mounted(){
      this.list_loading = true
      this.getRequest("/map/manage/").then((data)=>{
        this.tableData = data
        this.total = data.length
        this.list_loading = false
      }).catch(e=>{
        this.$message("获取列表失败")
        this.list_loading = false
      })

    },
    methods: {
      handleSelectionChange(){
        console.log('切换选择')
      },
      handlePreview(f=true, m=null){
        this.isAddMap = false
        this.show_img = f
        if(m){
          let obj = this.tableData.find((e=>{
            return e.id===m.id
          }))
          this.selected = obj
        }
      },
      handleDelete(id){
        this.loading = true
        console.log(id)
        this.deleteRequest("/map/manage/", id).then(data=>{
          console.log(1,data)
        }).catch(e=>{
          console.log(2,e)
        }).finally(()=>{
          this.loading = false
        })
      },
      handleShowAddImageView(f=true){
        this.isAddMap = true
        this.show_img = f
      },
      // 回到列表修改数据
      updateMapMarker(obj){
        let marker = this.tableData.find((i=>{
          return i.id === obj.id
        }))
        if(marker){
          marker.name = obj.name
          marker.text = obj.text
          marker.marker_lng = obj.marker_lng
          marker.marker_lat = obj.marker_lat
          marker.zoom = obj.zoom
          marker.create_time = obj.create_time
        }
      },
      //回到列表添加数据
      addMapMarker(obj){
        console.log(obj)
        this.tableData.push(Object.assign({},obj))
      },
    }
  }
</script>

<style scoped>
.hidden{
  display: none;
}
.header{
  display: flex;
  justify-content: space-between;
}
</style>