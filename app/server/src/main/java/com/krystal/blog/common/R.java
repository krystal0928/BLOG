package com.krystal.blog.common;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap {

    private static final String CODE = "code";
    private static final String MSG = "msg";

    public static R ok(String msg) {
        R r = new R();
        r.put(CODE, 200);
        r.put(MSG, msg);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }
}
