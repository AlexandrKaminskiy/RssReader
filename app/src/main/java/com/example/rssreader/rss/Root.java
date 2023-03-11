package com.example.rssreader.rss;

import com.example.rssreader.rss.Feed;
import com.example.rssreader.rss.Item;

import java.util.ArrayList;

public class Root {

    private String status;
    private Feed feed;
    private ArrayList<Item> items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}