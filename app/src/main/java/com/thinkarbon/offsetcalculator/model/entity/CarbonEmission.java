package com.thinkarbon.offsetcalculator.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CarbonEmission {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Double emission;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getEmission() {
        return emission;
    }

    public void setEmission(Double emission) {
        this.emission = emission;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CarbonEmission{" +
                "id=" + id +
                ", emission=" + emission +
                ", date='" + date + '\'' +
                '}';
    }
}
