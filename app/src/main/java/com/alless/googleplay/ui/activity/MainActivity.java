package com.alless.googleplay.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.alless.googleplay.R;
import com.alless.googleplay.adapter.MainAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private String[] mTitles;
    private ActionBar mActionBar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mNavigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTitles = getResources().getStringArray(R.array.main_titles);
        initActionBar();
        initViewPager();
        checkStoragePermission();
    }

    private void checkStoragePermission() {
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            //没有写磁盘权限,申请
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new MainAdapter(mTitles, getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initActionBar() {
        setSupportActionBar(mToolBar);
        mActionBar = getSupportActionBar();
        //设置返回按钮
        mActionBar.setDisplayHomeAsUpEnabled(true);
        //drawerLayout 和 actionbar的联动
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        //同步状态
        mActionBarDrawerToggle.syncState();
        //设置监听器
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }


    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mActionBarDrawerToggle.onOptionsItemSelected(item);
                break;

        }

        return true;
    }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        /**
         *
         * @param item
         * @return 将选中的条目设为选中状态
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
        }
    };

    /**
     * 创建菜单选项
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //DownloadManager
                }
                break;
        }
    }
}
