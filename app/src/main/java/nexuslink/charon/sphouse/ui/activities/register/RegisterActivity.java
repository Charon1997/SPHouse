package nexuslink.charon.sphouse.ui.activities.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.UserPresenter;
import nexuslink.charon.sphouse.ui.activities.BaseActivity;
import nexuslink.charon.sphouse.view.IRegisterView;

import static nexuslink.charon.sphouse.config.Constant.COUNT_DOWN_TIME;
import static nexuslink.charon.sphouse.config.Constant.CURRENT_TIME;
import static nexuslink.charon.sphouse.config.Constant.FORGET_NUM;
import static nexuslink.charon.sphouse.config.Constant.REGISTER_REGISTER;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/25 11:16
 * 修改人：Charon
 * 修改时间：2017/10/25 11:16
 * 修改备注：
 */

public class RegisterActivity extends BaseActivity implements IRegisterView {
    private EditText mEtUsername, mEtCode, mEtPassword1, mEtPassword2;
    private Button mBtCode;
    private Toolbar mToolbar;
    private String username;
    private UserPresenter presenter = new UserPresenter(this);
    private SharedPreferences preferences;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.register_code_button:
                //获取验证码
                presenter.registerGetCode(FORGET_NUM);
                break;
            default:
                break;
        }
    }

    @Override
    public void initSession(Session session) {
        username = (String) session.get(REGISTER_REGISTER);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        mEtUsername = $(R.id.register_username_edit);
        mEtPassword1 = $(R.id.register_password_edit);
        mEtPassword2 = $(R.id.register_password2_edit);
        mEtCode = $(R.id.register_code_edit);
        mBtCode = $(R.id.register_code_button);
        mToolbar = $(R.id.register_toolbar);
    }

    @Override
    public void setListener() {
        mBtCode.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");

        mEtUsername.setText(username);
        mEtUsername.setSelection(mEtUsername.length());

        preferences = getSharedPreferences("register", MODE_PRIVATE);
        isCountDown();
    }

    @Override
    public void loading(boolean loading) {
        loading(loading, "注册", "正在保存...");
    }

    @Override
    public String getUsername() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword1() {
        return mEtPassword1.getText().toString();
    }

    @Override
    public String getPassword2() {
        return mEtPassword2.getText().toString();
    }

    @Override
    public String getCode() {
        return mEtCode.getText().toString();
    }

    @Override
    public void toast(String msg) {
        showToast(msg);
    }

    @Override
    public void save() {
        showToast("注册成功");
        Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void buttonClickable(boolean clickable) {
        mBtCode.setClickable(clickable);
        if (clickable) {
            mBtCode.setBackgroundResource(R.drawable.btn_dwon);
        } else {
            mBtCode.setBackgroundResource(R.drawable.btn_unclick);
        }
    }

    @Override
    public void setCodeButton(String msg) {
        mBtCode.setText(msg);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reset_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reset_finish:
                //save
                presenter.registerSave();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(COUNT_DOWN_TIME, readTime());
        editor.putLong(CURRENT_TIME, System.currentTimeMillis());
        Log.d(TAG, "onStop: " + readTime());
        editor.apply();
    }

    private long readTime() {
        long time = 0;
        try {
            time = Long.parseLong(mBtCode.getText().toString().substring(0, mBtCode.getText().length() - 5));
        } catch (NumberFormatException e) {
            Log.e(TAG, "readTime: "+e);
        }

        return time;
    }

    public void isCountDown() {
        long remainingTime = preferences.getLong(COUNT_DOWN_TIME, 0);
        long restTime = (remainingTime - ((System.currentTimeMillis() - preferences.getLong(CURRENT_TIME, 0)) / 1000));
        Log.d(TAG, "isCountDown: " + restTime);
        if (restTime > 0) {
            CountDownTimer timer = new CountDownTimer(restTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    buttonClickable(false);
                    mBtCode.setText(millisUntilFinished / 1000 + "秒后可重发");
                }

                @Override
                public void onFinish() {
                    buttonClickable(true);
                    mBtCode.setText("获取验证码");
                }
            };
            timer.start();
        }

    }
}
