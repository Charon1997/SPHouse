package nexuslink.charon.sphouse.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.bean.UserBean;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.UserPresenter;
import nexuslink.charon.sphouse.view.ISignInView;

import static nexuslink.charon.sphouse.config.Constant.REGISTER_FORGET;
import static nexuslink.charon.sphouse.config.Constant.REGISTER_SIGN_IN;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/20 21:46
 * 修改人：Charon
 * 修改时间：2017/10/20 21:46
 * 修改备注：
 */

public class SignInActivity extends BaseActivity implements ISignInView{
    private Session session = Session.getSession();
    private EditText mEtUsername,mEtPassword;
    private Button mBtSignIn,mBtForget,mBtRegister;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;

    private UserPresenter presenter = new UserPresenter(this);
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.signin_forget_button:
                presenter.forget();
                break;
            case R.id.signin_register_button:
                presenter.register();
                break;
            case R.id.signin_signin_button:
                presenter.signIn();
                break;
            default:
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
        return R.layout.activity_sign_in;
    }

    @Override
    public void initView(View view) {
        mEtUsername = $(R.id.signin_name);
        mEtPassword = $(R.id.signin_password);
        mBtForget = $(R.id.signin_forget_button);
        mBtSignIn = $(R.id.signin_signin_button);
        mBtRegister = $(R.id.signin_register_button);
        mToolbar = $(R.id.signin_toolbar);
    }

    @Override
    public void setListener() {
        mBtForget.setOnClickListener(this);
        mBtSignIn.setOnClickListener(this);
        mBtRegister.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("登录");
    }

    @Override
    public String getUserName() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public void loading(boolean loading) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("加载中...");
            mProgressDialog.setTitle("登录");
        }
        if (loading) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showFaileError() {
        showToast("登录失败，请检查密码是否正确");
    }

    @Override
    public void signIn(UserBean userBean) {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        session.put(REGISTER_SIGN_IN, userBean);
        startActivity(intent);
        finish();
    }

    @Override
    public void forget(String username) {
        Intent intent = new Intent(SignInActivity.this,ForgetActivity.class);
        session.put(REGISTER_FORGET,username);
        startActivity(intent);
    }

    @Override
    public void register(String username) {

    }
}
