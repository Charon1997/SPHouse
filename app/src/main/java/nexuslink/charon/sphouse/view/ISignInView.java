package nexuslink.charon.sphouse.view;

import nexuslink.charon.sphouse.bean.UserBean;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/20 21:47
 * 修改人：Charon
 * 修改时间：2017/10/20 21:47
 * 修改备注：
 */

public interface ISignInView {
    public String getUserName();

    public String getPassword();

    public void showFailError();

    /**
     * 登录
     * @param userBean
     */
    public void signIn(UserBean userBean);

    public void forget(String username);

    public void register(String username);

    public void loading(boolean loading);
}
