package nexuslink.charon.sphouse.ui.activities;

import android.content.Context;
import android.os.Bundle;
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

import static nexuslink.charon.sphouse.config.Constant.EAT_INTAKE;
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

public class EatEditActivity extends BaseActivity {
    private Date date;
    private int foodIntake;
    private TimePickerView mTpvTime;
    private OptionsPickerView mOPVPick;
    private Toolbar mToolbar;
    private EditText mEtTime, mEtIntake;
    private List<Integer> intakeList;

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
        date = (Date) session.get(EAT_TIME);
        foodIntake = (int) session.get(EAT_INTAKE);
        session.cleanUpSession();

        mEtTime.setText(getTime(date));
        mEtIntake.setText(getIntake(foodIntake));

        mEtIntake.setInputType(InputType.TYPE_NULL);
        mEtTime.setInputType(InputType.TYPE_NULL);
        Log.d(TAG, "doBusiness: " + date + "intake" + foodIntake);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mTpvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                EditText et = (EditText) v;
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
                finish();
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
                showToast("确定");
                break;
        }
        return true;
    }

}
