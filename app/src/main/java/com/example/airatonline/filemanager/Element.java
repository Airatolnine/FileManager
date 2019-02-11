package com.example.airatonline.filemanager;

public class Element {
    public String text;
    public int image;

    public Element(String text, int image){
        this.text = text;
        this.image = image;
    }
    public String getText(){
        return text;
    }
    public int getImage(){
        return image;
    }
}
