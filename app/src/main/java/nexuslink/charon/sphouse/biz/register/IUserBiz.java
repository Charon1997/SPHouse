package nexuslink.charon.sphouse.biz.register;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/20 22:16
 * 修改人：Charon
 * 修改时间：2017/10/20 22:16
 * 修改备注：
 */

public interface IUserBiz {
    /**
     * 登录
     * @param username
     * @param password
     * @param listener
     */
    public void signIn(String username,String password,OnLoginListener listener);

    /**
     * 获取验证码
     * @param phoneNum
     * @param second
     * @param listener
     */
    public void getMessageCode(String phoneNum ,long second,OnClickableListener listener);

    /**
     * 重设密码
     * @param username
     * @param password
     * @param listener
     */
    public void resetPassword(String username,String password,OnResetListener listener);

    /**
     * 注册账号
     * @param username
     * @param password
     * @param code
     * @param listener
     */
    public void registerSave(String username,String password,String code,OnResetListener listener);
}
