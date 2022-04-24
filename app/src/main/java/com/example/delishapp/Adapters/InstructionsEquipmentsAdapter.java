package com.example.delishapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delishapp.R;
import com.example.delishapp.models.Equipment;
import com.example.delishapp.models.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsEquipmentsAdapter extends RecyclerView.Adapter<InstructionsEquipmentsViewHolder>{
    //objects
    Context context;
    List<Equipment> list;
    //constructor
    public InstructionsEquipmentsAdapter(Context context, List<Equipment> list) {
        this.context = context;
        this.list = list;
    }
    //methods
    @NonNull
    @Override
    public InstructionsEquipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //oncreate view holder
        return new InstructionsEquipmentsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsEquipmentsViewHolder holder, int position) {
        holder.textView_instructions_step_item.setText(list.get(position).name);
        //making textview selected
        holder.textView_instructions_step_item.setSelected(true);
        //image of equipment using API URL
        Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/"+list.get(position).image).into(holder.imageView_instructions_step_items);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
//viewholder class
class InstructionsEquipmentsViewHolder extends RecyclerView.ViewHolder {
    //objects according to xml file
    TextView textView_instructions_step_item;
    ImageView imageView_instructions_step_items;
    public InstructionsEquipmentsViewHolder(@NonNull View itemView) {
        super(itemView);
        //initialising views
        textView_instructions_step_item = itemView.findViewById(R.id.textView_instructions_step_item);
        imageView_instructions_step_items = itemView.findViewById(R.id.imageView_instructions_step_items);
    }
}