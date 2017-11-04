package com.example.aditya.itunestoppaidapps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by aditya on 10/23/17.
 */


public class AppUtil {

    static class AppParsor {

        private static ArrayList<App> appList;
        App app;
        StringBuilder jsonTxt;


        static ArrayList<App> parseApps(String in) throws JSONException, IOException {
            appList = new ArrayList<App>();

            JSONObject root = new JSONObject(in);
            JSONObject feed = root.getJSONObject("feed");
                JSONArray entries = feed.getJSONArray("entry");

                String image,imageDown, name, price;

                for (int i = 0; i < entries.length(); i++) {


                    JSONObject entryObject = entries.getJSONObject(i);


                    name = entryObject.getJSONObject("im:name").getString("label");
                    image = entryObject.getJSONArray("im:image").getJSONObject(0).getString("label");
                    price = entryObject.getJSONObject("im:price").getString("label");
                    imageDown = entryObject.getJSONArray("im:image").getJSONObject(2).getString("label");




                    App dummy = new App(image, imageDown, name, price);
                    appList.add(dummy);


                }



            return appList;
        }
    }

}

