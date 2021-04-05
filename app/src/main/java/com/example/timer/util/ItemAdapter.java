package com.example.timer.util;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timer.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> mItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        public ViewHolder(View view) {
            super(view);
            itemImage = (ImageView) view.findViewById(R.id.fruit_image);
            itemName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    public ItemAdapter(List<Item> itemList) {
        mItemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        //view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item item = mItemList.get(position);
        holder.itemImage.setImageResource(item.getImageId());
        holder.itemName.setText(item.getName());
    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}