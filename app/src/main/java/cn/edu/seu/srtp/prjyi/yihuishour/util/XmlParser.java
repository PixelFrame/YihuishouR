/*
 * Created by Pixel Frame on 2017/8/5.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by pm421 on 7/27/2017.
 * XML解析/生成类 using XmlPullParser
 */

public class XmlParser {
    public static List<Order> parse_order(XmlPullParser parser) throws Exception {
        List<Order> orders = null;
        Order order = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    orders = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("order")) {
                        order = new Order();
                    } else if (parser.getName().equals("oid")) {
                        eventType = parser.next();
                        order.setId(Integer.parseInt(parser.getText()));
                    } else if (parser.getName().equals("alias")) {
                        eventType = parser.next();
                        order.setAlias(parser.getText());
                    } else if (parser.getName().equals("item")) {
                        order.newItem();
                    } else if(parser.getName().equals("iid")) {
                        eventType = parser.next();
                        order.setItemId(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals("name")) {
                        eventType = parser.next();
                        order.setItemName(parser.getText());
                    } else if(parser.getName().equals("num")) {
                        eventType = parser.next();
                        order.setItemNum(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals("catagory")) {
                        eventType = parser.next();
                        order.setItemCatagory(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals("price")) {
                        eventType = parser.next();
                        order.setItemPrice(Double.parseDouble(parser.getText()));
                    } else if(parser.getName().equals("date")) {
                        eventType = parser.next();
                        order.setDate(Long.parseLong(parser.getText()));
                    } else if(parser.getName().equals("status")) {
                        eventType = parser.next();
                        order.setStatus(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals("attrib")) {
                        eventType = parser.next();
                        order.setAttrib(Integer.parseInt(parser.getText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("order")){
                        orders.add(order);
                        order = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return orders;
    }
    public static String serialize_order(List<Order> orders) throws Exception{
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "orders");
        for(Order order : orders) {
            serializer.startTag("", "order");

            serializer.startTag("","oid");
            serializer.text(order.getId()+"");
            serializer.endTag("","oid");

            serializer.startTag("","alias");
            serializer.text(order.getAlias());
            serializer.endTag("","alias");

            for(Item item : order.getItems()) {
                serializer.startTag("", "item");

                serializer.startTag("", "iid");
                serializer.text(item.getId() + "");
                serializer.endTag("", "iid");

                serializer.startTag("", "name");
                serializer.text(item.getName());
                serializer.endTag("", "name");

                serializer.startTag("", "num");
                serializer.text(item.getNum() + "");
                serializer.endTag("", "num");

                serializer.startTag("","catagory");
                serializer.text(item.getCatagory()+"");
                serializer.endTag("","catagory");

                serializer.startTag("", "price");
                serializer.text(item.getPrice() + "");
                serializer.endTag("","price");

                serializer.endTag("", "item");
            }

            serializer.startTag("","date");
            serializer.text(order.getDate() + "");
            serializer.endTag("","date");

            serializer.startTag("","status");
            serializer.text(order.getStatus() + "");
            serializer.endTag("","status");

            serializer.startTag("","attrib");
            serializer.text(order.getAttrib() + "");
            serializer.endTag("","attrib");

            serializer.endTag("","order");
        }
        serializer.endTag("","orders");
        serializer.endDocument();

        return writer.toString();
    }
    public static List<Item> parse_item(XmlPullParser parser) throws Exception {
        List<Item> items = null;
        Item item = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    items = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("item")) {
                        item = new Item();
                    } else if(parser.getName().equals("iid")) {
                        eventType = parser.next();
                        item.setId(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals("name")) {
                        eventType = parser.next();
                        item.setName(parser.getText());
                    } else if(parser.getName().equals("num")) {
                        eventType = parser.next();
                        item.setNum(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals("catagory")) {
                        eventType = parser.next();
                        item.setCatagory(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals("price")) {
                        eventType = parser.next();
                        item.setPrice(Double.parseDouble(parser.getText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")){
                        items.add(item);
                        item = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return items;
    }
    public static String serialize_item(List<Item> items) throws Exception{
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "items");
        for(Item item : items) {
            serializer.startTag("", "item");

            serializer.startTag("", "iid");
            serializer.text(item.getId() + "");
            serializer.endTag("", "iid");

            serializer.startTag("", "name");
            serializer.text(item.getName());
            serializer.endTag("", "name");

            serializer.startTag("", "num");
            serializer.text(item.getNum() + "");
            serializer.endTag("", "num");

            serializer.startTag("","catagory");
            serializer.text(item.getCatagory()+"");
            serializer.endTag("","catagory");

            serializer.startTag("", "price");
            serializer.text(item.getPrice() + "");
            serializer.endTag("","price");

            serializer.endTag("", "item");

            serializer.endTag("","item");
        }
        serializer.endTag("","items");
        serializer.endDocument();

        return writer.toString();
    }
    public static List<LocationPoint> parse_location(XmlPullParser parser) throws Exception {
        List<LocationPoint> locationPoints = null;
        LocationPoint locationPoint = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    locationPoints = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equalsIgnoreCase("locationPoint")) {
                        locationPoint = new LocationPoint(
                                Double.parseDouble(parser.getAttributeValue(0)),
                                Double.parseDouble(parser.getAttributeValue(1)), 
                                parser.getAttributeValue(2),
                                parser.getAttributeValue(3),
                                Integer.parseInt(parser.getAttributeValue(4)));
                        locationPoints.add(locationPoint);
                        locationPoint = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return locationPoints;
    }
    public static User parse_user(String xmlString) throws Exception{
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(xmlString));
        User user = null;
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    user = new User();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("id")){
                        eventType = parser.next();
                        user.setId(Integer.parseInt(parser.getText()));
                    } else if (parser.getName().equals("username")) {
                        eventType = parser.next();
                        user.setName(parser.getText());
                    } else if (parser.getName().equals("password")) {
                        eventType = parser.next();
                        user.setPassword(parser.getText());
                    } else if (parser.getName().equals("level")) {
                        eventType = parser.next();
                        user.setLevel(Integer.parseInt(parser.getText()));
                    } else if (parser.getName().equals("avatar")) {
                        eventType = parser.next();
                        user.setAvatar(parser.getText());
                    }
                    break;
            }
            eventType = parser.next();
        }
        return user;
    }
}
