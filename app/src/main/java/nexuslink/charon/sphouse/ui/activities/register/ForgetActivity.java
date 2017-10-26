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
import nexuslink.charon.sphouse.view.IForgetView;

import static nexuslink.charon.sphouse.config.Constant.COUNT_DOWN_TIME;
import static nexuslink.charon.sphouse.config.Constant.CURRENT_TIME;
import static nexuslink.charon.sphouse.config.Constant.FORGET_NUM;
import static nexuslink.charon.sphouse.config.Constant.FORGET_USERNAME;
import static nexuslink.charon.sphouse.config.Constant.PHONE_LENGTH;
import static nexuslink.charon.sphouse.config.Constant.REGISTER_FORGET;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/21 17:12
 * 修改人：Charon
 * 修改时间：2017/10/21 17:12
 * 修改备注：
 */

public class ForgetActivity extends BaseActivity implements IForgetView {
    private EditText mEtUsername, mEtCode;
    private Button mBtCode;
    private Toolbar mToolbar;
    private UserPresenter presenter = new UserPresenter(this);
    private Session session = Session.getSession();
    private String username;
    private SharedPreferences preferences;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.forget_code_button:
                presenter.forgetGetCode(FORGET_NUM);
                break;
            default:
                break;
        }
    }

    @Override
    public void initSession(Session session) {
        username = (String) session.get(REGISTER_FORGET);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_forget;
    }

    @Override
    public void initView(View view) {
        mEtUsername = $(R.id.forget_username_edit);
        mEtCode = $(R.id.forget_code_edit);
        mBtCode = $(R.id.forget_code_button);
        mToolbar = $(R.id.forget_toolbar);
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
        getSupportActionBar().setTitle("忘记密码");

        mEtUsername.setText(username);
        mEtUsername.setSelection(mEtUsername.length());

        preferences = getSharedPreferences("forget", MODE_PRIVATE);
        isCountDown();
    }

    @Override
    public String getUsername() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getCode() {
        return mEtCode.getText().toString();
    }

    @Override
    public void setCodeButton(String msg) {
        mBtCode.setText(msg);
    }

    /**
     * 检查验证码
     *
     * @param username
     * @param code
     */
    @Override
    public void next(String username, String code) {
        loading(true);
        if (username.length() == PHONE_LENGTH && code.length() == 6) {
            //检查验证码
            Intent intent = new Intent(ForgetActivity.this, ReSetActivity.class);
            session.put(FORGET_USERNAME, username);
            startActivity(intent);
            loading(false);
        } else if (username.length() != PHONE_LENGTH) {
            showToast("请输入正确的手机号");
            loading(false);
        } else if (username.length() == PHONE_LENGTH && code.length() != 6) {
            showToast("验证码错误");
            loading(false);
        }
    }

    @Override
    public void loading(boolean loading) {
        loading(loading, "忘记密码", "正在验证");
    }

    @Override
    public void buttonClickable(boolean clickable) {
        mBtCode.setClickable(clickable);
    }

    @Override
    public void toast(String msg) {
        showToast(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_forget_next:
                presenter.forgetNext();
                break;
            default:
                break;
        }
        return true;
    }

    public long readTime() {
        long time = 0;
        try {
            time = Long.parseLong(mBtCode.getText().toString().substring(0, mBtCode.getText().length() - 5));
        } catch (NumberFormatException e) {
            Log.e(TAG, "readTime: "+e);
        }
        return time;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(COUNT_DOWN_TIME, readTime());
        editor.putLong(CURRENT_TIME, System.currentTimeMillis());
        editor.apply();
    }

    public void isCountDown() {
        long remainingTime = preferences.getLong(COUNT_DOWN_TIME, 0);
        long restTime = (remainingTime - ((System.currentTimeMillis() - preferences.getLong(CURRENT_TIME, 0)) / 1000));
        if (restTime > 0) {
            CountDownTimer timer = new CountDownTimer(restTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    buttonClickable(false);
                    mBtCode.setText(millisUntilFinished/1000+"秒后可重发");
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
