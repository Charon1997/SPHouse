package nexuslink.charon.sphouse.view;

import java.util.Date;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/31 18:49
 * 修改人：Charon
 * 修改时间：2017/8/31 18:49
 * 修改备注：
 */

public interface IDogEditView extends BaseView {

    /**
     * 得到名字
     *
     * @return 狗名
     */
    String getName();

    /**
     * 得到狗生日
     *
     * @return 狗生日
     */
    Date getBirthday();

    /**
     * 得到狗狗性别
     *
     * @return 狗狗性别
     */
    String getSex();

    /**
     * 得到狗狗体重
     *
     * @return 狗体重
     */
    int getWeight();

    /**
     * 得到编辑的是哪条狗
     *
     * @return 位置
     */
    int getPosition();
}
