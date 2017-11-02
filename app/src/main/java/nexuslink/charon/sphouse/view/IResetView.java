package nexuslink.charon.sphouse.view;

/**
 * 项目名称：SPHouse
 * 类描述：重设密码界面
 * 创建人：Charon
 * 创建时间：2017/10/24 22:18
 * 修改人：Charon
 * 修改时间：2017/10/24 22:18
 * 修改备注：
 */

public interface IResetView extends BaseView {
    /**
     * 获取密码1
     *
     * @return 密码1
     */
    String getPassword1();

    /**
     * 获取密码2
     *
     * @return 密码2
     */
    String getPassword2();

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 提交
     */
    void next();

    /**
     * toast
     *
     * @param msg toast的msg
     */
    void toast(String msg);

}
