/*
 * Created by Pixel Frame on 2017/9/23.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader

class KotOrderDetailActivity : AppCompatActivity() {

    private val itemDetailImage: ImageView = findViewById(R.id.id_iv_itemDetImage) as ImageView
    private val imageLoader: ImageLoader = ImageLoader.getInstance()
    private val options: DisplayImageOptions = DisplayImageOptions.Builder().showStubImage(R.mipmap.image_loading)
            .showImageForEmptyUri(R.mipmap.image_loading)
            .showImageOnFail(R.mipmap.image_loading)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kot_order_detail)
        imageLoader.displayImage(_CONSTANTS.ServerURL+"img/testitem.png", itemDetailImage, options)
    }
}
