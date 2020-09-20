package com.example.sakura.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.sakura.R;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.common.Constant;
import com.example.sakura.ui.fragment.AnimeTimeLineFragment;
import com.example.sakura.ui.fragment.DownloadFragment;
import com.example.sakura.ui.fragment.HomeFragment;
import com.example.sakura.ui.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseMvpActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private EditText mSearch;
    private ImageView mSetting;
    private BottomNavigationView navBottom;
    private ViewPager vpContent;
    private List<Fragment> mFlist;

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        requestPer();
        mSearch = findViewById(R.id.search);
        mSetting = findViewById(R.id.setting);
        navBottom = findViewById(R.id.nav_bottom);
        vpContent = findViewById(R.id.vp_content);
        initFragments();
        initViewPager();
        vpContent.setCurrentItem(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent().getIntExtra(Constant.TODOWNLOADFRAGMENT,0)==1){
            vpContent.setCurrentItem(2);
            navBottom.setSelectedItemId(R.id.menu_bookshelf);

        }
    }

    private void initFragments() {
        mFlist = new ArrayList<>();
        mFlist.add(new HomeFragment());
        mFlist.add(new AnimeTimeLineFragment());
        mFlist.add(new DownloadFragment());
        mFlist.add(new MineFragment());
    }

    private void initViewPager() {
        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFlist.get(position);
            }

            @Override
            public int getCount() {
                return mFlist.size();
            }
        });
        vpContent.setOffscreenPageLimit(3);
    }


    @Override
    protected void initListener() {
        super.initListener();
        navBottom.setOnNavigationItemSelectedListener(this);

        vpContent.addOnPageChangeListener(this);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_homepage:
                vpContent.setCurrentItem(0);
                return true;
            case R.id.menu_ani:
                vpContent.setCurrentItem(1);
                return true;
            case R.id.menu_bookshelf:
                vpContent.setCurrentItem(2);
                return true;
            case R.id.menu_mine:
                vpContent.setCurrentItem(3);
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                navBottom.setSelectedItemId(R.id.menu_homepage);
                break;
            case 1:
                navBottom.setSelectedItemId(R.id.menu_ani);
                break;
            case 2:
                navBottom.setSelectedItemId(R.id.menu_bookshelf);
                break;
            case 3:
                navBottom.setSelectedItemId(R.id.menu_mine);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void requestPer() {//RxPermissions获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe((isGranted)->{
                    if(isGranted){
                    }else {
                        toast("未授权权限，部分功能不能使用！");
                    }
        });
    }
}

