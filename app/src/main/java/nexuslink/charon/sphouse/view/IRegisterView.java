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

public interface IRegisterView {
    public void loading(boolean loading);

    public String getUsername();

    public String getPassword1();

    public String getPassword2();

    public String getCode();

    public void toast(String msg);

    public void save();

    public void buttonClickable(boolean clickable);

    public void setCodeButton(String msg);

}
