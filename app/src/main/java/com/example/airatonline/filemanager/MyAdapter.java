package com.example.airatonline.filemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
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

import org.w3c.dom.Text;

import java.io.File;
import java.util.Collections;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Element[] myData;
    private Context context;
    private String path;
    private Activity activity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView imgType;
        private LinearLayout layout;
        private TextView additionText;

        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);
            imgType = v.findViewById(R.id.image);
            layout = v.findViewById(R.id.layout);
            additionText = v.findViewById(R.id.textIsEmpty);
        }
    }

    public MyAdapter(Element[] myData, Context context, String path, Activity activity) {
        this.myData = myData;
        this.context = context;
        this.path = path;
        this.activity = activity;
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mTextView.setText(myData[position].file.getName());
        holder.additionText.setTextColor(Color.argb(50,0,0,0));
        holder.additionText.setTextSize(12);
        if (myData[position].file.isDirectory()) {
            holder.imgType.setImageResource(R.drawable.ic_folder);
            if(myData[position].file.listFiles().length==0){
                holder.additionText.setText("Пустая папка");

            }
        } else {
            if(myData[position].file.length()<1000){
                holder.additionText.setText(String.valueOf(myData[position].file.length()) + " Б");
            }
            else if(myData[position].file.length()/1000<1000){
                holder.additionText.setText(String.valueOf(myData[position].file.length()/1000) + " КБ");
            }
            else if(myData[position].file.length()/1000/1000<1000){
                holder.additionText.setText(String.valueOf(myData[position].file.length()/1000/1000) + " МБ");
            }
            else if(myData[position].file.length()/1000/1000/1000<1000){
                holder.additionText.setText(String.valueOf(myData[position].file.length()/1000/1000/1000) + " ГБ");
            }
            switch (myData[position].file.getName().substring(myData[position].file.getName().lastIndexOf('.'))) {
                case ".jpg":
                    updateImage(holder.imgType, myData[position].file);
                    break;

                case ".jpeg":
                    updateImage(holder.imgType, myData[position].file);
                    break;

                case ".png":
                    updateImage(holder.imgType, myData[position].file);
                    break;
                case ".pdf":
                    holder.imgType.setImageResource(R.drawable.ic_pdf_file);
                    break;

                case ".apk":
                    holder.imgType.setImageResource(R.drawable.ic_apk_file);
                    break;

                default:
                    holder.imgType.setImageResource(R.drawable.ic_file);

            }
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAAA", String.valueOf(position));
                File file = new File(path + "/" + myData[position].file.getName());
                if (file.isDirectory()) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("folder", path + "/" + myData[position].file.getName());
                    Log.d("AAA", file.getAbsolutePath());
                    context.startActivity(intent);
                    activity.finish();
                }

            }
        });
    }

    void updateImage(final ImageView imageView, final File file) {
        class UpdloadTask extends AsyncTask<Void, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(Void... voids) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Log.d("AAA", "FileSize: " + String.valueOf(file.length()));
                if (file.length() > 100000) {
                    options.inSampleSize = 50;
                }
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                try {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100 * bitmap.getHeight() / bitmap.getWidth(), false);
                } catch (IllegalArgumentException e) {
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        }
        new UpdloadTask().execute();
    }

    @Override
    public int getItemCount() {
        return myData.length;
    }


}
