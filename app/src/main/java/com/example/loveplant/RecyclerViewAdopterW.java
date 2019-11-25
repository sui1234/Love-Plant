package com.example.loveplant;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdopterW extends RecyclerView.Adapter<RecyclerViewAdopterW.MyViewHolder> {

    Context mContext;
    List<Watering> data;

    public RecyclerViewAdopterW(Context mContext, List<Watering> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_watering,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imgWateringPlant.setImageURI(Uri.parse(data.get(position).getImage_plant()));
        Log.d("sui getplantimage "," is " + data.get(position).getImage_plant());

        holder.imgWater.setImageResource(data.get(position).getImage_water());
        holder.textName.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        private ImageView imgWateringPlant;
        private ImageView imgWater;
        private TextView textName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgWateringPlant = (ImageView) itemView.findViewById(R.id.img_my_plant);
            imgWater = (ImageView) itemView.findViewById(R.id.blue_water);
            textName = (TextView)itemView.findViewById(R.id.text_name);
        }
    }
}
