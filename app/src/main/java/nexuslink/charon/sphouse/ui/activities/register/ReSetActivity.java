package nexuslink.charon.sphouse.ui.activities.register;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.config.Session;
import nexuslink.charon.sphouse.presenter.UserPresenter;
import nexuslink.charon.sphouse.ui.activities.BaseActivity;
import nexuslink.charon.sphouse.view.IResetView;

import static nexuslink.charon.sphouse.config.Constant.FORGET_USERNAME;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/24 21:44
 * 修改人：Charon
 * 修改时间：2017/10/24 21:44
 * 修改备注：
 */

public class ReSetActivity extends BaseActivity implements IResetView {
    private EditText mEtPassword1, mEtPassword2;
    private Session session = Session.getSession();
    private String username;
    private Toolbar mToolbar;
    private UserPresenter presenter = new UserPresenter(this);

    @Override
    public void widgetClick(View v) {
    }

    @Override
    public void initSession(Session session) {
        username = (String) session.get(FORGET_USERNAME);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_forget_reset;
    }

    @Override
    public void initView(View view) {
        mToolbar = $(R.id.forget_reset_toolbar);
        mEtPassword1 = $(R.id.forget_reset_password_edit);
        mEtPassword2 = $(R.id.forget_reset_password2_edit);
    }

    @Override
    public void setListener() {

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
        getSupportActionBar().setTitle("重设密码");
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
                presenter.resetFinish();
                break;
            default:
                break;
        }
        return true;
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
    public String getUsername() {
        return username;
    }

    @Override
    public void next() {
        Intent intent = new Intent(ReSetActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void loading(boolean loading) {
        loading(loading, "重设密码", "正在重设...");
    }

    @Override
    public void toast(String msg) {
        showToast(msg);
    }
}
