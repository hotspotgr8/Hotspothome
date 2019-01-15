package com.hotspothome.hotspothome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddRoomctivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    DataBaseHelperNew db;

    List<BoardModel> roomList ;
    LinearLayout nodatafoubd;
    Button addmOre;
    EditText roomName;
    int statusSwich = 0;
    int numberRoom;
    SwitchCompat roomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roomctivity);

        roomName =  findViewById(R.id.roomNameEnter);
        roomNumber = findViewById(R.id.roomSwitch);
        addmOre = findViewById(R.id.addMore);
        recyclerView = findViewById(R.id.switchList);
        nodatafoubd = findViewById(R.id.noDataFoundLinear);

        if (getIntent() != null)
        {
            numberRoom = getIntent().getIntExtra("Number",0);
        }

        db = new DataBaseHelperNew(this);

        roomList= new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setNestedScrollingEnabled(false);


        setAdapter(numberRoom);


        roomNumber.setOnCheckedChangeListener(this);

        addmOre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rame = roomName.getText().toString();




                if (rame != null && !"".equalsIgnoreCase(rame))
                {
                    BoardModel model = new BoardModel();
                    model.status = statusSwich;
                    model.name = rame;
                    model.room_id = numberRoom;
                    db.insertSwitchData(model);
                    setAdapter(numberRoom);
                    roomName.getText().clear();
                }else
                {
                    Toast.makeText(AddRoomctivity.this,"Insert Proper value",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void setAdapter(int numberRoom) {

        try {
            roomList = db.getSwitchData(numberRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (roomList.size() > 0)
        {
            nodatafoubd.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            BoardApter adapterf = new BoardApter(this, roomList);
            recyclerView.setAdapter(adapterf);
        }else
        {
            nodatafoubd.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked)
        {
            statusSwich = 1;
        }else
        {
            statusSwich = 0;
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


