/*
 * Created by Pixel Frame on 2017/7/28.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import com.amap.api.maps.model.LatLng;

/**
 * Created by pm421 on 7/28/2017.
 * 坐标点类
 */

public class LocationPoint {
    private LatLng latLng;
    private String poiName;
    private String poiDesc;
    private int pid;

    public LocationPoint(double var1, double var3, String name, String desc, int id) {
        latLng = new LatLng(var1, var3);
        poiName = name;
        poiDesc = desc;
        pid = id;
    }

    public LatLng getLatLng() { return latLng; }
    public String getPoiName() { return poiName; }
    public String getPoiDesc() { return poiDesc; }
    public int getPid() { return pid; }
}
