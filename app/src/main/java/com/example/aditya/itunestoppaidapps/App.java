package com.example.aditya.itunestoppaidapps;

import java.util.Comparator;

/**
 * Created by aditya on 10/23/17.
 */

public class App {

    private String image, imageDown, name, price;





    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "App{" +
                "image='" + image + '\'' +
                ", imageDown='" + imageDown + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (!image.equals(app.image)) return false;
        if (!imageDown.equals(app.imageDown)) return false;
        if (!name.equals(app.name)) return false;
        return price.equals(app.price);

    }

    @Override
    public int hashCode() {
        int result = image.hashCode();
        result = 31 * result + imageDown.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    public String getImageDown() {

        return imageDown;
    }

    public void setImageDown(String imageDown) {
        this.imageDown = imageDown;
    }

    public App() {
    }

    public App(String image, String imageDown, String name, String price) {

        this.image = image;
        this.name = name;
        this.price = price;
        this.imageDown = imageDown;
    }

    public static Comparator<App> ascComparator = new Comparator<App>() {

        public int compare(App s1, App s2) {
            float price1 = Float.parseFloat(s1.getPrice().toString().substring(1,s1.getPrice().toString().length()));
            float price2 = Float.parseFloat(s2.getPrice().toString().substring(1,s2.getPrice().toString().length()));


            return Float.compare(price1, price2);
        }
    };
    public static Comparator<App> descComparator = new Comparator<App>() {

        public int compare(App s1, App s2) {
            float price1 = Float.parseFloat(s1.getPrice().toString().substring(1,s1.getPrice().toString().length()));
            float price2 = Float.parseFloat(s2.getPrice().toString().substring(1,s2.getPrice().toString().length()));


            return Float.compare(price2, price1);
        }
    };


}
