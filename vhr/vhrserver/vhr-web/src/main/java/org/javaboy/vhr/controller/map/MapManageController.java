package org.javaboy.vhr.controller.map;

import org.javaboy.vhr.model.MapMarker;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.MapMarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/map/manage")
public class MapManageController {

    @Autowired
    MapMarkerService mapMarkerService;

    @PostMapping("/")
    public RespBean addMapMarker(@RequestBody MapMarker mapMarker){
         if(mapMarkerService.addMapMarker(mapMarker)==1){
             return RespBean.ok("添加成功");
         }
         return RespBean.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteMapMarker(@PathVariable Integer id){
        if(mapMarkerService.deleteOne(id)==1){
           return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @PutMapping("/")
    public RespBean updateMapMarker(@RequestBody MapMarker mapMarker){
        if(mapMarkerService.updateOne(mapMarker)==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @GetMapping("/")
    public List<MapMarker> getAllMapMarkers(){
        return mapMarkerService.getAll();
    }
}
