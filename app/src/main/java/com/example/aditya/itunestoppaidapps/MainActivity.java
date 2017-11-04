package com.example.aditya.itunestoppaidapps;


import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements GetAppsAsyncTask.Idata, FilteredView_adapter.updateList {
    ArrayList<App> apps;
    ArrayList<App> filtered;
    ArrayList<App> favApps;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    static ListView toplist;
    ImageButton refresh;
    ProgressDialog loading;
    TextView label;
    TextView status;
    ListAdapter top;

    DatabaseDataManager dm;

    Switch sort;

    public static final String TAG = "ITunes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        toplist = (ListView)findViewById(R.id.topListView);
        sort = (Switch)findViewById(R.id.toggle);
        refresh = (ImageButton)findViewById(R.id.refresh);
        label = (TextView)findViewById(R.id.Order);
        status = (TextView)findViewById(R.id.status);
        apps = new ArrayList();
        filtered = new ArrayList();
        loading = new ProgressDialog(MainActivity.this);
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        String trackUrl= "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
        loading.show();
        try {
            new GetAppsAsyncTask(MainActivity.this).execute(trackUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        toplist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dm.saveNote(filtered.get(position));
                updateList();
                status.setText("Swipe Below");
                return false;
            }
        });

        sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    if (apps != null) {
                        Collections.sort(filtered, App.descComparator);
                        label.setText("Descending");
                    }
                    else{
                        Toast.makeText(MainActivity.this, "No results, Cannot be sorted!", Toast.LENGTH_SHORT).show();
                        label.setText("Descending");
                    }

                }
                else{
                    if (apps != null) {
                        Collections.sort(filtered, App.ascComparator);
                        label.setText("Ascending");
                    }
                    else{
                        Toast.makeText(MainActivity.this, "No results, Cannot be sorted!", Toast.LENGTH_SHORT).show();
                        label.setText("Ascending");
                    }
                }

                top.notifyDataSetChanged();

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trackUrl = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
                try {
                    loading.show();
                    new GetAppsAsyncTask(MainActivity.this).execute(trackUrl).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void updateList(){
        filtered = new ArrayList<>();
        favApps = (ArrayList<App>) dm.getAllNote();

        for (App app : apps) {
            if (!favApps.contains(app)){
                filtered.add(app);
            }
        }

        if (sort.isChecked()){
            Collections.sort(filtered, App.ascComparator);
        } else{
            Collections.sort(filtered, App.descComparator);
        }

        loading.dismiss();
        top = new ListAdapter(this, R.layout.listview, filtered);
        top.setNotifyOnChange(true);
        toplist.setAdapter(top);

        mAdapter = new FilteredView_adapter(favApps, MainActivity.this, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void setupData(ArrayList<App> result) {
        if (result != null) {
            apps = result;
            filtered = new ArrayList<>();
            favApps = (ArrayList<App>) dm.getAllNote();

            for (App app : apps) {
                if (!favApps.contains(app)){
                    filtered.add(app);
                }
            }

            sort.setChecked(true);
            label.setText("Ascending");

            Collections.sort(filtered, App.ascComparator);

            loading.dismiss();
            top = new ListAdapter(this, R.layout.listview, filtered);
            toplist.setAdapter(top);
            top.setNotifyOnChange(true);
        }
        else{
            loading.dismiss();
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        dm = new DatabaseDataManager(MainActivity.this);
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        favApps = (ArrayList<App>) dm.getAllNote();

            mAdapter = new FilteredView_adapter(favApps, MainActivity.this, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
        if (!dm.getAllNote().isEmpty()){
            status.setText("Swipe Below");
        }



    }

    @Override
    public void OnDeleteFilter(App app) {
        dm.deleteNote(app);

        updateList();
        if (dm.getAllNote().isEmpty()){
            status.setText("No Filtered Items");
        }
    }

}
