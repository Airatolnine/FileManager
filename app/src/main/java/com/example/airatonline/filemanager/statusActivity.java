package com.example.airatonline.filemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class statusActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        gridView = findViewById(R.id.gridView);
        String[] data = {"a", "b", "C", "d", "so many characters"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.statusitem, R.id.statusText, data);
        gridView.setAdapter(adapter);

    }
}
