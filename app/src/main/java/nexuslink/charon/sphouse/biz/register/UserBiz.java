package nexuslink.charon.sphouse.biz.register;

import android.os.CountDownTimer;

import nexuslink.charon.sphouse.bean.UserBean;

import static java.lang.Thread.sleep;
import static nexuslink.charon.sphouse.config.Constant.FORGET_NUM;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/20 22:17
 * 修改人：Charon
 * 修改时间：2017/10/20 22:17
 * 修改备注：
 */

public class UserBiz implements IUserBiz {

    @Override
    public void signIn(final String username, final String password, final OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟联网操作
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果输入正确
                if ("111".equals(username) && "111".equals(password)) {
                    listener.loginSuccess(new UserBean(username, password));
                } else {
                    listener.loginFailed();
                }
            }
        }).start();
    }


    @Override
    public void getMessageCode(String phoneNum, long second, final OnClickableListener listener) {

        CountDownTimer timer = new CountDownTimer(second * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                listener.cannotClick(millisUntilFinished / 1000 + "秒后可重发");
            }

            @Override
            public void onFinish() {
                listener.canClick();
            }
        };

        timer.start();
    }

    @Override
    public void resetPassword(final String username, final String password, final OnResetListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟联网
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //if (result == 200){listener.onSuccess()}^^^
                listener.onSuccess(new UserBean(username, password));
            }
        }).start();
    }

    @Override
    public void registerSave(final String username, final String password, String code, final OnResetListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟联网操作
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果输入正确
                listener.onSuccess(new UserBean(username, password));
            }
        }).start();
    }


}
