package com.example.timer.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        Button fruitBtn;
        public ViewHolder(View view) {
            super(view);
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            fruitBtn = (Button) view.findViewById(R.id.fruit_btn);
        }

    }

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        //view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
        if(fruit.getBtnName()==0) {
            holder.fruitBtn.setText("打卡");
        }
        else{
            holder.fruitBtn.setText("已打卡");
        }
        if(mOnItemClickListen != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListen.onClick(position);
                }
            });

            holder.fruitBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {mOnItemClickListen.btnClick(position);}
            });

        }
    }


    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    private OnRecyclerViewItemClickListener mOnItemClickListen = null;
    public void setOnItemClickListen(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListen = listener;
    }
    public interface OnRecyclerViewItemClickListener {
        void onClick(int position);
        void btnClick(int position);
    }


}