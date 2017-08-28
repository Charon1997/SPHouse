package nexuslink.charon.sphouse.presenter;

import java.util.List;

import nexuslink.charon.sphouse.bean.EatBean;
import nexuslink.charon.sphouse.view.IEatView;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/28 16:29
 * 修改人：Charon
 * 修改时间：2017/8/28 16:29
 * 修改备注：
 */

public class EatPresenter {
    private IEatView eatView;
    private List<EatBean> eatList;

    public EatPresenter(IEatView eatView, List<EatBean> eatList) {
        this.eatView = eatView;
        this.eatList = eatList;
    }

    public void deleteItem(int position) {
        eatList.remove(position);
        eatView.deleteItem(position);
    }

    public void toEdit(int position) {
        eatView.toEdit(eatList.get(position).getFoodTime(),eatList.get(position).getFoodIntake());
    }

    public int getListSize() {
        return eatList.size();
    }
}
