package com.hbung.utils.ui;

import android.app.Activity;

import java.util.Stack;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/3/27 17:54
 * <p>
 * 方法功能：存放Activity的栈
 */

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 获取前台 Activity
     */
    public static Activity getForegroundActivity() {
        return getInstance().currentActivity();
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    // 退出Activity
    public void exitActivity(Activity activity) {
        if (activity != null) {
            if (activityStack.contains(activity)) {
                // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        }
    }

    // 退出Activity
    public void exitActivity(@SuppressWarnings("rawtypes") Class activity) {
        if (activity != null) {
            // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            for (Activity a : activityStack) {
                if (activity.equals(a.getClass())) {
                    activityStack.remove(a);
                    a.finish();
                    a = null;
                    break;
                }
            }

        }
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack != null && !activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.addElement(activity);
    }

    // 退出栈中所有Activity
    public void exitAllActivity() {

        try {
            while (true) {
                Activity activity = currentActivity();
                if (activity == null) {
                    break;
                }
                exitActivity(activity);
            }
        } catch (Exception e) {

        }

    }

    // 退出栈中所有Activity
    public void exitAllActivity(Class... activity) {
        try {
            for (Activity a : activityStack) {
                if (a == null) {
                    continue;
                }
                boolean isfinish = true;
                for (Class c : activity) {
                    if (a.getClass().equals(c)) {
                        isfinish = false;
                        break;
                    }
                }
                if (isfinish)
                    exitActivity(a);
            }
        } catch (Exception e) {

        }

    }

}