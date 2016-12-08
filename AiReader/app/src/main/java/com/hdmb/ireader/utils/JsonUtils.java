package com.hdmb.ireader.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 17:32
 * 文件    TbReader
 * 描述
 */
public class JsonUtils {

    private static Gson gson = new Gson();

    /**
     *
     * @param json
     * @param type // new TypeToken<List<Person>>(){}.getType()
     * @param <T>
     * @return
     */
    public static <T> T formJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
