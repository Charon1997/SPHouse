package nexuslink.charon.sphouse.utils;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import nexuslink.charon.accountbook.greendao.gen.DaoMaster;
import nexuslink.charon.accountbook.greendao.gen.DaoSession;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/9/24 12:13
 * 修改人：Charon
 * 修改时间：2017/9/24 12:13
 * 修改备注：
 */

public class APP extends Application {
    public static final boolean ENCRYPTED = true;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED?"users-db-encrypted":"users-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }


    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
