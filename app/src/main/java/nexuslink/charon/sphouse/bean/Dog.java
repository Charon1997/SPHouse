package nexuslink.charon.sphouse.bean;

import java.util.Date;

import nexuslink.charon.sphouse.config.DateGetAge;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/31 16:54
 * 修改人：Charon
 * 修改时间：2017/8/31 16:54
 * 修改备注：
 */

public class Dog {
    private String name;
    private int age;
    private Date birthday;
    private String sex;
    private int weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public Date getBirthday() {
        return birthday;
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
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Dog(String name, Date birthday, String sex, int weight) {
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
}
