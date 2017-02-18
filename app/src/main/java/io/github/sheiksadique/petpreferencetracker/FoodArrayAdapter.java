package io.github.sheiksadique.petpreferencetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sadique on 2/15/17.
 */
public class FoodArrayAdapter extends ArrayAdapter<Food> {

    public FoodArrayAdapter(Context context, ArrayList<Food> foods){
        super(context, 0, foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Generate a view
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.preference_bar, null);
        }

        // Get the item in question
        Food fd = (Food) getItem(position);

        // Update progress bar
        ProgressBar pb = (ProgressBar) convertView.findViewById(R.id.progressbar);
        pb.setProgress((int) fd.getPreference());

        // Update image
        ImageView iv = (ImageView) convertView.findViewById(R.id.food_image);
        iv.setImageResource(fd.getImgfile());

        // Update Name
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(fd.getName());
        return convertView;
    }
}
