package nexuslink.charon.sphouse.biz.register;

import android.os.Handler;
import android.os.Message;

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
        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (second > 0){

                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = Message.obtain();
                            message.what = 0;
                            second --;
                            listener.cannotClick(second+"秒");
                            sendMessage(message);
                        } else {
                            Message message = Message.obtain();
                            message.what = 1;
                            sendMessage(message);
                            second = FORGET_NUM;
                        }
                        break;
                    case 1:
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        listener.canClick();
                        break;
                    default:
                        break;
                }

            }
        }; ;
        //模拟发短信
        Message message = Message.obtain();
        message.what = 0;
        mHandler.sendMessage(message);
    }


}
