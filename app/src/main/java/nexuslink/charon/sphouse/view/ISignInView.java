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

public interface ISignInView extends BaseView{
    /**
     * 得到用户名
     *
     * @return 用户名
     */
    String getUserName();

    /**
     * 得到密码
     *
     * @return 密码
     */
    String getPassword();

    /**
     * 登录出错时调用
     */
    void showFailError();

    /**
     * 登录
     *
     * @param userBean 用户
     */
    void signIn(UserBean userBean);

    /**
     * 点击忘记密码时调用
     *
     * @param username 用户名
     */
    void forget(String username);

    /**
     * 点击注册时调用
     * @param username 用户名
     */
    void register(String username);

}
