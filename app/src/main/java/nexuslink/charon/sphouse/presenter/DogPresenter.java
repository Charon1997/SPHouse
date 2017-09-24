package nexuslink.charon.sphouse.presenter;


import java.util.List;

import nexuslink.charon.sphouse.bean.DogBean;
import nexuslink.charon.sphouse.ui.activities.MainActivity;
import nexuslink.charon.sphouse.utils.DataUtil;
import nexuslink.charon.sphouse.view.IDogEditView;
import nexuslink.charon.sphouse.view.IMainView;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/31 18:40
 * 修改人：Charon
 * 修改时间：2017/8/31 18:40
 * 修改备注：
 */

public class DogPresenter {
    private static List<DogBean> dogList;
    private IMainView mainView;
    private IDogEditView dogEditView;

    public DogPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    public DogPresenter(IDogEditView dogEditView) {
        this.dogEditView = dogEditView;
    }

    public DogPresenter(List<DogBean> dogList, IMainView mainView) {
        this(mainView);
        DogPresenter.dogList = dogList;
    }

    public void save(int position,boolean isEdit) {
        MainActivity.mCurrentPager = position+1;
        if (isEdit){
            Long key = (long) position;
            DataUtil.updateDogName(key,dogEditView.getName());
            DataUtil.updateDogSex(key,dogEditView.getSex());
            DataUtil.updateDogBirthday(key, dogEditView.getBirthday());
            DataUtil.updateDogWeight(key, dogEditView.getWeight());
        } else {
            DataUtil.insertDogData(dogEditView.getName(),dogEditView.getBirthday(),dogEditView.getSex(),dogEditView.getWeight());
        }

//        dogList.get(position).setName(dogEditView.getName());
//        dogList.get(position).setBirthday(dogEditView.getBirthday());
//        dogList.get(position).setSex(dogEditView.getSex());
//        dogList.get(position).setWeight(dogEditView.getWeight());
    }

    public List<DogBean> getDogList() {
        return dogList;
    }


}
