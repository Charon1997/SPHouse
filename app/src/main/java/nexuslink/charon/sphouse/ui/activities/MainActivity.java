package nexuslink.charon.sphouse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.bean.Dog;
import nexuslink.charon.sphouse.config.ObservableScrollView;
import nexuslink.charon.sphouse.config.ScrollViewListener;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.DogPresenter;
import nexuslink.charon.sphouse.ui.adapter.MainViewPagerAdapter;
import nexuslink.charon.sphouse.view.IMainView;
import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;

import static nexuslink.charon.sphouse.config.Constant.*;

/**
 * 图表有问题，可以改进
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScrollViewListener, IMainView {
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private FloatingActionButton mFab;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TextView mTvNavName, mTvNavSub, mTvDogName, mTvDogAge, mTvDogSex, mTvDogWeight;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private ImageView mIvNavHead;
    private List<View> list;
    private SuitLines mSlWeight, mSlTemperature;
    private ObservableScrollView scrollView;
    private boolean isFabOut = false;

    private DogPresenter presenter;
    //临时数据
    private List<Dog> dogList = new ArrayList<>();


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.main_fab:
                eat();
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
    public void initSession(Session session) {

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
        mFab = $(R.id.main_fab);
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
        findNavId(headerView);

        mTvNavName.setOnClickListener(this);
        mTvNavSub.setOnClickListener(this);
        mIvNavHead.setOnClickListener(this);

        addView();


    }

    private void findNavId(View headerView) {
        mIvNavHead = (ImageView) headerView.findViewById(R.id.nav_header_imageView);
        mTvNavName = (TextView) headerView.findViewById(R.id.nav_header_text_name);
        mTvNavSub = (TextView) headerView.findViewById(R.id.nav_header_text_sub);
    }

    private Date initDate(int year, int month, int day) {
        String dateString = year + "-" + month + "-" + day;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void addView() {


        //初始化list
        list = new ArrayList<>();
        View view = LayoutInflater.from(this).inflate(R.layout.viewpager_main, null);

        //findViewpagerId(view);

        list.add(view);

        View view1 = LayoutInflater.from(this).inflate(R.layout.viewpager_main, null);
        list.add(view1);
        mainViewPagerAdapter = new MainViewPagerAdapter(list);
        mViewPager.setAdapter(mainViewPagerAdapter);
    }

    private void initDogList(int size) {
        presenter = new DogPresenter(this);
        if (presenter.getDogList() != null) {
            dogList = presenter.getDogList();
        } else {
            for (int j = 0; j < size; j++) {
                Dog dog1 = new Dog("Vik" + j, initDate(2015, 3 + j, 4), "女", 21 + j);
                dogList.add(dog1);
            }

        }
        presenter = new DogPresenter(dogList, this);

        for (int i = 0; i < dogList.size(); i++) {
            findViewpagerId(i);
        }
    }


    private void findViewpagerId(int position) {
        View view = list.get(position);
        scrollView = (ObservableScrollView) view.findViewById(R.id.scroll_view_viewpager_main);
        mSlWeight = (SuitLines) view.findViewById(R.id.weight_line_viewpager_main);
        mSlTemperature = (SuitLines) view.findViewById(R.id.temperature_line_viewpager_main);
        mTvDogName = (TextView) view.findViewById(R.id.name_text_viewpager_main);
        mTvDogAge = (TextView) view.findViewById(R.id.age_text_viewpager_main);
        mTvDogSex = (TextView) view.findViewById(R.id.sex_text_viewpager_main);
        mTvDogWeight = (TextView) view.findViewById(R.id.weight_text_viewpager_main);

        mTvDogName.setText(dogList.get(position).getName());
        mTvDogAge.setText(dogList.get(position).getAge() + "岁");
        mTvDogSex.setText(dogList.get(position).getSex());
        mTvDogWeight.setText(dogList.get(position).getWeight() + "kg");
        scrollView.setScrollViewListener(this);
        List<Unit> lines = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            lines.add(new Unit(new SecureRandom().nextInt(23) + 18, i + ""));
        }

        mSlTemperature.feedWithAnim(lines);
        mSlWeight.feedWithAnim(lines);
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
            editDog();
            return true;
        } else if (id == R.id.action_scan) {
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

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        int a = y - oldy;
        Log.d(TAG, "onScrollChanged: y:" + a);
        if (a > 7 && !isFabOut) {
            isFabOut = true;
            mFab.hide();
        } else if (a < -7 && isFabOut) {
            mFab.show();
            isFabOut = false;
        }
    }

    @Override
    public void eat() {
        Intent intent = new Intent(MainActivity.this, EatActivity.class);
        startActivity(intent);
    }

    @Override
    public void editDog() {
        int num = mViewPager.getCurrentItem();
        Session session = Session.getSession();
        session.put(MAIN_NAME, dogList.get(num).getName());
        session.put(MAIN_BIRTHDAY, dogList.get(num).getBirthday());
        session.put(MAIN_SEX, dogList.get(num).getSex());
        session.put(MAIN_WEIGHT, dogList.get(num).getWeight());
        session.put(MAIN_POSITION, num);
        Intent intent = new Intent(MainActivity.this, DogEditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDogList(mainViewPagerAdapter.getCount());
    }
}
