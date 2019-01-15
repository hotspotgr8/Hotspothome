package net.simplifiedcoding.firebasedatabaseexample;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String ARTIST_NAME = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    public static final String ARTIST_ID = "net.simplifiedcoding.firebasedatabaseexample.artistid";


    //a list to store all the artist from firebase database
    List<Artist> artists;


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

    //our database reference object
    DatabaseReference databaseArtists;
    DatabaseReference databaseArtistsForAurdino;
    DatabaseReference databaseArtistsForSwtich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("users");
        databaseArtistsForAurdino = FirebaseDatabase.getInstance().getReference("aurdinos");
        databaseArtistsForSwtich = FirebaseDatabase.getInstance().getReference("switchs");

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


        addArtist();


        setAdapter();

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



        //attaching listener to listview
      /*  recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Artist artist = artists.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistName());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        recyclerView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artists.get(i);
                showUpdateDeleteDialog(artist.getArtistId(), artist.getArtistName());
                return true;
            }
        });*/


    }

    private void showUpdateDeleteDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateArtist(artistId, name, genre);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(artistId);
                b.dismiss();
            }
        });
    }

    private boolean updateArtist(String id, String name, String genre) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);

        //updating artist
        Artist artist = new Artist(id, name, genre);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
//                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    artists.add(artist);
                }

               /* //creating adapter
                RoomAapter artistAdapter = new RoomAapter(MainActivity.this, artists);
                //attaching adapter to the listview
                recyclerView.setAdapter(artistAdapter);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /*
    * This method is saving a new artist to the
    * Firebase Realtime Database
    * */
    private void addArtist() {
        //getting the values to save
        String name = "Bindu";
        String genre = "Bindu";

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();
            String aurdinoId = databaseArtistsForAurdino.push().getKey();
            String switchId = databaseArtistsForSwtich.push().getKey();

            //creating an Artist Object
            User artist = new User(id, 1, genre,"fa","fa","fa","Fa","fa");
            Aurdino aurdino = new Aurdino(id, 1, genre,"fa","fa","fa");
            Switch aSwitch = new Switch(id, 1, genre,"fa","fa","fa");

            databaseArtists.child("8828376477").setValue(artist);
            databaseArtistsForAurdino.child(aurdinoId).setValue(artist);
            databaseArtistsForSwtich.child(switchId).setValue(artist);

            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
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
}
