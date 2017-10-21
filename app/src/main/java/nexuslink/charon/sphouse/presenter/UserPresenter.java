package nexuslink.charon.sphouse.presenter;

import android.os.Handler;

import nexuslink.charon.sphouse.bean.UserBean;
import nexuslink.charon.sphouse.biz.register.IUserBiz;
import nexuslink.charon.sphouse.biz.register.OnLoginListener;
import nexuslink.charon.sphouse.biz.register.UserBiz;
import nexuslink.charon.sphouse.view.IForgetView;
import nexuslink.charon.sphouse.view.ISignInView;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/20 22:32
 * 修改人：Charon
 * 修改时间：2017/10/20 22:32
 * 修改备注：
 */

public class UserPresenter {
    private IUserBiz userBiz;
    private ISignInView signInView;
    private IForgetView forgetView;
    private Handler mHandler = new Handler();

    public UserPresenter(ISignInView signUpView) {
        userBiz = new UserBiz();
        this.signInView = signUpView;
    }

    public UserPresenter(IForgetView forgetView) {
        userBiz = new UserBiz();
        this.forgetView = forgetView;
    }

    /**
     * 登录
     */
    public void signIn() {
        signInView.loading(true);
        userBiz.signIn(signInView.getUserName(), signInView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final UserBean user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        signInView.loading(false);
                        signInView.signIn(user);
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        signInView.showFaileError();
                        signInView.loading(false);
                    }
                });
            }
        });
    }

    /**
     * 忘记密码
     */
    public void forget() {
        signInView.forget(signInView.getUserName());
    }

    /**
     * 注册
     */
    public void register() {
        signInView.register(signInView.getUserName());
    }


    public void getCode() {
        forgetView.getCodeButton(forgetView.getUsername());
    }


    public void forgetNext() {
        forgetView.next(forgetView.getUsername(),forgetView.getCode());
    }
}
