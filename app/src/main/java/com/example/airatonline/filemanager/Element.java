package com.example.airatonline.filemanager;

import android.graphics.Bitmap;
import java.io.File;

public class Element {
    public File file;
    public Bitmap image;

    public Element(File file, Bitmap image){
        this.file = file;
        this.image = image;
    }
}
