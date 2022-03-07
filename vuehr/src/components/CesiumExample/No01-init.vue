<template>
  <div class="map-box">
<!--    <div id="cesiumContainer"></div>-->
    <ModelGltf   src="http://localhost:8080/3dModel/Duck/Duck.gltf"></ModelGltf>
  </div>
</template>

<script>
import { ModelGltf   } from 'vue-3d-model';
export default {
  name: "",
  components: {ModelGltf  },
  mounted() {
    // let viewer = new Cesium.Viewer("cesiumContainer");
    // let scene = viewer.scene;
    // let modelMatric = Cesium.Transforms.eastNorthUpToFixedFrame(Cesium.Cartesian3.fromDegrees(75.62898254394531,40.02804946899414,50.0))
    // let model = scene.primitives.add(Cesium.Model.fromGltf({
    //   url: "http://localhost:8080/3dModel/Duck/Duck.gltf",
    //   modelMatrix: modelMatric,
    //   scale: 3
    // }))
    // viewer.camera.flyTo({
    //   destination: Cesium.Cartesian3.fromDegrees(75.62898254394531,40.02804946899414,1000.0)
    // })
    // console.log(model)
  }
};
</script> 
<style scoped>
  .map-box{
    width: 100%;
    height: 100%;
  }
  #cesiumContainer{
    width: 100%;
    height: 100%;
  }
</style>
