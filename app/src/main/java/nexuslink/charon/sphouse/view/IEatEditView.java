package nexuslink.charon.sphouse.view;

import java.util.Date;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/29 11:22
 * 修改人：Charon
 * 修改时间：2017/8/29 11:22
 * 修改备注：
 */

public interface IEatEditView extends BaseView {
    /**
     * 得到喂食时间
     *
     * @return 时间
     */
    Date getTime();

    /**
     * 喂食量
     *
     * @return 喂食量
     */
    int getIntake();

    /**
     * 得到哪一个时间
     *
     * @return 位置
     */
    int getPosition();
}
