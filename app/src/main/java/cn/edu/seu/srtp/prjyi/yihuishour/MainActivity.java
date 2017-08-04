/*
 * Created by Pixel Frame on 2017/8/5.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;
    //四个Tab对应的布局
    private LinearLayout mTabHome;
    private LinearLayout mTabCart;
    private LinearLayout mTabOrder;
    private LinearLayout mTabMap;
    private LinearLayout mTabSetting;
    //四个Tab对应的ImageButton
    private ImageButton mImgHome;
    private ImageButton mImgCart;
    private ImageButton mImgOrder;
    private ImageButton mImgMap;
    private ImageButton mImgSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (v.getId()) {
            case R.id.id_tab_home:
                selectTab(0);
                break;
            case R.id.id_tab_cart:
                selectTab(1);
                break;
            case R.id.id_tab_order:
                selectTab(2);
                break;
            case R.id.id_tab_map:
                selectTab(3);
                break;
            case R.id.id_tab_setting:
                selectTab(4);
                break;
        }

    }

    private void initDatas() {
        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        mFragments.add(new HomeFragment());
        mFragments.add(new CartFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new AmapMapFragment());
        mFragments.add(new SettingFragment());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置四个Tab的点击事件
        mTabHome.setOnClickListener(this);
        mTabCart.setOnClickListener(this);
        mTabOrder.setOnClickListener(this);
        mTabMap.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);

    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabHome = (LinearLayout) findViewById(R.id.id_tab_home);
        mTabCart = (LinearLayout) findViewById(R.id.id_tab_cart);
        mTabMap = (LinearLayout) findViewById(R.id.id_tab_map);
        mTabSetting = (LinearLayout) findViewById(R.id.id_tab_setting);
        mTabOrder = (LinearLayout) findViewById(R.id.id_tab_order);

        mImgHome = (ImageButton) findViewById(R.id.id_tab_home_img);
        mImgCart = (ImageButton) findViewById(R.id.id_tab_cart_img);
        mImgMap = (ImageButton) findViewById(R.id.id_tab_map_img);
        mImgSetting = (ImageButton) findViewById(R.id.id_tab_setting_img);
        mImgOrder = (ImageButton) findViewById(R.id.id_tab_order_img);
    }
    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                mImgHome.setImageResource(R.mipmap.home_pressed);
                break;
            case 1:
                mImgCart.setImageResource(R.mipmap.cart_pressed);
                break;
            case 2:
                mImgOrder.setImageResource(R.mipmap.order_pressed);
                break;
            case 3:
                mImgMap.setImageResource(R.mipmap.map_pressed);
                break;
            case 4:
                mImgSetting.setImageResource(R.mipmap.setting_pressed);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mImgHome.setImageResource(R.mipmap.home_unpressed);
        mImgCart.setImageResource(R.mipmap.cart_unpressed);
        mImgMap.setImageResource(R.mipmap.map_unpressed);
        mImgSetting.setImageResource(R.mipmap.setting_unpressed);
        mImgOrder.setImageResource(R.mipmap.order_unpressed);
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
