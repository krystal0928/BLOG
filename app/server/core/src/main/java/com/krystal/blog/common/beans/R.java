package com.krystal.blog.common.beans;

import java.util.HashMap;

public class R extends HashMap {

    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final String DATA = "data";

    public static R ok(String msg) {
        R r = new R();
        r.put(CODE, 200);
        r.put(MSG, msg);
        return r;
    }

    public static R okMap(String msg, Object data) {
        R r = new R();
        r.put(CODE, 200);
        r.put(MSG, msg);
        r.put(DATA, data);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }


    @Override
    public R put(Object key, Object value) {
        super.put(key, value);
        return this;
    }
}
