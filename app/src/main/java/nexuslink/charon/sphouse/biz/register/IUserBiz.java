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
     * 获取手机验证码
     * @param phoneNum
     */
    public void getMessageCode(String phoneNum ,OnClickableListener listener);
}
