package com.example.oblig1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oblig1.domain.Cat;

import java.util.List;

/**
* This class is helping with providing a ListView over all the elements in the database
* It also creates CheckBoxes, which makes it possible to delete chosen cat-names and images
*
* The class is used in the database.java
 */

public class CustomAdapter extends ArrayAdapter<Cat> {
    private List<Cat> cats;
    Context context;

    // Boolean array for storing the state of the checkbox
    boolean[] checkBoxState;

    public CustomAdapter(List<Cat> cats, Context context) {
        super(context, R.layout.list_item, cats);
        this.cats = cats;
        this.context = context;
        checkBoxState = new boolean[cats.size()];
    }

    @Override
    public int getCount() {
        return cats.size();
    }

    @Override
    public Cat getItem(int position) {
        return cats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean[] getCheckBoxStates() {
        return checkBoxState;
    }

    private static class ViewHolder {
        TextView tv;
        ImageView img;
        CheckBox cb;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Cat cat = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            //cache the views
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textViewCatNameListItem);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.imageViewCatsListItem);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.checkBoxListItem);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //Set the data to be displayed
        viewHolder.tv.setText(cat.getName());
        viewHolder.img.setImageURI(Uri.parse(cat.getImage()));
        viewHolder.cb.setChecked(checkBoxState[position]);


        //Listens to changes
        viewHolder.cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                    checkBoxState[position]=true;
                else
                    checkBoxState[position]=false;

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
