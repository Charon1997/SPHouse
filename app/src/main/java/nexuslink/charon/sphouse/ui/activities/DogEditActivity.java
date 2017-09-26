package nexuslink.charon.sphouse.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.DogPresenter;
import nexuslink.charon.sphouse.utils.DataUtil;
import nexuslink.charon.sphouse.view.IDogEditView;

import static nexuslink.charon.sphouse.config.Constant.*;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/31 15:07
 * 修改人：Charon
 * 修改时间：2017/8/31 15:07
 * 修改备注：
 */

public class DogEditActivity extends BaseActivity implements IDogEditView {
    private String name, sex;
    private boolean isEdit ;
    private Date birthday;
    private int weight;
    private Toolbar mToolbar;
    private EditText mEtName, mEtAge, mEtSex, mEtWeight;
    private TextView mTvDelete;
    private OptionsPickerView mOpvSex, mOpvWeight;
    private TimePickerView mTpvAge;
    private List<Integer> ageList, weightList;
    private DogPresenter presenter = new DogPresenter(this);
    private int position;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.edit_age_dog_edit:
                closeSoftInput();
                mTpvAge.show(v);
                break;
            case R.id.edit_sex_dog_edit:
                closeSoftInput();
                mOpvSex.show(v);
                break;
            case R.id.edit_weight_dog_edit:
                closeSoftInput();
                mOpvWeight.show(v);
                break;
            case R.id.text_delete_dog_edit:
                showDialog();
                break;
        }
    }

    @Override
    public void initSession(Session session) {
        isEdit = (boolean) session.get(MAIN_EDIT);
        position = (int) session.get(MAIN_POSITION);
        if (isEdit){
            name = (String) session.get(MAIN_NAME);
            sex = (String) session.get(MAIN_SEX);
            birthday = (Date) session.get(MAIN_BIRTHDAY);
            weight = (int) session.get(MAIN_WEIGHT);
        } else {
            name = "";
            sex = "男";
            birthday = new Date(100, 0, 0, 0, 0, 0);
            weight = 0;
        }

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_dog_edit;
    }

    @Override
    public void initView(View view) {
        mToolbar = $(R.id.toolbar_dog_edit);
        mEtName = $(R.id.edit_name_dog_edit);
        mEtAge = $(R.id.edit_age_dog_edit);
        mEtSex = $(R.id.edit_sex_dog_edit);
        mEtWeight = $(R.id.edit_weight_dog_edit);
        mTvDelete = $(R.id.text_delete_dog_edit);
    }

    @Override
    public void setListener() {
        mEtName.setOnClickListener(this);
        mEtAge.setOnClickListener(this);
        mEtSex.setOnClickListener(this);
        mEtWeight.setOnClickListener(this);
        mTvDelete.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        mEtName.setText(name);
        mEtAge.setText(getTime(birthday));
        mEtSex.setText(sex);
        mEtWeight.setText(weight + "kg");
        if (!isEdit){
            mTvDelete.setVisibility(View.GONE);
        }

        mEtName.setSelection(mEtName.length());
        mEtAge.setInputType(InputType.TYPE_NULL);
        mEtSex.setInputType(InputType.TYPE_NULL);
        mEtWeight.setInputType(InputType.TYPE_NULL);

        initOpvAge();
        initOpvSex();
        initOpvWeight();
    }

    private void initOpvSex() {
        final List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        int option = 0;
        if (sex.equals("男")) {
            option = 0;
        } else option = 1;

        mOpvSex = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (!sex.equals(sexList.get(options1))) {
                    EditText et = (EditText) v;
                    et.setText(sexList.get(options1));
                    sex = sexList.get(options1);
                }

            }
        }).setSelectOptions(option)
                .setTitleText("性别")
                .setCancelText("取消")
                .setSubmitText("确认").setLabels("", "", "").build();

        mOpvSex.setPicker(sexList);
    }

    private void initOpvWeight() {
        mOpvWeight = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (weight != weightList.get(options1)) {
                    EditText et = (EditText) v;
                    et.setText(weightList.get(options1) + "kg");
                    weight = weightList.get(options1);
                }


            }
        }).setSelectOptions(weight)
                .setTitleText("体重")
                .setCancelText("取消")
                .setSubmitText("确认").setLabels("kg", "kg", "kg").build();
        initWeight(130);
        mOpvWeight.setPicker(weightList);
    }

    private void initWeight(int max) {
        weightList = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            weightList.add(i);
        }
    }

    private void initOpvAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        final Date d = new Date(Calendar.YEAR - 30, 0, 0);
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(d);
        mTpvAge = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (birthday != date) {
                    EditText et = (EditText) v;
                    et.setText(getTime(date));
                    birthday = date;
                }
            }
        }).setTitleText("生日")
                .setCancelText("取消")
                .setSubmitText("确认")
                .setType(new boolean[]{true, true, true, false, false, false})
                //.setRangDate(lastCalendar,Calendar.getInstance())
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setDate(calendar).build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1)
                    position = 0;
                MainActivity.mCurrentPager = position;
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("狗狗信息");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eat_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_edit:
                presenter.save(position,isEdit);
                showToast("信息已保存");
                finish();
                break;
        }
        return true;
    }


    @Override
    public void loading(boolean loading) {

    }

    @Override
    public String getName() {
        return mEtName.getText().toString();
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getPosition() {
        return position;
    }

    private void closeSoftInput() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void showDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(R.drawable.doghouse_yellow_logo);
        normalDialog.setTitle("删除狗窝");
        normalDialog.setMessage("狗狗很想要个窝，是否删除？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do传入数据库，建立连接，退出
                        MainActivity main = new MainActivity();
                        if (DataUtil.getDogSize() -1 == position){
                            DataUtil.deleteDogByKey((long)main.getDogId());
                            main.saveDogId(main.getDogId() -1);
                        } else {
                            DataUtil.deleteDogByKey((long)main.getDogId()-1);
                        }

                        finish();
                        showToast("已经删除"+mEtName.getText());
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
