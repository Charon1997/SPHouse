package nexuslink.charon.sphouse.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.EatPresenter;
import nexuslink.charon.sphouse.view.IEatEditView;

import static nexuslink.charon.sphouse.config.Constant.EAT_EDIT;
import static nexuslink.charon.sphouse.config.Constant.EAT_INTAKE;
import static nexuslink.charon.sphouse.config.Constant.EAT_POSITION;
import static nexuslink.charon.sphouse.config.Constant.EAT_TIME;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/28 17:36
 * 修改人：Charon
 * 修改时间：2017/8/28 22:35
 * 修改备注：
 */

public class EatEditActivity extends BaseActivity implements IEatEditView {
    private boolean isEdit;
    private Date mDate ;
    private int foodIntake,position;
    private TimePickerView mTpvTime;
    private OptionsPickerView mOPVPick;
    private Toolbar mToolbar;
    private EditText mEtTime, mEtIntake;
    private List<Integer> intakeList;
    private EatPresenter presenter = new EatPresenter(this);

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.edit_time_eat_edit:
                showToast("time");
                mTpvTime.show(v);
                break;
            case R.id.edit_intake_eat_edit:
                showToast("intake");
                mOPVPick.show(v);
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
        return R.layout.activity_eat_edit;
    }

    @Override
    public void initView(View view) {
        mEtTime = $(R.id.edit_time_eat_edit);
        mEtIntake = $(R.id.edit_intake_eat_edit);
        mToolbar = $(R.id.toolbar_eat_edit);
    }

    @Override
    public void setListener() {
        mEtTime.setOnClickListener(this);
        mEtIntake.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        Session session = Session.getSession();
        isEdit = (boolean) session.get(EAT_EDIT);
        if (isEdit){
            mDate = (Date) session.get(EAT_TIME);
            foodIntake = (int) session.get(EAT_INTAKE);
            position = (int) session.get(EAT_POSITION);
        }else {
            mDate = new Date(0,0,0,0,0,0);
            foodIntake = 0;
        }
        mEtTime.setText(getTime(mDate));
        mEtIntake.setText(getIntake(foodIntake));
        session.cleanUpSession();

        mEtIntake.setInputType(InputType.TYPE_NULL);
        mEtTime.setInputType(InputType.TYPE_NULL);

        Log.d(TAG, "doBusiness: " + mDate + "intake" + foodIntake);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        mTpvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                EditText et = (EditText) v;
                mDate = date;
                et.setText(getTime(date));
            }
        }).setTitleText("喂食时间")
                .setCancelText("取消")
                .setSubmitText("确认")
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setDate(calendar).build();

        mOPVPick = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                EditText et = (EditText) v;
                foodIntake = intakeList.get(options1);
                et.setText(getIntake(intakeList.get(options1)));
            }
        }).setSelectOptions(foodIntake)
                .setTitleText("喂食量")
                .setCancelText("取消")
                .setSubmitText("确认").setLabels("g", "g", "g").build();
        initIntake(150);
        mOPVPick.setPicker(intakeList);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private String getIntake(int foodIntake) {
        return foodIntake + "g";
    }

    private void initIntake(int max) {
        intakeList = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            intakeList.add(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("编辑");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eat_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_eat_edit:
                presenter.save(isEdit);
                showToast("信息已保存");
                finish();
                break;
        }
        return true;
    }

    @Override
    public Date getTime() {
        return mDate;
    }

    @Override
    public int getIntake() {
        return foodIntake;
    }
    @Override
    public int getPosition(){
        return position;
    }

    public void showDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(R.drawable.test_head_img);
        normalDialog.setTitle("暂未保存");
        normalDialog.setMessage("信息还未保存，是否退出？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do传入数据库，建立连接，退出
                    finish();
                    }
                });
        normalDialog.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

}
