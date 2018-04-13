package com.uzpeng.sign.util;

import com.uzpeng.sign.domain.RoleDO;

import java.util.HashMap;

/**
 * @author serverliu on 2018/4/11.
 */
public class UserMap {
    private static final HashMap<String, RoleDO> idMap = new HashMap<>();

    public static void putId(String auth, RoleDO role){
        idMap.put(auth, role);
    }

    public static RoleDO getId(String auth){
        return idMap.get(auth);
    }

    public static void remove(String auth){
        idMap.remove(auth);
    }
}
