package nexuslink.charon.sphouse.presenter;

import android.widget.ImageView;

import java.util.List;

import nexuslink.charon.sphouse.bean.Dog;
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
    private static List<Dog> dogList;
    private IMainView mainView;
    private IDogEditView dogEditView;

    public DogPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    public DogPresenter(IDogEditView dogEditView) {
        this.dogEditView = dogEditView;
    }

    public DogPresenter(List<Dog> dogList, IMainView mainView) {
        this(mainView);
        DogPresenter.dogList = dogList;
    }

    public void save(int position) {
        dogList.get(position).setName(dogEditView.getName());
        dogList.get(position).setBirthday(dogEditView.getBirthday());
        dogList.get(position).setSex(dogEditView.getSex());
        dogList.get(position).setWeight(dogEditView.getWeight());
    }

    public List<Dog> getDogList() {
        return dogList;
    }


}
