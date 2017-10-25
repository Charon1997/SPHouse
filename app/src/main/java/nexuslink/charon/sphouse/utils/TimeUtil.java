package nexuslink.charon.sphouse.utils;

import android.os.Handler;
import android.os.Message;

import nexuslink.charon.sphouse.biz.register.OnClickableListener;

import static java.lang.Thread.sleep;
import static nexuslink.charon.sphouse.config.Constant.FORGET_NUM;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/25 10:34
 * 修改人：Charon
 * 修改时间：2017/10/25 10:34
 * 修改备注：
 */

public class TimeUtil {
    public Handler mHandler ;
    private int time = FORGET_NUM;

    public   void countDown(final OnClickableListener listener) {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (time > 0) {

                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = Message.obtain();
                            message.what = 0;
                            listener.cannotClick(time + "秒");
                            time--;
                            sendMessage(message);
                        } else {
                            Message message = Message.obtain();
                            message.what = 1;
                            sendMessage(message);
                            time = FORGET_NUM;
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

            ;
        };
    }

}
