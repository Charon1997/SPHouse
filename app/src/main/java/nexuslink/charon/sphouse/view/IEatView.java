package nexuslink.charon.sphouse.view;

import java.util.Date;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/28 16:30
 * 修改人：Charon
 * 修改时间：2017/8/28 16:30
 * 修改备注：
 */

public interface IEatView {

    /**
     * 删除的位置
     *
     * @param position 位置
     */
    void deleteItem(int position);


    /**
     * 编辑喂食信息
     *
     * @param time       时间
     * @param foodIntake 喂食量
     * @param position   信息的位置
     * @param isEdit     是编辑还是新建
     */
    void toEdit(Date time, int foodIntake, int position, boolean isEdit);

    /**
     * 是编辑还是新建的页面
     *
     * @param isEdit 是编辑还是新建
     */
    void toEdit(boolean isEdit);
}
