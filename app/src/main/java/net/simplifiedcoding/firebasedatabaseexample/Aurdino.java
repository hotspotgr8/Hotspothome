package net.simplifiedcoding.firebasedatabaseexample;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Aurdino
{
    public String name;
    public int  aurdino_id;
    public String barcode;
    public String place;
    public String user_id;
    public String update_time;

    public Aurdino()
    {
    }

    public Aurdino(String name, int id, String barcode, String place, String user_id, String update_time) {
        this.name = name;
        this.aurdino_id = id;
        this.barcode = barcode;
        this.place = place;
        this.update_time = update_time;
        this.user_id = user_id;
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAurdino_id() {
        return aurdino_id;
    }

    public void setAurdino_id(int id) {
        this.aurdino_id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
