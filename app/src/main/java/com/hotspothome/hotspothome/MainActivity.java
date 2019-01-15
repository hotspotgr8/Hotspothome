package com.hotspothome.hotspothome;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button btnOn, addMore, btnDis;
    SeekBar brightness;

    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    DataBaseHelperNew db;

    List<RoomModel>roomList ;
    LinearLayout nodatafoubd;
    Button addmOre;
    EditText roomName;
    EditText roomNumber;

    DatabaseReference databaseArtists;

    List<User> artists;
    private static final String TAG = MainActivity.class.getSimpleName();

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artists = new ArrayList<>();

        databaseArtists = FirebaseDatabase.getInstance().getReference("users");

        db   =new DataBaseHelperNew(this);
        btnOn = (Button)findViewById(R.id.StartOn);
        addMore = (Button)findViewById(R.id.addMore);
        brightness = (SeekBar)findViewById(R.id.seekBar);
        recyclerView = (RecyclerView) findViewById(R.id.roomList);
        nodatafoubd = (LinearLayout) findViewById(R.id.noDataFoundLinear);
        roomName =  findViewById(R.id.roomNameEnter);
        roomNumber = findViewById(R.id.roomNumberEnter);

        roomList = new ArrayList<>();




        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(mLayoutManager);



        recyclerView.setNestedScrollingEnabled(false);




       // setAdapter();

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String rame = roomName.getText().toString();
                String rnumber = roomNumber.getText().toString();

                if (rame != null && !"".equalsIgnoreCase(rame)&&rnumber != null && !"".equalsIgnoreCase(rnumber))
                {
                    RoomModel model = new RoomModel();
                    model.number = Integer.parseInt(rnumber);
                    model.RoomName = rame;
                    db.insertRoomData(model);
                    setAdapter();

                    roomName.getText().clear();
                    roomNumber.getText().clear();
                }else
                {
                    Toast.makeText(MainActivity.this,"Insert Proper value",Toast.LENGTH_SHORT).show();
                }
            }
        });




        addArtist();
    }

    private void setAdapter() {

        try {
            roomList = db.getRoomData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (roomList.size() > 0)
        {
            nodatafoubd.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            RoomAapter adapterf = new RoomAapter(this, roomList);
            recyclerView.setAdapter(adapterf);
        }else
        {
            nodatafoubd.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }


    }

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }


    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return  Formatter.formatIpAddress(ip);
    }


    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    User artist = postSnapshot.getValue(User.class);
                    //adding artist to the list
                    artists.add(artist);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addArtist() {
        //getting the values to save
        String name = "Bndu";
        String genre = "Date Vlaue";

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();

            //creating an Artist Object
            User artist = new User(id, 1, genre,"fa","ada","afa","afa","faa");

            //Saving the Artist
            databaseArtists.child(id).setValue(artist);

            //displaying a success toast
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

}
