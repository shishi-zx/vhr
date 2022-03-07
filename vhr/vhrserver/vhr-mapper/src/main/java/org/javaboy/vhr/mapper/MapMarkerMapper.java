package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.MapMarker;

import java.util.List;

public interface MapMarkerMapper {
    int insertMapMarker(MapMarker record);
    int deleteById(Integer id);
    int updateById(MapMarker record);
    List<MapMarker> selectAll();

}
