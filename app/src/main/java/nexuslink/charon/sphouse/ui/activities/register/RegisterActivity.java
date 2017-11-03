package nexuslink.charon.sphouse.ui.activities.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.UserPresenter;
import nexuslink.charon.sphouse.ui.activities.BaseActivity;
import nexuslink.charon.sphouse.utils.SystemUtil;
import nexuslink.charon.sphouse.view.IRegisterView;

import static nexuslink.charon.sphouse.config.Constant.CODE_GET_SMS_COMPLETE;
import static nexuslink.charon.sphouse.config.Constant.CODE_LENGTH;
import static nexuslink.charon.sphouse.config.Constant.CODE_SUBMIT_COMPLETE;
import static nexuslink.charon.sphouse.config.Constant.COUNT_DOWN_TIME;
import static nexuslink.charon.sphouse.config.Constant.CURRENT_TIME;
import static nexuslink.charon.sphouse.config.Constant.FORGET_NUM;
import static nexuslink.charon.sphouse.config.Constant.FORGET_USERNAME;
import static nexuslink.charon.sphouse.config.Constant.PASSWORD_MIX;
import static nexuslink.charon.sphouse.config.Constant.PHONE_LENGTH;
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
    private EventHandler eventHandler;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SUBMIT_COMPLETE:
                    //完成验证码校验，去保存数据
                    presenter.registerSave();
                    break;
                case CODE_GET_SMS_COMPLETE:
                    //启动倒计时
                    presenter.registerGetCode(FORGET_NUM);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.register_code_button:
                //获取验证码
                SMSSDK.getVerificationCode("86", getUsername());
                Log.d("TAG", "getMessageCode: 发送");
                //收回键盘
                SystemUtil.hideSoftKeyboard((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE),getWindow());
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
        initSMS();
    }

    /**
     * 初始化SMS
     */
    private void initSMS() {
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (o instanceof Throwable) {
                    Throwable throwable = (Throwable) o;
                    String msg = throwable.getMessage();
                    error(msg.substring(24, msg.length() - 2));
                    loading(false);
                } else {
                    if (i1 == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Message message = new Message();
                            message.what = CODE_SUBMIT_COMPLETE;
                            mHandler.sendMessage(message);
                        } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            Message message = new Message();
                            //短信
                            message.what = CODE_GET_SMS_COMPLETE;
                            mHandler.sendMessage(message);

                        } else if (i == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                            //语音
                        } else if (i == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
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
    public void error(String msg) {
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
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
                // 隐藏软键盘
                SystemUtil.hideSoftKeyboard((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE),getWindow());
                if (getUsername().length() != PHONE_LENGTH) {
                    error("电话号码不正确");
                    break;
                }
                if (getCode().length() != CODE_LENGTH) {
                    error("验证码不正确");
                    break;
                }
                if (getPassword1().length() < PASSWORD_MIX || getPassword2().length() < PASSWORD_MIX) {
                    error("密码不能低于6位");
                    break;
                }

                if (!getPassword1().equals(getPassword2())) {
                    error("输入密码不一致");
                    break;
                }
                // 开启加载
                loading(true);
                // 校验输入
                SMSSDK.submitVerificationCode("86", getUsername(), getCode());
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 将倒计时的时间和退出的时间点记录下来，以便再次进入时能同步倒计时
     */
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(COUNT_DOWN_TIME, readTime());
        editor.putLong(CURRENT_TIME, System.currentTimeMillis());
        Log.d(TAG, "onStop: " + readTime());
        editor.apply();
    }

    /**
     * 读取现在的时间
     *
     * @return 现在的时间
     */
    private long readTime() {
        long time = 0;
        try {
            time = Long.parseLong(mBtCode.getText().toString().substring(0, mBtCode.getText().length() - 5));
        } catch (NumberFormatException e) {
            Log.e(TAG, "readTime: " + e);
        }
        return time;
    }

    /**
     * 判断倒计时是否结束
     */
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
