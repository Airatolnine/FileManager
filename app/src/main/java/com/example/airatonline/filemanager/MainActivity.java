package com.example.airatonline.filemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    LinearLayout navLayout;

    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navLayout = findViewById(R.id.navLayout);
        setSupportActionBar(toolbar);
        ImageView home = new ImageView(getApplicationContext());
        home.setImageResource(R.drawable.ic_home_black_24dp);
        navLayout.addView(home);

        Intent intent = getIntent();


        try {
            path = intent.getStringExtra("folder");
            Log.d("AAA|main", path);
        } catch (Exception e) {
            path = null;
        }


        recyclerView = findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        myManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myManager);


        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    permissions,
                    1);
        } else {
            if (path != null) {
                fillActivity(path);
            } else {
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
                    if (path != null) {
                        fillActivity(path);
                    } else {
                        fillActivity(Environment.getExternalStorageDirectory().getAbsolutePath());
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
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
        String pathFromHome = path.replace(Environment.getExternalStorageDirectory().getAbsolutePath(), "");
        String[] foldersFromHome = pathFromHome.split("/");
        View view = new View(getApplicationContext());
        view.setMinimumWidth(10);
        for (String aFoldersFromHome : foldersFromHome) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(aFoldersFromHome);
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);


            //navLayout.addView(view);
            navLayout.addView(textView);
            navLayout.addView(imageView);
        }
        Log.d("AAAA", file.getAbsolutePath());
        File[] files = file.listFiles();
        if (files != null) {
            List<File> fileList = new ArrayList<>(Arrays.asList(files));
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
            myAdapter = new MyAdapter(e, this, path, this);
            recyclerView.setAdapter(myAdapter);
        }

    }

    @Override
    public void onBackPressed() {

        String[] a = path.split("/");
        Log.d("path", path);
        StringBuilder newPath = new StringBuilder();
        for (int i = 0; i < a.length - 1; i++) {
            newPath.append(a[i]).append("/");
        }

        try {
            String at = Environment.getExternalStorageDirectory().getAbsolutePath();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("folder", newPath.toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } finally {
            super.onBackPressed();
            finish();
        }


    }
}
