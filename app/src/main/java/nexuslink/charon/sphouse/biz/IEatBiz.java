package nexuslink.charon.sphouse.biz;

import java.util.Date;

import nexuslink.charon.sphouse.bean.EatBean;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/21 16:09
 * 修改人：Charon
 * 修改时间：2017/10/21 16:09
 * 修改备注：
 */

public interface IEatBiz {

    /**
     * 获取狗狗吃饭信息
     * @param userName 用户名
     * @param dogId 狗的id
     * @return 狗的所有数据
     */
    EatBean getDogEatInformation(String userName,int dogId);

    /**
     * 保存某一项的狗狗信息
     * @param userName 用户名
     * @param dogId 狗id
     * @param position 该喂食信息所在第几项
     * @param foodTime 喂食时间
     * @param foodIntake 喂食量
     */
    void saveDogEatInformation(String userName, int dogId, int position, Date foodTime,int foodIntake);

    /**
     * 添加某一狗的喂食信息
     * @param userName
     * @param dogId
     * @param foodTime
     * @param foodIntake
     */
    void addDogEatInformation(String userName, int dogId, Date foodTime,int foodIntake);
}
