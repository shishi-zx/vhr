package org.javaboy.vhr.model;

import java.io.Serializable;
import java.util.Date;

public class MapMarker implements Serializable {
    private Integer id;
    private String name;
    private String text;
    private String marker_lat;
    private String marker_lng;
    private Integer zoom;
    private Date create_time;

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MapMarker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", marker_lat='" + marker_lat + '\'' +
                ", marker_lng='" + marker_lng + '\'' +
                ", zoom=" + zoom +
                ", create_time=" + create_time +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMarker_lat() {
        return marker_lat;
    }

    public void setMarker_lat(String marker_lat) {
        this.marker_lat = marker_lat;
    }

    public String getMarker_lng() {
        return marker_lng;
    }

    public void setMarker_lng(String marker_lng) {
        this.marker_lng = marker_lng;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }
}
