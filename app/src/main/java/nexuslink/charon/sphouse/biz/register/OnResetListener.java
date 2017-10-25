package nexuslink.charon.sphouse.biz.register;

import nexuslink.charon.sphouse.bean.UserBean;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/25 10:04
 * 修改人：Charon
 * 修改时间：2017/10/25 10:04
 * 修改备注：
 */

public interface OnResetListener {
    public void onSuccess(UserBean userBean);

    public void onFailed();
}
