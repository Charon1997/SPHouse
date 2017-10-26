package nexuslink.charon.sphouse.view;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/21 19:53
 * 修改人：Charon
 * 修改时间：2017/10/21 19:53
 * 修改备注：
 */

public interface IForgetView {
    public String getUsername();

    public String getCode();

    public void setCodeButton(String msg);

    public void next(String username,String code);

    public void loading(boolean loading);

    public void buttonClickable(boolean clickable);

    public void toast(String msg);

}
