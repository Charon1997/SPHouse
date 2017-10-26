package nexuslink.charon.sphouse.biz.home;

import java.util.Date;

import nexuslink.charon.sphouse.bean.DogBean;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/10/26 18:46
 * 修改人：Charon
 * 修改时间：2017/10/26 18:46
 * 修改备注：
 */

public interface IDogBiz {
    /**
     * 通过用户登录信息获取狗狗信息
     * 返回狗的基本信息
     *
     * @param userName
     * @return
     */
    DogBean getDogInformation(String userName);

    /**
     * 保存狗狗信息
     * @param dogId
     * @param dogName
     * @param birthday
     * @param sex
     * @param weight
     */
    void saveDogInformation(int dogId,String dogName, Date birthday,String sex,int weight);

    /**
     * 编辑狗狗信息
     * @param dogId
     * @param dogName
     * @param birthday
     * @param sex
     * @param weight
     */
    void editDogInformation(int dogId,String dogName, Date birthday,String sex,int weight);

    /**
     * 删除狗狗信息
     * @param dogId
     */
    void deleteDogInformation(int dogId);


}
