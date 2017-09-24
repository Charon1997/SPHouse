package nexuslink.charon.sphouse.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/27 23:46
 * 修改人：Charon
 * 修改时间：2017/8/27 23:46
 * 修改备注：
 */
@Entity
public class EatBean {
    @Id
    private Long id;

    private Long dogId;

    private int foodIntake;
    private Date foodTime;
    @Generated(hash = 1549537155)
    public EatBean(Long id, Long dogId, int foodIntake, Date foodTime) {
        this.id = id;
        this.dogId = dogId;
        this.foodIntake = foodIntake;
        this.foodTime = foodTime;
    }
    @Generated(hash = 684460570)
    public EatBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDogId() {
        return this.dogId;
    }
    public void setDogId(Long dogId) {
        this.dogId = dogId;
    }
    public int getFoodIntake() {
        return this.foodIntake;
    }
    public void setFoodIntake(int foodIntake) {
        this.foodIntake = foodIntake;
    }
    public Date getFoodTime() {
        return this.foodTime;
    }
    public void setFoodTime(Date foodTime) {
        this.foodTime = foodTime;
    }
}
