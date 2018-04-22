package com.uzpeng.sign.util;

import com.uzpeng.sign.domain.UserDO;

import java.util.HashMap;

/**
 * @author serverliu on 2018/4/11.
 */
public class UserMap {
    private static final HashMap<String, UserDO> idMap = new HashMap<>();

    public static void putUser(String auth, UserDO role){
        idMap.put(auth, role);
    }

    public static UserDO getUser(String auth){
        return idMap.get(auth);
    }

    public static void remove(String auth){
        idMap.remove(auth);
    }
}
