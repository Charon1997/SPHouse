package nexuslink.charon.sphouse.view;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/25 11:17
 * 修改人：Charon
 * 修改时间：2017/10/25 11:17
 * 修改备注：
 */

public interface IRegisterView extends BaseView {

    /**
     * 得到用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取密码
     *
     * @return 密码
     */
    String getPassword1();

    /**
     * 得到确认密码
     *
     * @return 确认密码
     */
    String getPassword2();

    /**
     * 得到验证码
     *
     * @return 验证码
     */
    String getCode();

    /**
     * toast
     *
     * @param msg toast信息
     */
    void toast(String msg);

    /**
     * 保存
     */
    void save();

    /**
     * 能否点击
     *
     * @param clickable 能否点击
     */
    void buttonClickable(boolean clickable);

    /**
     * 设置button信息
     *
     * @param msg msg
     */
    void setCodeButton(String msg);

}
