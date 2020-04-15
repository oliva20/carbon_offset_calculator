package com.example.offsetcalculator.model.emission;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Simple POJO that simplifies the complexity of having all the emissions in one place and keeps track of a daily emission
 * @author Andre
 * @version 1.0
 * @since 1.0
 */
@Entity
public class EmissionTotal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dateCreated; //you can use .parse to parse a date object to a string
    private Double total;

    public EmissionTotal(String dateCreated, Double total) {
        this.dateCreated = dateCreated;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
