package com.example.airatonline.filemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.io.File;

public class statusActivity extends AppCompatActivity {

    GridView gridView;
    int imgCountInt = 0;
    int videoCountInt = 0;
    int musicCountInt = 0;
    Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        TextView imgCount = findViewById(R.id.imgCount);
        TextView videoView = findViewById(R.id.videoCount);
        ( findViewById(R.id.imgTap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        ( findViewById(R.id.videoTap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        String path = Environment.getExternalStorageDirectory() + "/";
        Log.d("AAA", path);
        countFun(path);
        imgCount.setText(String.valueOf(imgCountInt));
        videoView.setText(String.valueOf(videoCountInt));


    }


    void countFun(String path) {
        File[] files = new File(path).listFiles();//Получили все файлы в DCIM
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                countFun(path + "/" + files[i].getName());
            } else {
                String extension = files[i].getName().substring(files[i].getName().lastIndexOf('.') + 1);
                //Log.d("AAA", extension);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
                if (mimeType != null) {
                    String mimeElement = mimeType.split("/")[0];
                    if (mimeElement.equals("image")) {
                        imgCountInt++;
                    }
                    else if(mimeElement.equals("video")){
                        videoCountInt++;
                    }
                }
            }
        }
    }
}
