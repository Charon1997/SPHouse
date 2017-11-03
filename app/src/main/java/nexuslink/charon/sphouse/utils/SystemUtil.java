package nexuslink.charon.sphouse.utils;

import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/3 11:50
 * 修改人：Charon
 * 修改时间：2017/11/3 11:50
 * 修改备注：
 */

public class SystemUtil {
    public static void hideSoftKeyboard(@NotNull InputMethodManager manager, @NotNull Window window) {
        InputMethodManager imm =  manager;
        if (imm != null) {
            imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
        }
    }
}
