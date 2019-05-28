package com.xml;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static boolean saveUserInfo(Context context,String number,String password){
        SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("username",number);
        editor.putString("pwd",password);
        editor.commit();
        return true;
    }

    public static Map<String, String> getUserInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_PRIVATE);

        String number=sp.getString("username",null);
        String password=sp.getString("pwd",null);
        Map<String,String> userMap=new HashMap<String, String>();
        userMap.put("number",number);
        userMap.put("password",password);
        return userMap;
    }
}
