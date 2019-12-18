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
    private List<Watering> listWat;
    private OnItemListener mOnItemListener;

    public RecyclerViewAdopterW(Context mContext, List<Watering> data,OnItemListener onItemListener) {
        this.mContext = mContext;
        this.listWat = data;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_watering,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v, mOnItemListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imgWateringPlant.setImageURI(Uri.parse(listWat.get(position).getImage_plant()));
        Log.d("sui getplantimage "," is " + listWat.get(position).getImage_plant());

        holder.imgWater.setImageResource(listWat.get(position).getImage_water());
        holder.textName.setText(listWat.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listWat.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imgWateringPlant;
        private ImageView imgWater;
        private TextView textName;
        OnItemListener onItemListener;

        public MyViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            imgWateringPlant = (ImageView) itemView.findViewById(R.id.img_my_plant);
            imgWater = (ImageView) itemView.findViewById(R.id.blue_water);
            textName = (TextView)itemView.findViewById(R.id.text_name);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}