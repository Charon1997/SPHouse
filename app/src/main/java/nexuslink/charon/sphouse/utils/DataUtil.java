package nexuslink.charon.sphouse.utils;

import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nexuslink.charon.accountbook.greendao.gen.DaoSession;
import nexuslink.charon.accountbook.greendao.gen.DogBeanDao;
import nexuslink.charon.accountbook.greendao.gen.EatBeanDao;
import nexuslink.charon.sphouse.bean.DogBean;
import nexuslink.charon.sphouse.bean.EatBean;
import nexuslink.charon.sphouse.ui.activities.MainActivity;
import nexuslink.charon.sphouse.utils.APP;

import static nexuslink.charon.sphouse.config.Constant.DOG_MULU;

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
    public static MainActivity main = new MainActivity();

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
        main.saveDogId(main.getDogId()+1);
    }

    /**
     * 添加狗狗喂食信息
     * @param dogId
     * @param foodIntake
     * @param foodTime
     */
    public static void insertEatData(Long dogId, int foodIntake, Date foodTime ) {
        Log.d("123", "insertEatData: dogId"+dogId);
        EatBean eatBean = new EatBean(null,dogId,foodIntake,foodTime);

        mEatBeanDao.insert(eatBean);
    }

    /**
     * 根据id删除狗狗信息
     * @param key
     */
    public static void deleteDogByKey(Long key) {
        mDogBeanDao.deleteByKey(key);
        List<EatBean> findEatList = mEatBeanDao.queryBuilder().where(EatBeanDao.Properties.DogId.eq(key)).build().list();
        if (findEatList != null) {
            for (int i = 0; i < findEatList.size() ; i++) {
                mEatBeanDao.delete(findEatList.get(i));
            }
        }
    }

    /**
     * 根据id删除狗狗喂食信息
     * @param key
     */
    public static void deleteEatByKey(Long key,int position) {
        List<EatBean> findEatList = mEatBeanDao.queryBuilder().where(EatBeanDao.Properties.DogId.eq(key)).build().list();
        Log.d("123", "deleteEatByKey: "+findEatList.size());
        for (int i = 0; i < findEatList.size(); i++) {
            Log.d("123", "deleteEatByKey: "+findEatList.get(i).getId());
        }
        if (findEatList.size() > 0) {
            mEatBeanDao.delete(findEatList.get(position));
        }
    }

    public static void clearAll() {
        mEatBeanDao.deleteAll();
        mDogBeanDao.deleteAll();
        File file = new File(Environment.getExternalStorageDirectory(),
                DOG_MULU);
        deleteDir(file);
        main.saveDogId(0);
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void updateDogName(Long id, String name) {
        DogBean dogBean = mDogBeanDao.queryBuilder().where(DogBeanDao.Properties.Id.eq(id)).unique();
        dogBean.setName(name);
        mDogBeanDao.update(dogBean);
    }

    public static void updateDogSex(Long id, String sex) {
        DogBean dogBean = mDogBeanDao.queryBuilder().where(DogBeanDao.Properties.Id.eq(id)).unique();
        dogBean.setSex(sex);
        mDogBeanDao.update(dogBean);
    }
    public static void updateDogBirthday(Long id, Date birthday) {
        DogBean dogBean = mDogBeanDao.queryBuilder().where(DogBeanDao.Properties.Id.eq(id)).unique();
        dogBean.setBirthday(birthday);
        mDogBeanDao.update(dogBean);
    }
    public static void updateDogWeight(Long id, int weight) {
        DogBean dogBean = mDogBeanDao.queryBuilder().where(DogBeanDao.Properties.Id.eq(id)).unique();
        dogBean.setWeight(weight);
        mDogBeanDao.update(dogBean);
    }

    public static void updateEatIntake(Long id,int position,int intake) {
        List<EatBean> eatBeanList = mEatBeanDao.queryBuilder().where(EatBeanDao.Properties.DogId.eq(id)).build().list();
        EatBean eatBean = eatBeanList.get(position);
        eatBean.setFoodIntake(intake);
        mEatBeanDao.update(eatBean);
    }
    public static void updateEatTime(Long id,int position ,Date time) {
        List<EatBean> eatBeanList = mEatBeanDao.queryBuilder().where(EatBeanDao.Properties.DogId.eq(id)).build().list();
        EatBean eatBean = eatBeanList.get(position);
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

    public static void showDogInf() {
        List<DogBean> dogBeenList = queryDogList();
        MainActivity main = new MainActivity();
        Log.d("123", "showDogInf: "+"mDogId" + main.getDogId());
        for (int i = 0; i <dogBeenList.size(); i++) {
            DogBean dogBean = dogBeenList.get(i);
            Log.d("123", "showDogInf: "+dogBean.getId()+dogBean.getName()  );
            List<EatBean> eatBeanList = queryEatList((long)i+1);
            for (int j = 0; j < eatBeanList.size(); j++) {
                EatBean eatBean = eatBeanList.get(j);
                Log.d("123", "showDogInf: "+eatBean.getId()+"dogid"+eatBean.getDogId()+"intake"+eatBean.getFoodIntake());
            }
        }
    }

    public static long getDogSize() {
        return mDogBeanQuery.list().size();
    }

}
