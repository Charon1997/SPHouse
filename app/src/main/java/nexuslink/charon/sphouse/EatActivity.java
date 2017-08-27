package nexuslink.charon.sphouse;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.view.WheelTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import nexuslink.charon.sphouse.adapter.EatRecyclerViewAdapter;
import nexuslink.charon.sphouse.bean.EatBean;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/27 20:28
 * 修改人：Charon
 * 修改时间：2017/8/27 20:28
 * 修改备注：
 */

public class EatActivity extends BaseActivity {
    private TextView mTvEatTime;
    private Button mBtPickTime;
    private TimePickerView mTpv;
    private EditText mEtPickTime;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    //测试数据
    private List<EatBean> eatList;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.eat_fab:
                showToast("添加");
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
        initData(4);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        EatRecyclerViewAdapter adapter = new EatRecyclerViewAdapter(eatList);
        mRecyclerView.setAdapter(adapter);
//        mTpv = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {
//                EditText et = (EditText) v;
//                et.setText(getTime(date));
//            }
//        }).setTitleText("喂食时间")
//                .setCancelText("取消")
//                .setSubmitText("确认")
//                .setType(new boolean[]{false, false, false, true, true, false})
//                .setLabel("年", "月", "日", "时", "分", "秒").build();
    }

    private void initData(int j) {
        eatList = new ArrayList<>();
//        try {
//            date = dateFormat.parse("2017-05-30 05:30:30");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        for (int i = 0;i < j;i++) {
            Date date = new Date(2010, 11, 20, 4+j, 21, 41);
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

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("dd日HH时mm分");
        return format.format(date);
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
    }
}
