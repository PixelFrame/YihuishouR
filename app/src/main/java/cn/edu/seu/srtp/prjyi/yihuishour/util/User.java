/*
 * Created by Pixel Frame on 2017/8/3.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pm421 on 7/22/2017.
 * 用户类
 */

public class User {
    private int id = 000000;
    private String name = "用户名";
    private String password = "";
    private int level = 0;
    private int avatar = 000000;
    private List<Order> orders = new ArrayList<>();
    private List<Item> shoppingList = new ArrayList<>();
    User(){}

    protected void updateUser(){
    }
    public void setName(String str) { name = str; }
    public void setId(int i) { id = i; }
    public void setPassword(String str) { password = str; }
    public void setLevel(int i) { level = i; }

    public String getName() { return name; }
    public int getLevel() { return level; }
}
