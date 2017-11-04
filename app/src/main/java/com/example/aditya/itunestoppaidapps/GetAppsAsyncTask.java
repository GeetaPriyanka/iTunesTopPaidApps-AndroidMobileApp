package com.example.aditya.itunestoppaidapps;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by aditya on 10/23/17.
 */

public class GetAppsAsyncTask extends AsyncTask<String,Void,ArrayList<App>> {

    Idata activity;
    public static ArrayList<App> apps;

    public GetAppsAsyncTask(Idata activity) {
        this.activity = activity;
    }
    @Override
    protected ArrayList<App> doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int StatusCode = con.getResponseCode();
            if(StatusCode == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line = bufferedreader.readLine();
                while(line!= null){
                    sb.append(line);
                    line = bufferedreader.readLine();
                }
                apps = AppUtil.AppParsor.parseApps(sb.toString());

                return apps;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<App> recipes){

        activity.setupData(recipes);
    }

    static public interface Idata{
        public void setupData(ArrayList<App> result);

    }
}
