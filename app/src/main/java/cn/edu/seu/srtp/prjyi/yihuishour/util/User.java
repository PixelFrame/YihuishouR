/*
 * Created by Pixel Frame on 2017/11/4.
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
    private int id = -1;
    private String name = "用户名";
    private String password = "";
    private int level = 0;
    private String avatar = null;
    public User(){}
    public User(int id, String name, int level){
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public void setName(String str) { name = str; }
    public void setId(int i) { id = i; }
    public void setPassword(String str) { password = str; }
    public void setLevel(int i) { level = i; }
    public void setAvatar(String ava) { avatar = ava; }

    public String getAvatar() { return avatar; }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getLevel() { return level; }
}
