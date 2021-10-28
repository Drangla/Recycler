package com.example.recycler;

import static com.example.recycler.RESTHelperClass.postToHttpURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SHItems {

    private String name;
    private String state;
    private int color;
    private String link;

    public SHItems(String name, String state, int color) {
        this.name = name;
        this.state = state;
        this.color = color;
    }

    public SHItems(JSONObject entry){
        try {
            this.name = entry.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.state = entry.getString("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.link = entry.getString("link");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (this.state.equals("ON")) {
            this.color = R.color.teal_700;
        } else {
            this.color = R.color.cardview_dark_background;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void postCommad(String command) {
        CommandRunnable runnable = new CommandRunnable(this.link, command);
        new Thread(runnable).start();
    }

    class CommandRunnable implements Runnable {
        URL url;
        String command;

        CommandRunnable(String urlString, String command) {
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            this.command = command;
        }

        @Override
        public void run() {
            try {
                postToHttpURL(this.url, command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
