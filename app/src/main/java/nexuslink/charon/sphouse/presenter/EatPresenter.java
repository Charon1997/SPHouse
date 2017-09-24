package nexuslink.charon.sphouse.presenter;

import java.util.List;

import nexuslink.charon.sphouse.bean.EatBean;
import nexuslink.charon.sphouse.ui.activities.MainActivity;
import nexuslink.charon.sphouse.utils.DataUtil;
import nexuslink.charon.sphouse.view.IEatEditView;
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
    private static List<EatBean> eatList;
    private IEatEditView eatEditView;

    public EatPresenter(IEatView eatView) {
        this.eatView = eatView;
    }

    public EatPresenter(IEatView eatView, List<EatBean> eatList) {
        this.eatView = eatView;
        this.eatList = eatList;
    }

    public EatPresenter(IEatEditView eatEditView) {
        this.eatEditView = eatEditView;
    }

    public void deleteItem(int position) {
        eatList.remove(position);
        DataUtil.deleteEatByKey((long)position);
        eatView.deleteItem(position);
    }

    public void toEdit(int position) {
        eatView.toEdit(eatList.get(position).getFoodTime(), eatList.get(position).getFoodIntake(), position, true);
    }

    public void toEdit() {
        eatView.toEdit(false);
    }

    public void save(boolean isEdit,Long key) {
        MainActivity.mCurrentPager = key.intValue();
        if (isEdit) {
            int position = eatEditView.getPosition();
            DataUtil.updateEatIntake(key,position, eatEditView.getIntake());
            DataUtil.updateEatTime(key,position,eatEditView.getTime());
        } else {
            DataUtil.insertEatData(key,eatEditView.getIntake(), eatEditView.getTime());
        }
    }

    public int getListSize() {
        return eatList.size();
    }

    public List<EatBean> getEatList() {
        if (eatList != null)
            return eatList;
        else return null;
    }
}
