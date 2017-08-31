package nexuslink.charon.sphouse.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/28 17:47
 * 修改人：Charon
 * 修改时间：2017/8/28 17:47
 * 修改备注：
 */

public class Session {
    @SuppressWarnings("unchecked")
    private Map _objectContainer;

    private static Session session;

    //Attention here, DO NOT USE keyword 'new' to create this object.
//Instead, use getSession method.
    @SuppressWarnings("unchecked")
    private Session() {
        _objectContainer = new HashMap();
    }

    public static Session getSession() {

        if (session == null) {
            session = new Session();
            return session;
        } else {
            return session;
        }
    }

    @SuppressWarnings("unchecked")
    public void put(Object key, Object value) {
        _objectContainer.put(key, value);
    }

    public Object get(Object key) {

        return _objectContainer.get(key);
    }

    public void cleanUpSession() {
        _objectContainer.clear();
    }

    public void remove(Object key) {
        _objectContainer.remove(key);
    }
}
