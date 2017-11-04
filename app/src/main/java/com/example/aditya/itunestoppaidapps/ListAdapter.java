package com.example.aditya.itunestoppaidapps;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya on 10/23/17.
 */

public class ListAdapter extends ArrayAdapter<App> {

    Context mContext;
    public static ArrayList<App> dblist;
    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<App> objects) {
        super(context, resource, objects);
        mContext = context;
        dblist = new ArrayList<>();
        DatabaseDataManager dm = new DatabaseDataManager(mContext);
        dblist = (ArrayList<App>) dm.getAllNote();

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        App app = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.app_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.app_price);
            viewHolder.small_image = (ImageView) convertView.findViewById(R.id.small_image);
            viewHolder.price_image = (ImageView) convertView.findViewById(R.id.imageView3);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(app.getName());
        viewHolder.price.setText(app.getPrice());
        Picasso.with(mContext).load(app.getImage()).into(viewHolder.small_image);
        float price = Float.parseFloat(app.getPrice().toString().substring(1, app.getPrice().toString().length()));
        if (price >= 0 && price < 2.00) {
            viewHolder.price_image.setImageResource(R.drawable.price_low);
        } else if (price >= 2.00 && price < 6.00) {
            viewHolder.price_image.setImageResource(R.drawable.price_medium);
        } else if (price >= 6.00) {
            viewHolder.price_image.setImageResource(R.drawable.price_high);
        }



        return convertView;
    }


    private static class ViewHolder{
        TextView name;
        TextView price;
        ImageView small_image, price_image;
    }


}


