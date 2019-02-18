package com.example.airatonline.filemanager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myManager;
    String path;
    Toolbar toolbar;

    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();


        try{
            path = intent.getStringExtra("folder");
            Log.d("AAA|main", path);
        }catch (Exception e){
            path = null;
        }


        recyclerView = (RecyclerView) findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        myManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myManager);


        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    permissions,
                    1);
        } else {
            if(path!=null){
                fillActivity(path);
            }else{
                fillActivity(Environment.getExternalStorageDirectory().getAbsolutePath());
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if(path!=null){
                        fillActivity(path);
                    }else{
                        fillActivity(Environment.getExternalStorageDirectory().getAbsolutePath());
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void fillActivity(String path) {
        File file = new File(path);
        Log.d("AAAA", file.getAbsolutePath());
        File[] files = file.listFiles();
        if(files!=null){
            List<File> fileList = new ArrayList<>();
            fileList.addAll(Arrays.asList(files));
            Collections.sort(fileList);
            Element[] e = new Element[files.length];
            for (int i = 0; i < fileList.size(); i++) {
                Element tmp;
                if (fileList.get(i).isDirectory()) {
                    tmp = new Element(fileList.get(i).getName(), R.mipmap.ic_folder);
                } else {
                    tmp = new Element(fileList.get(i).getName(), R.mipmap.ic_file);
                }
                e[i] = tmp;
            }
            Log.d("files", Arrays.toString(files));
            myAdapter = new MyAdapter(e, this, path);
            recyclerView.setAdapter(myAdapter);
        }

    }
}
