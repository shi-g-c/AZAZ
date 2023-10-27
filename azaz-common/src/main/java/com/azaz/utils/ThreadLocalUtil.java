package com.azaz.utils;

/**
 * @author c'y'x
 */
public class ThreadLocalUtil {
    private final static ThreadLocal<Long> USER_THREAD_LOCAL = new ThreadLocal<>();

    //存入线程中
    public static void setUserId(Long userId){
        USER_THREAD_LOCAL.set(userId);
    }

    //从线程中获取
    public static Long getUserId(){
        return USER_THREAD_LOCAL.get();
    }

    //清理
    public static void clear(){
        USER_THREAD_LOCAL.remove();
    }
}
