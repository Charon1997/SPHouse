package nexuslink.charon.sphouse.biz.register;

import android.os.Handler;
import android.os.Message;

import nexuslink.charon.sphouse.bean.UserBean;
import nexuslink.charon.sphouse.utils.TimeUtil;

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
    private int second = FORGET_NUM;
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
                if (username.equals("123") && password.equals("123")){
                    listener.loginSuccess(new UserBean(username,password));
                } else {
                    listener.loginFailed();
                }
            }
        }).start();
    }

    @Override
    public void getMessageCode(String phoneNum, final OnClickableListener listener) {
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.countDown(listener);
        //模拟发短信
        Message message = Message.obtain();
        message.what = 0;
        timeUtil.mHandler.sendMessage(message);
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
                listener.onSuccess(new UserBean(username,password));
            }
        }).start();
    }

    @Override
    public void registerSave(final String username, final String password ,String code, final OnResetListener listener) {

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
                listener.onSuccess(new UserBean(username,password));
            }
        }).start();
    }


}
