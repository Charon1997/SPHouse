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

public interface  IEatView {
    void loading(boolean loading);

    void deleteItem(int position);


    void toEdit(Date time,int foodIntake);
}
