/*
 * Created by Pixel Frame on 2017/8/26.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.edu.seu.srtp.prjyi.yihuishour.R;

/**
 * Created by pm421 on 8/9/2017.
 */

public class ImageloaderGridAdapter extends BaseAdapter {
    private Context mContext;
    private final ImageLoader imageLoader;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.image_loading)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.image_loading)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.image_loading)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .bitmapConfig(Bitmap.Config.RGB_565)        // 设置图片的解码类型
            .build();                                   // 创建配置过得DisplayImageOption对象;

    final private String[] IMAGES = {
            _CONSTANTS.AvatarImgURL+"/1.png",
            _CONSTANTS.AvatarImgURL+"/2.png",
            _CONSTANTS.AvatarImgURL+"/3.png",
            _CONSTANTS.AvatarImgURL+"/4.png",
            _CONSTANTS.AvatarImgURL+"/5.png",
            _CONSTANTS.AvatarImgURL+"/6.png",
            _CONSTANTS.AvatarImgURL+"/7.png",
            _CONSTANTS.AvatarImgURL+"/8.png",
            _CONSTANTS.AvatarImgURL+"/9.png",
            _CONSTANTS.AvatarImgURL+"/10.png",
            _CONSTANTS.AvatarImgURL+"/11.png",
            _CONSTANTS.AvatarImgURL+"/12.png"
    };

    public ImageloaderGridAdapter(Context context) {
        mContext = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return IMAGES.length;
    }
    @Override
    public Object getItem(int position) {
        return IMAGES[position];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.grid_item_imageloader, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.grid_il_iv_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
         // 显示图片
        imageLoader.displayImage(IMAGES[position],holder.image,options);
        return convertView;
    }
    public  static  class  ViewHolder{
        public ImageView image;
    }

}
