package nexuslink.charon.sphouse.utils;

import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nexuslink.charon.accountbook.greendao.gen.DaoSession;
import nexuslink.charon.accountbook.greendao.gen.DogBeanDao;
import nexuslink.charon.accountbook.greendao.gen.EatBeanDao;
import nexuslink.charon.sphouse.bean.DogBean;
import nexuslink.charon.sphouse.bean.EatBean;
import nexuslink.charon.sphouse.utils.APP;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/9/24 12:11
 * 修改人：Charon
 * 修改时间：2017/9/24 12:11
 * 修改备注：
 */

public class DataUtil {
    public static Query<DogBean> mDogBeanQuery;
    public static DogBeanDao mDogBeanDao;
    public static Query<EatBean> mEatBeanQuery;
    public static EatBeanDao mEatBeanDao;


    /**
     * 初始化数据库
     */
    public static void initDataBase(){
        DaoSession daoSession = APP.getDaoSession();
        mDogBeanDao = daoSession.getDogBeanDao();
        mDogBeanQuery = mDogBeanDao.queryBuilder().orderAsc(DogBeanDao.Properties.Id).build();
        mEatBeanDao = daoSession.getEatBeanDao();
        mEatBeanQuery = mEatBeanDao.queryBuilder().orderAsc(EatBeanDao.Properties.Id).build();
    }

    /**
     * 添加Dog数据
     * @param name
     * @param birthday
     * @param sex
     * @param weight
     */
    public static void insertDogData(String name, Date birthday, String sex, int weight ) {
        DogBean dogBean = new DogBean(null,name,birthday,sex,weight);
        mDogBeanDao.insert(dogBean);
    }

    /**
     * 添加狗狗喂食信息
     * @param dogId
     * @param foodIntake
     * @param foodTime
     */
    public static void insertEatData(Long dogId, int foodIntake, Date foodTime ) {
        EatBean eatBean = new EatBean(null,dogId,foodIntake,foodTime);
        mEatBeanDao.insert(eatBean);
    }

    /**
     * 根据id删除狗狗信息
     * @param key
     */
    public static void deleteDogByKey(Long key) {
        mDogBeanDao.deleteByKey(key);
        EatBean findEat = mEatBeanDao.queryBuilder().where(EatBeanDao.Properties.DogId.eq(key)).build().unique();
        if (findEat != null) {
            mEatBeanDao.delete(findEat);
        }
    }

    /**
     * 根据id删除狗狗喂食信息
     * @param key
     */
    public static void deleteEatByKey(Long key) {
        mEatBeanDao.deleteByKey(key);
    }

    public static void clearAll() {
        mEatBeanDao.deleteAll();
        mDogBeanDao.deleteAll();
    }

    public static void updateDogName(Long id, String name) {
        DogBean dogBean = mDogBeanDao.load(id);
        dogBean.setName(name);
        mDogBeanDao.update(dogBean);
    }
    public static void updateDogSex(Long id, String sex) {
        DogBean dogBean = mDogBeanDao.load(id);
        dogBean.setSex(sex);
        mDogBeanDao.update(dogBean);
    }
    public static void updateDogBirthday(Long id, Date birthday) {
        DogBean dogBean = mDogBeanDao.load(id);
        dogBean.setBirthday(birthday);
        mDogBeanDao.update(dogBean);
    }
    public static void updateDogWeight(Long id, int weight) {
        DogBean dogBean = mDogBeanDao.load(id);
        dogBean.setWeight(weight);
        mDogBeanDao.update(dogBean);
    }

    public static void updateEatIntake(Long id,int position,int intake) {
        DogBean dogBean = mDogBeanDao.load(id);
        EatBean eatBean = dogBean.getEatBeanList().get(position);
        eatBean.setFoodIntake(intake);
        mEatBeanDao.update(eatBean);
    }
    public static void updateEatTime(Long id,int position ,Date time) {
        DogBean dogBean = mDogBeanDao.load(id);
        EatBean eatBean = dogBean.getEatBeanList().get(position);
        eatBean.setFoodTime(time);
        mEatBeanDao.update(eatBean);
    }

    public static List<DogBean> queryDogList() {
        return mDogBeanQuery.list();
    }

    public static List<EatBean> queryEatList(Long id){
        List<EatBean> beanList = new ArrayList<>();
        for (int i = 0; i < mEatBeanQuery.list().size(); i++) {
            if (mEatBeanQuery.list().get(i).getDogId().equals(id)) {
                beanList.add(mEatBeanQuery.list().get(i));
            }
        }
        return beanList;
    }

}
