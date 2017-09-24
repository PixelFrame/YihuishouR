/*
 * Created by Pixel Frame on 2017/9/24.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

/**
 * Created by pm421 on 7/22/2017.
 * 物品类
 */

public class Item {
    private int id = 0;
    private String name = "NULL";
    private int num = -1;
    private int catagory = -1;
    private double price = 42;

    public void setId(int i) { this.id = i; }
    public void setName(String text) { this.name = text; }
    public void setNum(int i) { this.num = i;}
    public void setCatagory(int i) { this.catagory = i; }
    public void setPrice(double v) { this.price = v; }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getNum() { return num; }
    public int getCatagory() { return catagory; }
    public double getPrice() { return price; }
}
