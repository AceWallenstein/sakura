package com.example.sakura.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.sakura.R;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.common.Constant;
import com.example.sakura.ui.fragment.AnimeTimeLineFragment;
import com.example.sakura.ui.fragment.HomeFragment;
import com.example.sakura.ui.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

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
        mSearch = findViewById(R.id.search);
        mSetting = findViewById(R.id.setting);
        navBottom = findViewById(R.id.nav_bottom);
        vpContent = findViewById(R.id.vp_content);
        initFragments();
        initViewPager();
        vpContent.setCurrentItem(0);
    }

    private void initFragments() {
        mFlist = new ArrayList<>();
        mFlist.add(new HomeFragment());
        mFlist.add(new AnimeTimeLineFragment());
        mFlist.add(new HomeFragment());
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
        mSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                // 执行发送消息等操作
                if (TextUtils.isEmpty(mSearch.getText().toString()))
                    return false;
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra(Constant.SEARCH_WORD, mSearch.getText()
                        .toString());
                startActivity(intent);
                return true;
            }
            return false;

        });
        mSetting.setOnClickListener((v) -> toast("进入设置"));
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
}

