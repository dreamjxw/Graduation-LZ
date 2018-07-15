package com.lz.design.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 线程管理
 *
 * @author Xingwu.Jia
 * @date 2018/1/24 16:10
 */
public class ThreadRecycles {

    private static final Logger logger = LoggerFactory.getLogger(ThreadRecycles.class);

    private final static ThreadLocal<ThreadRecycles> local = new ThreadLocal<ThreadRecycles>();

    public static ThreadRecycles init() {
        ThreadRecycles tlr = local.get();
        if (tlr == null)
            local.set(tlr = new ThreadRecycles());
        return tlr;
    }

    public static void release() {
        ThreadRecycles current = local.get();
        if (current != null) {
            current.clear();
            local.remove();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map.Entry<Object, Recyclable>[] recycleSet() {
        ThreadRecycles current = local.get();
        if (current == null) {
            return new Map.Entry[0];
        } else {
            Map.Entry<Object, Recyclable>[] arr = new Map.Entry[current.recycles.size()];
            current.recycles.entrySet().toArray(arr);
            return arr;
        }
    }

    public static void setRecycle(Object key, Recyclable recycle) {
        ThreadRecycles current = local.get();
        if (current == null) {
            current = init();
            logger.error("线程回收器(LocalRecycles)未被成功接管,请确定当前资源是在安全的执行环境(线程池)中被开启.{}:{}"
                    , recycle, Thread.currentThread());
        }
        current.put(key, recycle);
    }

    @SuppressWarnings({"unchecked"})
    public static <E> E getRecycle(Object key) {
        ThreadRecycles current = local.get();
        if (current != null) {
            return (E) current.recycles.get(key);
        } else {
            return null;
        }
    }

    public static void removeRecycle(Object key) {
        ThreadRecycles current = local.get();
        if (current != null) {
            current.recycles.remove(key);
        }
    }


    private LinkedHashMap<Object, Recyclable> recycles = new LinkedHashMap<Object, Recyclable>();
    //private int gid = 0;

    private ThreadRecycles() {
    }

    private void put(Object key, Recyclable recycle) {
        if (recycles.containsKey(key)) {
            throw new IllegalStateException("重复的key:" + key);
        }
        recycles.put(key, recycle);
    }

    void clear() {
        Recyclable[] arr = new Recyclable[recycles.size()];
        recycles.values().toArray(arr);
        recycles.clear();

        for (int i = arr.length - 1; i >= 0; i--) {
            try {
                arr[i].recycle();
                logger.warn("资源回收器捕获到未回收的资源: {}|{}", Thread.currentThread(), arr[i]);
            } catch (Exception t) {
                logger.warn("recycle failed", t);
            }
        }
    }
}
