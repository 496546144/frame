/* 
 * @(#)StringHelper.java    Created on 2013-3-14
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.hbung.utils.other;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 作者　　: 李坤
 * 创建时间: 2016/10/12 13:53
 * <p>
 * 方法功能：字符串一些工具类
 */

public class StringUtils {


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/10/12 14:08
     * <p>
     * 方法功能：去除字符串空字符
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 10:54
     * <p>
     * 方法功能：是否正常的字符串
     */
    public static boolean isEmpty(String str) {
        return (TextUtils.isEmpty(str) || str.equals("null") || str.equals("NULL"));
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 11:00
     * <p>
     * 方法功能：比较两个字符串
     */

    public static boolean isEquals(String actual, String expected) {
        return actual == expected
                || (actual == null ? expected == null : actual.equals(expected));
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 11:01
     * <p>
     * 方法功能：字节转换成合适的单位
     */
    public static String prettyBytes(long value) {
        String args[] = {"B", "KB", "MB", "GB", "TB"};
        StringBuilder sb = new StringBuilder();
        int i;
        if (value < 1024L) {
            sb.append(String.valueOf(value));
            i = 0;
        } else if (value < 1048576L) {
            sb.append(String.format("%.1f", value / 1024.0));
            i = 1;
        } else if (value < 1073741824L) {
            sb.append(String.format("%.2f", value / 1048576.0));
            i = 2;
        } else if (value < 1099511627776L) {
            sb.append(String.format("%.3f", value / 1073741824.0));
            i = 3;
        } else {
            sb.append(String.format("%.4f", value / 1099511627776.0));
            i = 4;
        }
        sb.append(' ');
        sb.append(args[i]);
        return sb.toString();
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/4/29 11:05
     * 方法功能：非空判断处理和转换为String类型
     * dataFilter("aaa")  -> aaa
     * dataFilter(null)    ->"未知"
     * dataFilter("aaa","未知")  -> aaa
     * dataFilter(123.456  ,  2) -> 123.46
     * dataFilter(123.456  ,  0) -> 123
     * dataFilter(123.456  )    -> 123.46
     * dataFilter(56  )    -> "56"
     * dataFilter(true)        ->true
     *
     * @param source 主要对String,Integer,Double这三种类型进行处理
     * @param filter 要改的内容，这个要转换的内容可以不传，
     *               1如传的是String类型就会认为String为空时要转换的内容，不传为空时默认转换为未知，
     *               2如果传入的是intent类型，会认为double类型要保留的小数位数，
     *               3如是传入的是0会认为double要取整
     * @return 把内容转换为String返回
     */

    public static String dataFilter(Object source, Object filter) {
        try {
            if (source != null && !isEmpty(source.toString())) {//数据源没有异常
                if (source instanceof String) {
                    return source.toString().trim();//String 处理
                } else if (source instanceof Double || source instanceof Float) {
                    //小数处理，
                    BigDecimal bd = new BigDecimal(Double.parseDouble(source.toString()));
                    if (filter != null && filter instanceof Integer) {
                        if ((int) filter == 0) {
                            return String.valueOf((int) (bd.setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
                        } else {
                            return String.valueOf(bd.setScale(Math.abs((int) filter), BigDecimal.ROUND_HALF_EVEN).doubleValue());
                        }
                    }
                    return String.valueOf(bd.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
                } else if (source instanceof Integer || source instanceof Boolean) {
                    return source.toString();
                } else {
                    return "未知";
                }
            } else if (filter != null) {//数据源异常 并且filter不为空
                return filter.toString();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "未知";
    }

    public static String dataFilter(Object source) {
        return dataFilter(source, null);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/10/12 14:07
     * <p>
     * 方法功能：返回指定长度的字符串
     */
    public static String isNullToConvert(String str, int maxLenght) {
        return isEmpty(str) ? "未知" : str.substring(0, str.length() < maxLenght ? str.length() : maxLenght);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 11:06
     * <p>
     * 方法功能：小数 四舍五入 19.0->19.0    返回Double
     */
    public static Double roundDouble(double val, int precision) {
        int resQ = (int) Math.round(val * Math.pow(10.0, precision));
        double res = resQ / Math.pow(10.0, precision);
        return res;
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 11:06
     * <p>
     * 方法功能：小数后两位
     */

    public static Double roundDouble(double val) {
        return roundDouble(val, 2);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 11:07
     * <p>
     * 方法功能：小数 四舍五入 19.0->19.0   返回字符串
     */
    public static String roundString(double val, int precision) {
        return String.valueOf(roundDouble(val, precision));

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 11:08
     * <p>
     * 方法功能：从url里面获取最后一个，就是文件名
     */
    public static String getUrlToFileName(String url) {
        if (url != null) {
            String[] splitS = url.split("/");
            if (splitS.length > 0) {
                return splitS[splitS.length - 1];
            }
        }
        return null;
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/6/29 11:09
     * <p>
     * 方法功能：获取钱过滤成字符串
     */
    public static String getMoney(int money) {
        return money <= 0 ? "- -" : String.valueOf(money);
    }

}
