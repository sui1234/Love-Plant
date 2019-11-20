package com.example.loveplant;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;

import java.util.List;

import static android.net.Uri.parse;

public class RecyclerViewAdopterP extends RecyclerView.Adapter<RecyclerViewAdopterP.MyViewHolder> {

    Context mContext;
    List<Plant> data;

    public RecyclerViewAdopterP(Context mContext, List<Plant> data) {
        this.mContext = mContext;
        this.data = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_my_plants,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //get imageUri and parse it.
        holder.imgMyPlant.setImageURI(Uri.parse(data.get(position).getImage()));

        //holder.imgMyPlant.setImageResource(R.drawable.tree);
        holder.plantName.setText(data.get(position).getName());
        holder.days.setText(data.get(position).getDays());
        holder.lastTime.setText(data.get(position).getWateringLastTime());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Plant getPlantAtPosition(int position){
        return data.get(position);
    }
    public void removeItem(int position){
        data.remove(position);
        notifyItemRemoved(position);

    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        private ImageView imgMyPlant;
        private TextView plantName;
        private TextView days;
        private TextView lastTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMyPlant = (ImageView) itemView.findViewById(R.id.img_my_plant);
            plantName = (TextView) itemView.findViewById(R.id.my_plant_name);
            days = (TextView) itemView.findViewById(R.id.my_plant_days);
            lastTime = (TextView) itemView.findViewById(R.id.watering_last_time);


        }
    }
}