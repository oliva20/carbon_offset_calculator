package com.example.offsetcalculator.model.route;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.offsetcalculator.converters.TimestampConverter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/** Represents and calculates the route from user coordinates.
 * @author Andre
 * @version 1.1
 * @since 1.0
 *
 * ROOM does not store lists, refer to this stack overflow problem -> https://stackoverflow.com/questions/44736604/how-to-store-objects-in-android-room
 */
@Entity
public class Route {
    @PrimaryKey
    public Integer id;

    private String timestamp; //timestamp must string otherwise room won't persist it

    public Route(Integer id, String timestamp){
        this.timestamp = timestamp.toString();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp.toString();
    }


}