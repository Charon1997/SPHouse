package nexuslink.charon.sphouse.biz.register;

import nexuslink.charon.sphouse.bean.UserBean;
import nexuslink.charon.sphouse.biz.register.IUserBiz;
import nexuslink.charon.sphouse.biz.register.OnLoginListener;

import static java.lang.Thread.sleep;

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
                if (username.equals("123") && password.equals("123")){
                    listener.loginSuccess(new UserBean(username,password));
                } else {
                    listener.loginFailed();
                }
            }
        }).start();
    }




}
