package com.example.airatonline.filemanager;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.io.File;

public class statusActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        TextView imgCount = findViewById(R.id.imgCount);
        String path = Environment.getExternalStorageDirectory() + "/DCIM/";
        Log.d("AAA", path);
        Log.d("AAA", String.valueOf(imgCountFun(path)));
        imgCount.setText(String.valueOf(imgCountFun(path)));


    }

    int imgCountFun(String path) {
        int res=0;
        File[] files = new File(path).listFiles();//Получили все файлы в DCIM
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                res+=imgCountFun(path+"/"+files[i].getName());
            }
            else{
                res++;
            }
        }
        return res;
    }
}
