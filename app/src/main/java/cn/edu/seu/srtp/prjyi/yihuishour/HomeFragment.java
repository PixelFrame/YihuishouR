/*
 * Created by Pixel Frame on 2017/7/28.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import cn.edu.seu.srtp.prjyi.yihuishour.util.AdDomain;
import cn.edu.seu.srtp.prjyi.yihuishour.util.MyGridAdapter;
import cn.edu.seu.srtp.prjyi.yihuishour.util.MyGridView;

/**
 * Created by pm421 on 7/15/2017.
 * 主页 包含顶部的Banner和分类的GridView
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    public static String IMAGE_CACHE_PATH = "imageloader/Cache";
    private ViewPager adViewPager;
    private List<ImageView> imageViews;
    private List<View> dots;
    private List<View> dotList;
    private TextView tv_date;
    private TextView tv_title;
    private TextView tv_topic_from;
    private TextView tv_topic;
    private int currentItem = 0;
    private View dot0;
    private View dot1;
    private View dot2;
    private View dot3;
    private View dot4;
    private ScheduledExecutorService scheduledExecutorService;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private List<AdDomain> adList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            adViewPager.setCurrentItem(currentItem);
        }
    };
    private MyGridView gridview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_tab, container, false);

        initImageLoader();
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()  
                .showStubImage(R.drawable.top_banner_android)  
                .showImageForEmptyUri(R.drawable.top_banner_android)  
                .showImageOnFail(R.drawable.top_banner_android)  
                .cacheInMemory(true).cacheOnDisc(true)  
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY).build();
        initAdData(view);
        startAd();
        initGridView(view);

        return view;
    }

    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getActivity().getApplicationContext(), IMAGE_CACHE_PATH);
        DisplayImageOptions defaultOptions = (new Builder()).cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = (new com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder(getActivity())).defaultDisplayImageOptions(defaultOptions).memoryCache(new LruMemoryCache(12582912)).memoryCacheSize(12582912).discCacheSize(33554432).discCacheFileCount(100).discCache(new UnlimitedDiscCache(cacheDir)).threadPriority(3).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    private void initAdData(View view) {
        adList = getBannerAd();
        imageViews = new ArrayList<>();
        dots = new ArrayList<>();
        dotList = new ArrayList<>();
        dot0 = view.findViewById(R.id.v_dot0);
        dot1 = view.findViewById(R.id.v_dot1);
        dot2 = view.findViewById(R.id.v_dot2);
        dot3 = view.findViewById(R.id.v_dot3);
        dot4 = view.findViewById(R.id.v_dot4);
        dots.add(dot0);
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
          
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_topic_from = (TextView) view.findViewById(R.id.tv_topic_from);
        tv_topic = (TextView) view.findViewById(R.id.tv_topic);
        adViewPager = (ViewPager) view.findViewById(R.id.vp);
        adViewPager.setAdapter(new MyAdapter());
        adViewPager.setOnPageChangeListener(new MyPageChangeListener());
        addDynamicView(); 
    }
    
    private void addDynamicView() {
        for (int i = 0; i < adList.size(); i++) {  
            ImageView imageView = new ImageView(getActivity());
            mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView,  
                                        options);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
            dots.get(i).setVisibility(View.VISIBLE);
            dotList.add(dots.get(i));
        }
    }
    
    private void startAd() {  
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,  
                                TimeUnit.SECONDS);
    }

    private class ScrollTask implements Runnable {
      @Override  
        public void run() {  
            synchronized (adViewPager) {  
                currentItem = (currentItem + 1) % imageViews.size();  
                handler.obtainMessage().sendToTarget();  
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();  
        // 当Activity不可见的时候停止切换  
        scheduledExecutorService.shutdown();  
    }  
     @Override
     public void onResume() {
        super.onResume();  
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition;

        private MyPageChangeListener() {
            oldPosition = 0;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            currentItem = position;
            AdDomain adDomain = adList.get(position);
            tv_title.setText(adDomain.getTitle());
            tv_date.setText(adDomain.getDate());
            tv_topic_from.setText(adDomain.getTopicFrom());
            tv_topic.setText(adDomain.getTopic());
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            this.oldPosition = position;
        }
    }

    private class MyAdapter extends PagerAdapter {
        private MyAdapter() {
        }

        public int getCount() {
            return adList.size();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = imageViews.get(position);
            container.addView(iv);
            AdDomain var10000 = adList.get(position);
            iv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                }
            });
            return iv;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager)arg0).removeView((View)arg2);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        public Parcelable saveState() {
            return null;
        }

        public void startUpdate(View arg0) {
        }

        public void finishUpdate(View arg0) {
        }
    }

    public static List<AdDomain> getBannerAd() {  
        List<AdDomain> adList = new ArrayList<>();
  
        AdDomain adDomain = new AdDomain();  
        adDomain.setId("108078");  
        adDomain.setDate("3月4日");  
        adDomain.setTitle("我和令计划只是同姓");  
        adDomain.setTopicFrom("阿宅");  
        adDomain.setTopic("我想知道令狐安和令计划有什么关系？");  
        adDomain.setImgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=bb99d6add2c8a786be2a4c0f5708c9c7/d50735fae6cd7b8900d74cd40c2442a7d9330e29.jpg");  
        adDomain.setAd(false);  
        adList.add(adDomain);  
  
        AdDomain adDomain2 = new AdDomain();  
        adDomain2.setId("108078");  
        adDomain2.setDate("3月5日");  
        adDomain2.setTitle("我和令计划只是同姓");  
        adDomain2.setTopicFrom("小巫");  
        adDomain2.setTopic("“我想知道令狐安和令计划有什么关系？”");  
        adDomain2  
                .setImgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=7cbcd7da78f40ad115e4c1e2672e1151/eaf81a4c510fd9f9a1edb58b262dd42a2934a45e.jpg");  
        adDomain2.setAd(false);  
        adList.add(adDomain2);  
  
        AdDomain adDomain3 = new AdDomain();  
        adDomain3.setId("108078");  
        adDomain3.setDate("3月6日");  
        adDomain3.setTitle("我和令计划只是同姓");  
        adDomain3.setTopicFrom("旭东");  
        adDomain3.setTopic("“我想知道令狐安和令计划有什么关系？”");  
        adDomain3  
                .setImgUrl("http://e.hiphotos.baidu.com/image/w%3D310/sign=392ce7f779899e51788e3c1572a6d990/8718367adab44aed22a58aeeb11c8701a08bfbd4.jpg");  
        adDomain3.setAd(false);  
        adList.add(adDomain3);  
  
        AdDomain adDomain4 = new AdDomain();  
        adDomain4.setId("108078");  
        adDomain4.setDate("3月7日");  
        adDomain4.setTitle("我和令计划只是同姓");  
        adDomain4.setTopicFrom("小软");  
        adDomain4.setTopic("“我想知道令狐安和令计划有什么关系？”");  
        adDomain4  
                .setImgUrl("http://d.hiphotos.baidu.com/image/w%3D310/sign=54884c82b78f8c54e3d3c32e0a282dee/a686c9177f3e670932e4cf9338c79f3df9dc55f2.jpg");  
        adDomain4.setAd(false);  
        adList.add(adDomain4);  
  
        AdDomain adDomain5 = new AdDomain();  
        adDomain5.setId("108078");  
        adDomain5.setDate("3月8日");  
        adDomain5.setTitle("我和令计划只是同姓");  
        adDomain5.setTopicFrom("大熊");  
        adDomain5.setTopic("“我想知道令狐安和令计划有什么关系？”");  
        adDomain5  
                .setImgUrl("http://e.hiphotos.baidu.com/image/w%3D310/sign=66270b4fe8c4b7453494b117fffd1e78/0bd162d9f2d3572c7dad11ba8913632762d0c30d.jpg");  
        adDomain5.setAd(true); // 代表是广告  
        adList.add(adDomain5);  
  
        return adList;  
    }

    private void initGridView(View view) {
        this.gridview = (MyGridView)view.findViewById(R.id.id_view_gridview);
        this.gridview.setAdapter(new MyGridAdapter(getContext()));
    }
}
