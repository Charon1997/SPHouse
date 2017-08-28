package nexuslink.charon.sphouse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.ui.adapter.MainViewPagerAdapter;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private FloatingActionButton mFab;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TextView mTvName,mTvSub;
    private ImageView mIvHead;
    private List<View> list;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.main_fab:
//                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, EatActivity.class);
                startActivity(intent);

                break;
            case R.id.nav_header_imageView:
                showToast("image");
                break;
            case R.id.nav_header_text_name:
                showToast("name");
                break;
            case R.id.nav_header_text_sub:
                showToast("sub");
                break;
        }
    }

    @Override
    public void initParam(Bundle param) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        mToolbar = $(R.id.main_toolbar);
        mDrawer = $(R.id.drawer_layout);
        mFab = $  (R.id.main_fab);
        mNavigationView = $(R.id.nav_view);
        mViewPager = $(R.id.viewpager_main);
    }

    @Override
    public void setListener() {
        mFab.setOnClickListener(this);

    }

    @Override
    public void doBusiness(Context mContext) {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        mIvHead = (ImageView) headerView.findViewById(R.id.nav_header_imageView);
        mTvName = (TextView) headerView.findViewById(R.id.nav_header_text_name);
        mTvSub = (TextView) headerView.findViewById(R.id.nav_header_text_sub);
        mTvName.setOnClickListener(this);
        mTvSub.setOnClickListener(this);
        mIvHead.setOnClickListener(this);

        addView();
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(list);
        mViewPager.setAdapter(mainViewPagerAdapter);

    }

    private void addView() {
        //初始化list
        list = new ArrayList<>();
        View view = LayoutInflater.from(this).inflate(R.layout.viewpager_main, null);
        list.add(view);
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_header_imageView) {
            showToast("image");
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
