package com.example.offsetcalculator.model.route;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/** Coordinate for the tracking service
 * @author Andre
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Coordinate {
    @PrimaryKey(autoGenerate = true)
    private Integer coordinateId;
    @ForeignKey(entity = Route.class,parentColumns = "id", childColumns = "parentRouteId", onDelete = CASCADE) //delete coordinate if parent route gets deleted
    private Integer parentRouteId; //route primary key
    private Double latitude;
    private Double longitude;
    private String transportType = "foot"; //this prop will be used to find out what type of emission to be calculated

    public Coordinate(Double latitude, Double longitude, Integer parentRouteId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.parentRouteId = parentRouteId;
    }

    public Integer getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(Integer coordinateId) {
        this.coordinateId = coordinateId;
    }

    public Integer getParentRouteId() {
        return parentRouteId;
    }

    public void setParentRouteId(Integer parentRouteId) {
        this.parentRouteId = parentRouteId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setTransportType(String transportType){this.transportType = transportType;}

    public String getTransportType(){return transportType;}

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude = " + latitude +
                ", longitude = " + longitude +
                ", transportType = " + transportType +
                '}';
    }
}

