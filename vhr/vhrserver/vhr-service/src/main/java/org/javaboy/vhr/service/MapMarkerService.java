package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.MapMarkerMapper;
import org.javaboy.vhr.model.MapMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapMarkerService {
    @Autowired
    MapMarkerMapper mapMarkerMapper;

    public int addMapMarker(MapMarker mapMarker){
        return mapMarkerMapper.insertMapMarker(mapMarker);
    }

    public int deleteOne(Integer id){
        return mapMarkerMapper.deleteById(id);
    }

    public int updateOne(MapMarker mapMarker){
        return mapMarkerMapper.updateById(mapMarker);
    }

    public List<MapMarker> getAll(){
        return mapMarkerMapper.selectAll();
    }
}
