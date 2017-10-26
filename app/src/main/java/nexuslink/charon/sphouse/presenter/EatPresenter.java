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
    private List<EatBean> eatList;
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

    /**
     * key 狗狗的position，position item 的
     * @param key
     * @param position
     */
    public void deleteItem(long key,int position) {
        eatList.remove(position);
        DataUtil.deleteEatByKey(key,position);
        eatView.deleteItem(position);
    }

    public void toEdit(int position) {
        //Bug
        eatView.toEdit(eatList.get(position).getFoodTime(), eatList.get(position).getFoodIntake(), position, true);
    }

    public void toEdit() {
        eatView.toEdit(false);
    }

    public void save(boolean isEdit,int dogPosition ,int position) {
        MainActivity.mCurrentPager = dogPosition;
        MainActivity main = new MainActivity();
        long key;
        if (MainActivity.mDogSize - 1 == dogPosition) {
            key = main.getDogId();
        } else {
            key = main.getDogId()-1;
        }

        if (isEdit) {
            //编辑
            DataUtil.updateEatIntake(key,position, eatEditView.getIntake());
            DataUtil.updateEatTime(key,position,eatEditView.getTime());
        } else {
            //新建
            DataUtil.insertEatData(key,eatEditView.getIntake(), eatEditView.getTime());
        }
    }

    public int getListSize() {
        return eatList.size();
    }

    public List<EatBean> getEatList() {
        if (eatList != null){
            return eatList;
        }
        else {
            return null;
        }
    }
}
