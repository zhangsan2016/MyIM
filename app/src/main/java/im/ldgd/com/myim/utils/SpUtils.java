package im.ldgd.com.myim.utils;

import android.content.Context;
import android.content.SharedPreferences;

import im.ldgd.com.myim.IMApplication;

/**
 * Created by ldgd on 2017/11/7.
 * SharedPreferences工具类
 */

public class SpUtils {

    private static SharedPreferences mSp;
    private static SpUtils instance = new SpUtils();

    public static SpUtils getInstance() {
        if (mSp == null) {
            mSp = IMApplication.getGlobalContext().getSharedPreferences("im", Context.MODE_PRIVATE );
        }
        return instance;
    }

    public void save(String key, Object value){
        if(value instanceof String){
            mSp.edit().putString(key, (String) value).commit();
        }else if(value instanceof Boolean){
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        }else if (value instanceof Integer){
            mSp.edit().putInt(key, (Integer) value).commit();
        }

    }

    public String getString(String key,String value){
        return  mSp.getString(key,value);
    }

    public Boolean getBoolean(String key,Boolean value){
        return  mSp.getBoolean(key,value);
    }

    public int getInt(String key,int value){
        return  mSp.getInt(key,value);
    }


}
