package nexuslink.charon.sphouse.view;

/**
 * 项目名称：SPHouse
 * 类描述：忘记密码的页面接口
 * 创建人：Charon
 * 创建时间：2017/10/21 19:53
 * 修改人：Charon
 * 修改时间：2017/11/02 22:18
 * 修改备注：增加了注释
 */

public interface IForgetView extends BaseView {
    /**
     * 得到用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    String getCode();

    /**
     * 设置button的信息
     *
     * @param msg 信息
     */
    void setCodeButton(String msg);

    /**
     * 点击下一步
     *
     * @param username 用户名
     * @param code     验证码
     * @param smart    是否为智能通过
     */
    void next(String username, String code, boolean smart);

    /**
     * 按钮是否可以点击
     *
     * @param clickable 是否可以点击
     */
    void buttonClickable(boolean clickable);

    /**
     * toast
     *
     * @param msg toast的信息
     */
    void toast(String msg);

    /**
     * 错误信息
     *
     * @param msg 错误信息
     */
    void error(String msg);

}
