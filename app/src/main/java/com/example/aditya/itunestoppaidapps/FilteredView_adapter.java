package com.example.aditya.itunestoppaidapps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by aditya on 10/23/17.
 */

public class FilteredView_adapter extends RecyclerView.Adapter<FilteredView_adapter.ViewHolder> {

    public static ArrayList<App> mData;
    public static Context mContext;
    public static int pos;

    updateList activity;


    //0
    public FilteredView_adapter(ArrayList<App> mData, Context context, updateList activity) {
        this.mData = mData;
        mContext = context;
        this.activity = activity;


    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    //2
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filtered_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //3
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        App app = mData.get(position);
        Picasso.with(mContext).load(app.getImageDown()).into(holder.large_image);
        float price = Float.parseFloat(app.getPrice().toString().substring(1,app.getPrice().toString().length()));
        if (price >= 0&& price <2.00){
            holder.price_image.setImageResource(R.drawable.price_low);
        }

        else if (price >= 2.00&& price <6.00){
            holder.price_image.setImageResource(R.drawable.price_medium);
        }
        else if (price >= 6.00){
            holder.price_image.setImageResource(R.drawable.price_high);
        }

        holder.textViewName.setText(app.getName());
        holder.textViewPrice.setText(app.getPrice());
        holder.app = app;
    }

    //1
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice;
        ImageView large_image, price_image;
        ImageButton delete;
        App app;

        public ViewHolder(final View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.filtered_name);
            textViewPrice = (TextView) itemView.findViewById(R.id.filtered_price);
            large_image = (ImageView) itemView.findViewById(R.id.small_image);
            price_image = (ImageView) itemView.findViewById(R.id.price_image);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            pos = getAdapterPosition();

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseDataManager dm = new DatabaseDataManager(mContext);
                    dm.deleteNote(app);
                    activity.OnDeleteFilter(app);
                }
            });
        }




    }
    public interface updateList{
        void OnDeleteFilter(App app);

    }

}

