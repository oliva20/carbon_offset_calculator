package com.example.offsetcalculator.model.route;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.offsetcalculator.converters.TimestampConverter;

import java.util.List;

/** Represents and calculates the route from user coordinates.
 * @author Andre
 * @version 1.0
 * @since 1.0
 *
 * ROOM does not store lists, refer to this stack overflow problem -> https://stackoverflow.com/questions/44736604/how-to-store-objects-in-android-room
 */
//TODO: This needs testing as well as the DAO
@Entity
public class Route {
    @PrimaryKey
    public Integer id;
    @TypeConverters({TimestampConverter.class})
    private Long timestamp;

    public Route(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
