package com.example.airatonline.filemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Collections;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Element[] myData;
    private Context context;
    private String path;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView imgType;
        private LinearLayout layout;

        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);
            imgType = v.findViewById(R.id.image);
            layout = v.findViewById(R.id.layout);
        }
    }

    public MyAdapter(Element[] myData, Context context, String path) {
        this.myData = myData;
        this.context = context;
        this.path = path;
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mTextView.setText(myData[position].text);
        holder.imgType.setImageDrawable(ContextCompat.getDrawable(context, myData[position].image));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAAA", String.valueOf(position));
                File file = new File(path + "/" + myData[position].text);
                if (file.isDirectory()) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("folder", path + "/" + myData[position].text);
                    Log.d("AAA", file.getAbsolutePath());
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return myData.length;
    }

}
