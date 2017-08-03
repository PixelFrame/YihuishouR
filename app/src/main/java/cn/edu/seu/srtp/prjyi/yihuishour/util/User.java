/*
 * Created by Pixel Frame on 2017/8/3.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

/**
 * Created by pm421 on 7/22/2017.
 * 用户类
 */

public class User {
    private int id;
    private String name;
    private String password;
    private int avatar;
    private Order order[];
    private Item shoppingList[];
    public User(){
    }
    protected void updateUser(){
    }
    public void setName(String str) { name = str; }

    public void setId(int i) { id = i; }

    public void setPassword(String str) { password = str; }
}
