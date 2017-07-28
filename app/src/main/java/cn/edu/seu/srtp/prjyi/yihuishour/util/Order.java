/*
 * Created by Pixel Frame on 2017/7/28.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import java.util.ArrayList;

/**
 * Created by pm421 on 7/22/2017.
 * 订单类
 */

public class Order {
    private int id;
    private String alias;
    private ArrayList<Item> items;
    private int date;
    private int status;
    private int attrib;

    public void setId(int i) { this.id = i; }
    public void setAlias(String text) { this.alias = text; }
    public void setDate(int i) { this.date = i; }
    public void setStatus(int i) { this.status = i; }
    public void setAttrib(int i) { this.attrib = i; }
    public void newItem() { items.add(new Item()); }
    public void setItemId(int i) { items.get(items.size() - 1).setId(i); }
    public void setItemName(String text) { items.get(items.size() - 1).setName(text); }
    public void setItemNum(int i) { items.get(items.size() - 1).setNum(i);    }
    public void setItemCatagory(int i) { items.get(items.size() - 1).setCatagory(i); }
    public void setItemPrice(double v) { items.get(items.size() - 1).setPrice(v); }

    public int getId() { return id; }
    public String getAlias() { return alias; }
    public ArrayList<Item> getItems() { return items; }
    public int getDate() { return date; }
    public int getStatus() { return status; }
    public int getAttrib() { return attrib; }
}
