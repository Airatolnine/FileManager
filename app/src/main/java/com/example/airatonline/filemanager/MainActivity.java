package com.example.airatonline.filemanager;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
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
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myManager;
    String path;
    Toolbar toolbar;
    LinearLayout navLayout;
    Context context = this;

    Element[] e;

    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            int colorFrom = Color.argb(255, 0, 0, 0);
            int colorTo = Color.argb(255, 0, 0, 255);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(10000); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    window.setStatusBarColor((int) animator.getAnimatedValue());
                    window.setNavigationBarColor((int) animator.getAnimatedValue());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        window.setNavigationBarDividerColor((int) animator.getAnimatedValue());
                    }
                }

            });
            colorAnimation.start();
        }

        int colorFrom = Color.argb(255, 0, 0, 255);
        int colorTo = Color.argb(50, 0, 0, 255);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(10000); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                toolbar.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        navLayout = findViewById(R.id.navLayout);
        setSupportActionBar(toolbar);
        ImageView home = new ImageView(getApplicationContext());
        home.setImageResource(R.drawable.ic_home_black_24dp);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("folder", path);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
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
        final String pathFromHome = path.replace(Environment.getExternalStorageDirectory().getAbsolutePath(), "");
        final String[] foldersFromHome = pathFromHome.split("/");
        View view = new View(getApplicationContext());
        view.setMinimumWidth(10);
        for (final String aFoldersFromHome : foldersFromHome) {
            final TextView textView = new TextView(getApplicationContext());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder path = new StringBuilder();
                    //int id = pathFromHome.indexOf(textView.getText().toString());
                    path.append(Environment.getExternalStorageDirectory().getAbsolutePath()).append("/");
                    int i = -1;
                    do {
                        i++;
                        path.append(foldersFromHome[i]);
                    } while (!foldersFromHome[i].equals(textView.getText().toString()));
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("folder", path.toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            });
            textView.setText(aFoldersFromHome);
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);

            //navLayout.addView(view);
            if (!textView.getText().equals("")) {
                navLayout.addView(imageView);
                navLayout.addView(textView);
            }

        }
        Log.d("AAAA", file.getAbsolutePath());
        File[] files = file.listFiles();
        if (files != null) {
            List<File> fileList = new ArrayList<>(Arrays.asList(files));
            Collections.sort(fileList);
            e = new Element[files.length];
            for (int i = 0; i < fileList.size(); i++) {
                Element tmp = new Element(fileList.get(i));
                e[i] = tmp;
            }
            Log.d("files", Arrays.toString(files));
            myAdapter = new MyAdapter(e, this, path, this);
            recyclerView.setAdapter(myAdapter);
        }

    }

    @Override
    public void onBackPressed() {
        try {
            String[] a = path.split("/");
            Log.d("path", path);
            StringBuilder newPath = new StringBuilder();
            for (int i = 0; i < a.length - 1; i++) {
                newPath.append(a[i]).append("/");
            }
            String at = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (newPath.toString().length() <= at.length()) {
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("folder", newPath.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }

        } catch (Exception e) {

        } finally {
            super.onBackPressed();
            finish();
        }


    }

}

class Integ {
    public int a;
    public Bitmap bitmap;

    Integ(int a) {
        this.a = a;
    }
}