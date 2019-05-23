package com.example.airatonline.filemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class statusActivity extends AppCompatActivity {


    int imgCountInt = 0;
    int videoCountInt = 0;
    int musicCountInt = 0;
    int textCountInt = 0;
    int archiveCountInt = 0;
    int downloadCountInt = 0;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        TextView imgCount = findViewById(R.id.imgCount);
        TextView videoView = findViewById(R.id.videoCount);
        TextView musicView = findViewById(R.id.musicCount);
        TextView textView = findViewById(R.id.textCount);
        TextView arciveView = findViewById(R.id.archiveCount);
        TextView downloadView = findViewById(R.id.downloadCount);
        setDownloadCountInt();
        (findViewById(R.id.imgTap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        (findViewById(R.id.videoTap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        (findViewById(R.id.musicTap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        (findViewById(R.id.textTap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        (findViewById(R.id.archiveTap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        (findViewById(R.id.downloadTap)).setOnClickListener(new View.OnClickListener() {
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
        musicView.setText(String.valueOf(musicCountInt));
        textView.setText(String.valueOf(textCountInt));
        arciveView.setText(String.valueOf(archiveCountInt));
        downloadView.setText(String.valueOf(downloadCountInt));


    }


    void countFun(String path) {
        File[] files = new File(path).listFiles();//Получили все файлы в DCIM
        for (File file : files) {
            if (file.isDirectory() && file.getName().charAt(0) != '.' && !file.getName().equals("Android")) {
                countFun(path + "/" + file.getName());
            } else {
                String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
                if(extension.equals("rar")|| extension.equals("zip")){
                    archiveCountInt++;
                }



                //Log.d("AAA", extension);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
                if (mimeType != null) {
                    String mimeElement = mimeType.split("/")[0];
                    switch (mimeElement) {
                        case "image":
                            imgCountInt++;
                            break;
                        case "video":
                            videoCountInt++;
                            break;
                        case "audio":
                            musicCountInt++;
                            break;
                        case "text":
                            textCountInt++;
                            break;
                    }
                }
            }
        }
    }
    void setDownloadCountInt(){
        String path = Environment.getExternalStorageDirectory() + "/Download/";
        downloadCountInt = (new File(path).listFiles().length);
    }
}
