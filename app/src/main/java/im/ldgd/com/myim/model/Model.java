package im.ldgd.com.myim.model;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import im.ldgd.com.myim.model.bean.UserInfo;
import im.ldgd.com.myim.model.dao.UserAccountDao;
import im.ldgd.com.myim.model.db.DBManager;

/**
 * Created by ldgd on 2017/9/19.
 * 数据模型层全局类
 */

public class Model {

    private Context mContext;
    private static Model model = new Model();

    // 创建一个定长线程池，支持定时及周期性任务执行。
    private ExecutorService executors = Executors.newCachedThreadPool();
    private UserAccountDao userAccountDao;
    private DBManager dbManager;


    // 私有化构造
    private Model() {
    }

    // 获取单例对象
    public static Model getInstance() {
        return model;
    }

    // 获取全局线程池对象
    public ExecutorService getGlobalThreadPool() {
        return executors;
    }


    // 初始化的方法
    public void init(Context context) {
        mContext = context;

        // 创建用户账号数据库的操作类对象
        userAccountDao = new UserAccountDao(mContext);

        // 开启全局监听
        EventListener eventListener = new EventListener(mContext);
    }

    /**
     * 用户登录成功后的处理
     *
     * @param userInfo
     */
    public void loginSuccess(UserInfo userInfo) {

        // 校验userinfo信息
        if (userInfo == null) {
            return;
        }

        // 更新当前登录用户信息
        if (dbManager != null) {
            dbManager.close();
        }

        dbManager = new DBManager(mContext, userInfo.getName());
    }


    public UserAccountDao getUserAccountDao() {
        return userAccountDao;
    }


    public DBManager getDbManager() {
        return dbManager;
    }
}
