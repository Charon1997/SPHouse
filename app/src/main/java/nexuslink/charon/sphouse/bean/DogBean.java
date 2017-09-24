package nexuslink.charon.sphouse.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import nexuslink.charon.accountbook.greendao.gen.DaoSession;
import nexuslink.charon.accountbook.greendao.gen.EatBeanDao;
import nexuslink.charon.accountbook.greendao.gen.DogBeanDao;
import nexuslink.charon.sphouse.config.DateGetAge;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/9/24 11:44
 * 修改人：Charon
 * 修改时间：2017/9/24 11:44
 * 修改备注：
 */
@Entity
public class DogBean {
    @Id
    private Long id;
    private String name;
    private int age;
    private Date birthday;
    private String sex;
    private int weight;

    @ToMany(referencedJoinProperty = "dogId")
    private List<EatBean> eatBeanList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1782484275)
    private transient DogBeanDao myDao;

    @Generated(hash = 1851406471)
    public DogBean(Long id, String name, int age, Date birthday, String sex,
            int weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.sex = sex;
        this.weight = weight;
    }

    public DogBean(Long id,String name, Date birthday, String sex, int weight) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.weight = weight;
        try {
            this.age = DateGetAge.getAge(birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Generated(hash = 1018659967)
    public DogBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        try {
            this.age = DateGetAge.getAge(birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 527144716)
    public List<EatBean> getEatBeanList() {
        if (eatBeanList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EatBeanDao targetDao = daoSession.getEatBeanDao();
            List<EatBean> eatBeanListNew = targetDao._queryDogBean_EatBeanList(id);
            synchronized (this) {
                if (eatBeanList == null) {
                    eatBeanList = eatBeanListNew;
                }
            }
        }
        return eatBeanList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 54236868)
    public synchronized void resetEatBeanList() {
        eatBeanList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 661992616)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDogBeanDao() : null;
    }
}
