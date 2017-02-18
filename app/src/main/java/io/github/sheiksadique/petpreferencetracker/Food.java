package io.github.sheiksadique.petpreferencetracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by sadique on 2/15/17.
 */
public class Food implements Parcelable, Serializable{
    private double preference;
    private int imgfile;
    private String name;

    public Food(String nm, double pref, int imf){
        preference = pref;
        imgfile = imf;
        name = nm;
    }

    public Food(Parcel in){
        preference = in.readDouble();
        imgfile = in.readInt();
        name = in.readString();
    }

    public void setPreference(double pref){
        preference = pref;
    }

    public double getPreference(){
        return preference;
    }

    public void setImgfile(int imf){
        imgfile = imf;
    }

    public int getImgfile(){
        return imgfile;
    }

    public void setName(String nm){
        name = nm;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(preference);
        dest.writeInt(imgfile);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Food> CREATOR
            = new Parcelable.Creator<Food>() {
        public Food createFromParcel(Parcel in){
            return new Food(in);
        }

        public Food[] newArray(int size){
            return new Food[size];
        }
    };
}

