package io.github.sheiksadique.petpreferencetracker;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.*;
import java.util.ArrayList;

public class AddNewFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);

    }

    public void addFoodName(View view){
        EditText et = (EditText) findViewById(R.id.newnamefield);
        String name = et.getText().toString();
        if (name != ""){
            ArrayList<Food> foods = loadDataFromFile(getString(R.string.datafile));
            Food fd = new Food(name, 50.0, R.drawable.turkey_giblets_pate);
            foods.add(fd);
            saveDataToFile(foods);
            NavUtils.navigateUpFromSameTask(this);
        } else {
            // Do nothing
        }
    }


    private void saveDataToFile(ArrayList<Food> fds){
        File datafile = getApplicationContext().getFileStreamPath(getString(R.string.datafile));
        Log.v("SaveFile", datafile.getAbsolutePath());
        // Finally write data to file
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try{
            fout = new FileOutputStream(datafile.getAbsoluteFile(), false);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(fds);
            oos.close();
            fout.close();
        } catch (Exception ex) {
            Log.v("SaveFile", ex.toString());
        }
    }

    private ArrayList<Food> loadDataFromFile(String fname){
        ArrayList<Food> flist = new ArrayList<>();
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream(getApplicationContext().getFileStreamPath(fname));
            objectinputstream = new ObjectInputStream(streamIn);
            flist = (ArrayList<Food>) objectinputstream.readObject();
            streamIn.close();
            objectinputstream.close();
            Log.v("LoadData", "Done loading from file");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flist;
    }

}
