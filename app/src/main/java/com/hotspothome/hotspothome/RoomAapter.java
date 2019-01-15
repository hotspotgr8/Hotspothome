package com.hotspothome.hotspothome;

/**
 * Created by acer on 5/23/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;


import java.util.ArrayList;
import java.util.List;

public class RoomAapter extends RecyclerView.Adapter<RoomAapter.MyViewHolder> implements View.OnClickListener {

    List<RoomModel> imageList = new ArrayList<>();
    Context npContext;
    boolean quizHolder;

    public RoomAapter(Context deatilPageActivity, List<RoomModel> data) {

        this.npContext = deatilPageActivity;
        this.imageList = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_adapter_parent, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


      RoomModel media = imageList.get(position);

      holder.name.setText(media.RoomName);

      holder.number.setText("Rno : "+String.valueOf(media.number));

      holder.addmorebtton.setTag(position);
      holder.addmorebtton.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        try {
            return imageList.size();

        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();

        switch (v.getId())
        {
            case R.id.addBoard:

                Intent i = new Intent(npContext,AddRoomctivity.class);

                i.putExtra("Number",imageList.get(position).number);

                npContext.startActivity(i);

                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

    //    ImageView SwitchimageView;
        TextView name;
        TextView number;
        TextView addmorebtton;
      //  SwitchCompat switchStatus;


        public MyViewHolder(View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.roomName);
            number =  itemView.findViewById(R.id.roomNumber);
            addmorebtton =  itemView.findViewById(R.id.addBoard);
        }
    }
}