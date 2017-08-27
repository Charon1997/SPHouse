package nexuslink.charon.sphouse.bean;

import java.util.Date;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/27 23:46
 * 修改人：Charon
 * 修改时间：2017/8/27 23:46
 * 修改备注：
 */

public class EatBean {
    private int foodIntake;
    private Date foodTime;
    private boolean isChanged;

    public EatBean(int foodIntake, Date foodTime, boolean isChanged) {
        this.foodIntake = foodIntake;
        this.foodTime = foodTime;
        this.isChanged = isChanged;
    }

    public int getFoodIntake() {
        return foodIntake;
    }

    public void setFoodIntake(int foodIntake) {
        this.foodIntake = foodIntake;
    }

    public Date getFoodTime() {
        return foodTime;
    }

    public void setFoodTime(Date foodTime) {
        this.foodTime = foodTime;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
