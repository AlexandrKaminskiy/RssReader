package com.example.rssreader;

import com.example.rssreader.rss.Item;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;

public class DataFormatter {

    ArrayList<Item> items;

    public DataFormatter() {
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public boolean format(String s) {
        boolean status = true;
        boolean inItem = false;
        Item item = null;
        String value = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(s));
            int event = parser.getEventType();
            String currtag = "";
            while (event != XmlPullParser.END_DOCUMENT) {
                String tName = parser.getName();
                currtag = tName != null ? tName : currtag;
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tName)) {
                            inItem = true;
                            item = new Item();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        value = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inItem) {
                            if ("item".equalsIgnoreCase(tName)) {
                                inItem = false;
                                items.add(item);
                            }
                            else if ("title".equalsIgnoreCase(currtag) && item.getTitle() == null) {
                                item.setTitle(value);
                            }
                            else if ("description".equalsIgnoreCase(currtag) && item.getDescription() == null) {
                                item.setDescription(value);
                            }
                            else if ("creator".equalsIgnoreCase(currtag) && item.getAuthor() == null) {
                                item.setAuthor(value);
                            }
                            else if ("link".equalsIgnoreCase(currtag) && item.getLink() == null) {
                                item.setLink(value);
                            }
                            else if ("pubdate".equalsIgnoreCase(currtag) && item.getPubDate() == null) {
                                item.setPubDate(value);
                            }
                        }
                        break;
                }
                event = parser.next();

            }
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        items.forEach(System.out::println);
        return status;
    }

}
