package com.example.airatonline.filemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Element[] myData;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView imgType;

        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);
            imgType = v.findViewById(R.id.image);
        }
    }

    public MyAdapter(Element[] myData, Context context) {
        this.myData = myData;
        this.context = context;
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mTextView.setText(myData[position].text);
        holder.imgType.setImageDrawable(ContextCompat.getDrawable(context, myData[position].image));
    }

    @Override
    public int getItemCount() {
        return myData.length;
    }

}
