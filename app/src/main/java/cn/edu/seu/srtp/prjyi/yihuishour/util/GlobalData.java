/*
 * Created by Pixel Frame on 2017/8/5.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import android.app.Application;

/**
 * Created by pm421 on 8/3/2017.
 * 全局变量
 */

public class GlobalData extends Application {

    private User user;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public void setUser(User u) { this.user = u; }
    public User getUser() { return user; }

}
