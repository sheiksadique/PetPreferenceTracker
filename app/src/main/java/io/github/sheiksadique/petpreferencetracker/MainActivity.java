package io.github.sheiksadique.petpreferencetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Food> foods = new ArrayList<Food>();
    private String dataFileName;


    private void initData(){
        foods = loadDataFromFile(dataFileName);
        if (foods.size() == 0) {
            // TODO: The data should be a resource and not initialized in the activity
            foods.add(new Food("Pate", 0.5*100, R.drawable.turkey_giblets_pate));
            foods.add(new Food("Shreds", 0.2*100, R.drawable.turkey_giblets_pate));
            foods.add(new Food("Something", 0.7*100, R.drawable.turkey_giblets_pate));
            foods.add(new Food("Else", 0.7*100, R.drawable.turkey_giblets_pate));
            foods.add(new Food("And so on", 0.7*100, R.drawable.turkey_giblets_pate));
            Log.v("InitData", "Adding sample data");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataFileName = getString(R.string.datafile);
        setContentView(R.layout.activity_main);

        // Loading saved data
        Log.v("Main Activity", "On create called");
        if (savedInstanceState == null){
            Log.v("Main Activity", "No saved state sent");
            initData();
        } else {
            Log.v("MainActivity", savedInstanceState.toString());
            ArrayList<Food> x = savedInstanceState.getParcelableArrayList("foods");
            if (x == null) {
                Log.v("Main Activity", "No data found");
                initData();
            } else {
                foods = x;
            }
        }

        Spinner spinner = (Spinner) findViewById(R.id.foodspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Food> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, foods);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //        R.array.foodnames, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        ListView lv = (ListView) findViewById(R.id.listpreferenceview);
        FoodArrayAdapter foodArrayAdapter = new FoodArrayAdapter(this, foods);
        lv.setAdapter(foodArrayAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuitem_add_new){
            Intent addActIntent = new Intent(this, AddNewFoodActivity.class);
            startActivity(addActIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    public void updateRecords(View view){
        // Update data
        Spinner spinner = (Spinner) findViewById(R.id.foodspinner);
        Food fd = foods.get(spinner.getSelectedItemPosition());
        fd.setPreference((int) (fd.getPreference()+10));

        // Update list view
        ListView lv = (ListView) findViewById(R.id.listpreferenceview);
        ArrayAdapter foodArrayAdapter = (ArrayAdapter) lv.getAdapter();
        foodArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataToFile();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("foods", foods);
    }

    private void saveDataToFile(){
        File datafile = getApplicationContext().getFileStreamPath(dataFileName);
        Log.v("SaveFile", datafile.getAbsolutePath());
        // Finally write data to file
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try{
            fout = new FileOutputStream(datafile.getAbsoluteFile(), false);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(foods);
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
            FileInputStream  streamIn = new FileInputStream(getApplicationContext().getFileStreamPath(fname));
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
