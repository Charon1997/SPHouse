package nexuslink.charon.sphouse.ui.activities.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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
import nexuslink.charon.sphouse.view.IForgetView;

import static nexuslink.charon.sphouse.config.Constant.CODE_LENGTH;
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
    private EventHandler eventHandler;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    loading(false);
                    Intent intent = new Intent(ForgetActivity.this, ReSetActivity.class);
                    session.put(FORGET_USERNAME, username);
                    startActivity(intent);
                    break;
                case 2:
                    presenter.forgetNext(true);
                    break;
                case 3:
                    presenter.forgetGetCode(FORGET_NUM);
                    break;
                case 4:

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    } ;



    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.forget_code_button:
                SMSSDK.getVerificationCode("86", getUsername());
                Log.d("TAG", "getMessageCode: 发送");
                //收回键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
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

        initSMS();


    }


    private void initSMS() {
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (o instanceof Throwable) {
                    Throwable throwable = (Throwable) o;
                    String msg = throwable.getMessage();
                    error(msg.substring(24,msg.length()-2));
                } else {
                    if (i1 == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Message message = new Message();
                            message.what = 1;
                            mHandler.sendMessage(message);

                        } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            boolean smart = (boolean) o;
                            Message message = new Message();
                            if (smart) {
                                //智能短信

                                message.what = 2;
                                mHandler.sendMessage(message);

                            } else {
                                //短信
                                message.what = 3;
                                mHandler.sendMessage(message);

                            }
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
    public void next(final String username, String code, boolean smart) {
        //如果是智能验证
        if (smart) {
            Intent intent = new Intent(ForgetActivity.this, ReSetActivity.class);
            session.put(FORGET_USERNAME, username);
            startActivity(intent);
        } else {
            loading(true);

            if (username.length() == PHONE_LENGTH && code.length() == CODE_LENGTH) {
                //检查验证码
                SMSSDK.submitVerificationCode("86", getUsername(), getCode());

            } else if (username.length() != PHONE_LENGTH) {
                error("请输入正确的手机号");
                loading(false);
            } else if (username.length() == PHONE_LENGTH && code.length() != CODE_LENGTH) {
                error("验证码错误");
                loading(false);
            }
        }
    }

    @Override
    public void loading(boolean loading) {
        loading(loading, "忘记密码", "正在验证");
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
    public void toast(String msg) {
        showToast(msg);
    }

    @Override
    public void error(String msg) {
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
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
                presenter.forgetNext(false);
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
            Log.e(TAG, "readTime: " + e);
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
