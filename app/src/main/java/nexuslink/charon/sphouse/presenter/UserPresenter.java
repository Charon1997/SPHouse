package nexuslink.charon.sphouse.presenter;

import android.os.Handler;

import nexuslink.charon.sphouse.bean.UserBean;
import nexuslink.charon.sphouse.biz.register.IUserBiz;
import nexuslink.charon.sphouse.biz.register.OnClickableListener;
import nexuslink.charon.sphouse.biz.register.OnLoginListener;
import nexuslink.charon.sphouse.biz.register.OnResetListener;
import nexuslink.charon.sphouse.biz.register.UserBiz;
import nexuslink.charon.sphouse.view.IForgetView;
import nexuslink.charon.sphouse.view.IRegisterView;
import nexuslink.charon.sphouse.view.IResetView;
import nexuslink.charon.sphouse.view.ISignInView;

import static nexuslink.charon.sphouse.config.Constant.CODE_LENGTH;
import static nexuslink.charon.sphouse.config.Constant.FORGET_NUM;
import static nexuslink.charon.sphouse.config.Constant.PASSWORD_MIX;
import static nexuslink.charon.sphouse.config.Constant.PHONE_LENGTH;

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
    private IResetView resetView;
    private IRegisterView registerView;
    private Handler mHandler = new Handler();

    public UserPresenter(ISignInView signUpView) {
        userBiz = new UserBiz();
        this.signInView = signUpView;
    }

    public UserPresenter(IForgetView forgetView) {
        userBiz = new UserBiz();
        this.forgetView = forgetView;
    }

    public UserPresenter(IResetView resetView) {
        userBiz = new UserBiz();
        this.resetView = resetView;
    }

    public UserPresenter(IRegisterView registerView) {
        userBiz = new UserBiz();
        this.registerView = registerView;
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
                        signInView.showFailError();
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


    /**
     * 忘记密码界面获取code
     *
     * @param time 倒计时时间
     */
    public void forgetGetCode(long time) {
        if (forgetView.getUsername().length() == PHONE_LENGTH) {
            forgetView.buttonClickable(false);
            userBiz.getMessageCode(forgetView.getUsername(), time, new OnClickableListener() {
                @Override
                public void canClick() {
                    forgetView.buttonClickable(true);
                    forgetView.setCodeButton("获取验证码");
                }

                @Override
                public void cannotClick(String second) {
                    forgetView.setCodeButton(second);
                }
            });
        } else {
            forgetView.error("请输入正确的手机号");
        }
    }


    /**
     * 忘记密码完成检验
     *
     * @param smart 是否为智能检验
     */
    public void forgetNext(boolean smart) {
        forgetView.next(forgetView.getUsername(), forgetView.getCode(), smart);
    }

    /**
     * 重设密码
     */
    public void resetFinish() {
        if (resetView.getPassword1().equals(resetView.getPassword2())) {
            resetView.loading(true);
            userBiz.resetPassword(resetView.getUsername(), resetView.getPassword1(), new OnResetListener() {
                @Override
                public void onSuccess(UserBean userBean) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            resetView.loading(false);
                            resetView.toast("重设密码成功");
                            resetView.next();
                        }
                    });
                }

                @Override
                public void onFailed() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            resetView.loading(false);
                            resetView.toast("重设密码失败");
                        }
                    });
                }
            });
        } else {
            resetView.toast("输入密码不一致");
        }
    }


    /**
     * 注册界面获取code
     *
     * @param time 倒计时时长
     */
    public void registerGetCode(long time) {
        if (registerView.getUsername().length() == PHONE_LENGTH) {
            registerView.buttonClickable(false);
            userBiz.getMessageCode(registerView.getUsername(), time, new OnClickableListener() {
                @Override
                public void canClick() {
                    registerView.buttonClickable(true);
                    registerView.setCodeButton("获取验证码");
                }

                @Override
                public void cannotClick(String second) {
                    registerView.setCodeButton(second);
                }
            });
        } else {
            registerView.error("请输入正确的手机号");
        }
    }

    /**
     * 注册的保存
     */
    public void registerSave() {
        registerView.loading(true);
        userBiz.registerSave(registerView.getUsername(), registerView.getPassword1(), registerView.getCode(), new OnResetListener() {
            @Override
            public void onSuccess(UserBean userBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        registerView.loading(false);
                        registerView.save();
                    }
                });
            }

            @Override
            public void onFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        registerView.loading(false);
                        registerView.error("注册失败");
                    }
                });

            }
        });
    }
}
