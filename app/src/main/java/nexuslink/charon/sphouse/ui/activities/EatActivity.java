package nexuslink.charon.sphouse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.bean.EatBean;
import nexuslink.charon.sphouse.config.OnEatItemOnClickListener;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.EatPresenter;
import nexuslink.charon.sphouse.ui.adapter.EatRecyclerViewAdapter;
import nexuslink.charon.sphouse.view.IEatView;

import static nexuslink.charon.sphouse.config.Constant.EAT_EDIT;
import static nexuslink.charon.sphouse.config.Constant.EAT_INTAKE;
import static nexuslink.charon.sphouse.config.Constant.EAT_POSITION;
import static nexuslink.charon.sphouse.config.Constant.EAT_TIME;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/27 20:28
 * 修改人：Charon
 * 修改时间：2017/8/27 20:28
 * 修改备注：
 */

public class EatActivity extends BaseActivity implements IEatView {
    private TextView mTvEatTime;
    private Button mBtPickTime;

    private EditText mEtPickTime;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private EatRecyclerViewAdapter adapter;

    //测试数据
    private List<EatBean> eatList;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private EatPresenter presenter;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.eat_fab:
                showToast("添加");
                presenter.toEdit();
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
        return R.layout.activity_eat;
    }

    @Override
    public void initView(View view) {
        mToolbar = $(R.id.toolbar_eat);
        mFab = $(R.id.eat_fab);
        mRecyclerView = $(R.id.recycler_eat);
    }

    @Override
    public void setListener() {
        mFab.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {


    }

    private void initData(int j) {
        eatList = new ArrayList<>();
//        try {
//            date = dateFormat.parse("2017-05-30 05:30:30");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        for (int i = 0;i < j;i++) {
            Date date = new Date(2010, 11, 20, 2+i, 23, 41);
            EatBean eatbean = new EatBean(20, date,false);
            eatList.add(eatbean);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("喂食");

        presenter = new EatPresenter(this);
        if (presenter.getEatList() != null) {
            eatList = presenter.getEatList();
            Log.d(TAG, "listSize: "+eatList.size());
        }else {
            initData(4);
        }
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.d(TAG, "doBusiness: adaper");

        adapter = new EatRecyclerViewAdapter(eatList);
        mRecyclerView.setAdapter(adapter);
        presenter = new EatPresenter(this,eatList);
        adapter.setOnEatItemOnClickListener(new OnEatItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.toEdit(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eat,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_eat:
                showToast("喂食");
                break;
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        int position = adapter.getPosition();
        switch (id) {
            case 0:
                showToast("编辑" + position);
                presenter.toEdit(position);
                break;
            case 1:
                showToast("删除" + position);
                presenter.deleteItem(position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void loading(boolean loading) {

    }

    @Override
    public void deleteItem(int position) {
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,presenter.getListSize()-position);
    }

    @Override
    public void toEdit(Date time, int foodIntake,int position,boolean isEdit) {
        Intent intent = new Intent(EatActivity.this, EatEditActivity.class);
        Session session = Session.getSession();
        session.put(EAT_EDIT, isEdit);
        session.put(EAT_TIME, time);
        session.put(EAT_INTAKE, foodIntake);
        session.put(EAT_POSITION,position);
        startActivity(intent);
    }
    @Override
    public void toEdit(boolean isEdit) {
        Intent intent = new Intent(EatActivity.this, EatEditActivity.class);
        Session session = Session.getSession();
        session.put(EAT_EDIT, isEdit);
        startActivity(intent);
    }


}
