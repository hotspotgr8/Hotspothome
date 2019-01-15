package net.simplifiedcoding.firebasedatabaseexample;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Switch
{
    public String name;
    public String  aurdino_id;
    public int  switch_id;
    public String barcode;
    public String status;
    public String user_id;
    public String update_time;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Switch() {
    }

    public Switch(String name, int id, String status, String aurdino_id, String user_id, String update_time) {
        this.name = name;
        this.switch_id = id;
        this.status = status;
        this.aurdino_id = aurdino_id;
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

    public String getAurdino_id() {
        return aurdino_id;
    }

    public void setAurdino_id(String aurdino_id) {
        this.aurdino_id = aurdino_id;
    }

    public int getSwitch_id() {
        return switch_id;
    }

    public void setSwitch_id(int switch_id) {
        this.switch_id = switch_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
