/**
 * Copyright &copy; 2012-2013 瑞钱通 All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.nice.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author nice
 * @version 2018-7-3
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

	private static String defaultPattern = "yyyy-MM-dd HH:mm:ss";
    public static final String MEDIATIME_PATTERN = "yyyy-MM-dd";
    public static final String MEDIATIME_PATTERN2 = "yyyyMMdd";

	/**
	 * 获取下一天
	 * @return
	 */
	public static String getNextDay(){
		return new DateTime().plusDays(1).toString("yyyy-MM-dd 00:00:00");
	}
	
	/**
	 * 格式化当天日期
	 * @return
	 */
	public static Date formatToday(){
		return DateUtils.formtDate(new Date());
	}
	/**
	 * 格式化给定日期
	 * @param day
	 * @return
	 */
	public static Date formtDate(Date day){
		String d = DateFormatUtils.format(day, defaultPattern);
		SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
		try {
			return sdf.parse(d);
		} catch (ParseException e) {

		}
		return null;
	}
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 * 2015-10-23:修正错误 date==null
	 */
	public static String formatDate(Date date, Object... pattern) {
	  if (date==null)
	    return null;
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param str
	 * @param pattern { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 * @return
	 */
	public static Date parseDate(String str, String pattern) {
		if (str == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
	
    
	public static Date getDateStart(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDateEnd(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据当前时间获取上个月的字符串，格式yyyy-MM
	 * @return
	 */
	public static String getLastMonth(){
		return new DateTime().minusMonths(1).toString("yyyy-MM");
	}
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
		System.out.println(getDaysofMonth());
		Date parseDate = parseDate(getNextDay(),new String[]{"yyyy-MM-dd HH:mm:ss"});
		System.out.println(getNextDay()+">>>>:"+parseDate);
		System.out.println((parseDate.getTime()-new Date().getTime())/1000);
	}
	
	/**
	 * 获取当月有多少天
	 * @return
	 */
	public static int getDaysofMonth(){
		try {
			Date firstDayOfMonth = DateUtils.parseDate(DateUtils.getFirstDayOfMonth(),
					new String[]{"yyyy-MM-dd"});
			Date firstDayOfNextMonth = DateUtils.parseDate(DateUtils.getFirstDayOfNextMonth(),
					new String[]{"yyyy-MM-dd"});
			return diffDays(firstDayOfMonth,firstDayOfNextMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 得到当前月份的第一天
	 * @return
	 */
	public static String getFirstDayOfMonth(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 //获取当前月第一天：
    Calendar c = Calendar.getInstance();    
    c.add(Calendar.MONTH, 0);
    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
    return format.format(c.getTime());
	}
	/**
	 * 得到下一个月份的第一天
	 * @return
	 */
	public static String getFirstDayOfNextMonth(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 //获取当前月第一天：
    Calendar c = Calendar.getInstance();    
    c.add(Calendar.MONTH, 1);
    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号
    return format.format(c.getTime());
	}
	
	/**
	 * 判断投资时间在上一次还款之前还是之后
	 * @param investTime
	 * @param repaymentTime
	 * @return
	 */
	public static Boolean isBeforeLastPayment(Date investTime,Date repaymentTime){
		long lastPayment = new DateTime(repaymentTime).minusMonths(1).getMillis();
		if(investTime.getTime()-lastPayment<0){
			return true;
		}
		return false;
	} 
	
	/**
	* 计算两个日期之间相差的天数
	* @param smdate 较小的时间
	* @param bdate 较大的时间
	* @return 相差天数
	* @throws ParseException
	*/
	public static long daysBetween(Date smdate,Date bdate) {
		long different = 0;
		if(smdate.getTime()<=bdate.getTime()){
			different = bdate.getTime()-smdate.getTime();
		}else{
			different = smdate.getTime() - bdate.getTime();
		}
		return different/(1000 * 60 * 60 * 24);
	} 
	
	/**
	 * 得到上一个月的同一天
	 * @param date
	 * @return
	 */
	public static Date getDayOfLastMonth(Date date){
		return new DateTime(date).minusMonths(1).toDate();
	}
	
	/*
	* 两个日期相隔的天数 
	* @param beginDateStr 
	* @param endDateStr 
	* @return 
	* long 
	*/ 
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return String
	*/
	public static String DateToStr(Date date) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String str = format.format(date);
	   return str;
	} 
	
	/**
	 * 计算两个日期相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDays(Date date1, Date date2){
		return Days.daysBetween(new DateTime(date1), 
				new DateTime(date2)).getDays();
	}

	/**
	 * 当前时间是否在两个日期之间，格式 yyyy-MM-dd
	 * @param begin
	 * @param end
	 * @return
	 */
	public static boolean betweenDays(String begin,String end){
	  String today = new DateTime().toString("yyyy-MM-dd");
	  if (today.compareTo(begin)>=0 && today.compareTo(end)<=0){
	    return true;
	  }
	  return false;
	}
	
	/**
	 * 得到当前月份字符串 格式(例1,2,3...)
	 */
	public static String getMonths() {
		String month =  formatDate(new Date(), "MM");
		switch (month) {
			case "01":
				month="一";
				break;
			case "02":
				month="二";
				break;
			case "03":
				month="三";
				break;
			case "04":
				month="四";
				break;
			case "05":
				month="五";
				break;
			case "06":
				month="六";
				break;
			case "07":
				month="七";
				break;
			case "08":
				month="八";
				break;
			case "09":
				month="九";
				break;
			case "10":
				month="十";
				break;
			case "11":
				month="十一";
				break;
			case "12":
				month="十二";
				break;	
			}	
		return month;
	}
	 /**
   * 
   * @param date  
   * @param count
   * @return
   */
   public static Date getTimeToNext(Date date,int count){
     Calendar cal = Calendar.getInstance();
     cal.setTime(new Date());
     cal.add(Calendar.DATE,count);
     return cal.getTime();
   }
   
   /** 
    * 两个时间相差距离多少天多少小时多少分多少秒 
    * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
    * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
    * @desscription str1 < str2 时，返回  {0, 0, 0, 0}
    * @return long[] 返回值为：{天, 时, 分, 秒} 
    */ 
   public static long[] getDistanceTimes(String str1, String str2) { 
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
       Date one; 
       Date two; 
       long day = 0; 
       long hour = 0; 
       long min = 0; 
       long sec = 0; 
       try { 
           one = df.parse(str1); 
           two = df.parse(str2); 
           long time1 = one.getTime(); 
           long time2 = two.getTime(); 
           long diff ; 
           if(time1 < time2) { 
        	   return new long[]{0, 0, 0, 0};
           } else { 
               diff = time1 - time2; 
           } 
           day = diff / (24 * 60 * 60 * 1000); 
           hour = (diff / (60 * 60 * 1000) - day * 24); 
           min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60); 
           sec = (diff/1000-day*24*60*60-hour*60*60-min*60); 
       } catch (ParseException e) { 
           e.printStackTrace(); 
       } 
       long[] times = {day, hour, min, sec}; 
       return times; 
   } 
   
   /** 
    * 两个时间相差距离多少天多少小时多少分多少秒 
    * @param one 时间参数 1 
    * @param two 时间参数 2  
    * @description one < two 时，返回  {0, 0, 0, 0}
    * @return long[] 返回值为：{天, 时, 分, 秒} 
    */ 
   public static long[] getDistanceTimes(Date one, Date two) { 
       long day = 0; 
       long hour = 0; 
       long min = 0; 
       long sec = 0; 
       long time1 = one.getTime(); 
	   long time2 = two.getTime(); 
	   long diff ; 
	   if(time1 < time2) { 
	       return new long[]{0, 0, 0, 0};
	   } else { 
	       diff = time1 - time2; 
	   } 
	   day = diff / (24 * 60 * 60 * 1000); 
	   hour = (diff / (60 * 60 * 1000) - day * 24); 
	   min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60); 
	   sec = (diff/1000-day*24*60*60-hour*60*60-min*60); 
	   long[] times = {day, hour, min, sec}; 
       return times; 
   } 
   
   /** 
    * 两个时间相差距离几个月多少天 
    * @param one 时间参数 1 
    * @param two 时间参数 2  
    * @description one < two 时，返回 ：0月0天/0天
    * @return String 返回值为：x月x天 
    */ 
   public static String getDistanceTime(Date one, Date two) { 
       long mon = 0; 
       long day = 0; 
       long time1 = one.getTime(); 
	   long time2 = two.getTime(); 
	   long diff ; 
	   if(time1 < time2) { 
	       return "0天";
	   } else {
	       diff = time1 - time2; 
	   }
	   mon = diff / (24L * 60L * 60L * 1000L * 30L);
	   day = diff / (24L * 60L * 60L * 1000L) - mon * 30L;
	   if (mon == 0) {
		   return day + "天";
	   }
	   if (day == 0) {
		   return mon + "月";
	   }
       return mon + "月" + Math.abs(day) + "天"; 
   } 
   
   /**
	 * 判断两个时间大小
	 * 
	 * date1 > date2 返回 1
	 * date1 = date2 返回0
	 * date1 < date2 返回-1
	 * @author nice
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareTo(Date date1,Date date2){
		if(date1.getTime() - date2.getTime() < 0){
			return -1;
		} else if (date1.getTime() - date2.getTime() > 0) {
			return 1;
		} else {
			return 0;
		}
	} 
	
	/**
	 * 判断两个时间大小
	 * 
	 * date1 > date2 返回 1
	 * date1 = date2 返回0
	 * date1 < date2 返回-1
	 * @author wangya
	 * @param date1
	 * @param date2
	 * @param pattern { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 * @return
	 */
	public static int compareTo(String date1,String date2, String pattern){
		if(parseDate(date1, pattern).getTime() - parseDate(date2, pattern).getTime() < 0){
			return -1;
		} else if (parseDate(date1, pattern).getTime() - parseDate(date2, pattern).getTime() > 0) {
			return 1;
		} else {
			return 0;
		}
	} 
	
  /** 
   * 获得指定日期的前一天 
   *  
   * @param specifiedDay 
   * @return 
   * @throws Exception 
   */  
  public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数  
      Calendar c = Calendar.getInstance();  
      Date date = null;  
      try {  
          date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);  
      } catch (ParseException e) {  
          e.printStackTrace();  
      }  
      c.setTime(date);  
      int day = c.get(Calendar.DATE);  
      c.set(Calendar.DATE, day - 1);  

      String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c  
              .getTime());  
      return dayBefore;  
  }  
  
    /**
     * 获取指定日期以后（以前）一定月份数的日期
     *
     * @param strTime 字符串形式的日期，格式可以为：（1）yyyyMMdd （2） yyyy-MM-dd
     * @param count 要增加（减少）的月份数，是负数表示向前的月份数
     * @return String 格式与输入的日期格式相同
     */
    public static String dateAddMonth(String strTime, int count) {
        if (strTime == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();

        Date dtSrc = parseDate(strTime);
        calendar.setTime(dtSrc);

        calendar.add(Calendar.MONTH, count);
        Date dtDst = calendar.getTime();

        if (strTime.indexOf('-') >= 0) // yyyy-MM-dd
        {
            SimpleDateFormat dateFormat2 = new SimpleDateFormat(MEDIATIME_PATTERN);
            return dateFormat2.format(dtDst);
        } else // //yyyyMMdd
        {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(MEDIATIME_PATTERN2);
            return dateFormat1.format(dtDst);
        }
    }

    /**
     * 获取指定日期以后（以前）n个星期的日期
     *
     * @param strTime 字符串形式的日期，格式可以为：（1）yyyyMMdd （2） yyyy-MM-dd
     * @param count 要增加（减少）的星期数，是负数表示向前的星期数
     * @return String 格式与输入的日期格式相同
     */
    public static String dateAddWeek(String strTime, int count) {
        if (strTime == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();

        Date dtSrc = parseDate(strTime);
        calendar.setTime(dtSrc);

        calendar.add(Calendar.WEEK_OF_MONTH, count);
        Date dtDst = calendar.getTime();

        if (strTime.indexOf('-') >= 0) // yyyy-MM-dd
        {
            SimpleDateFormat dateFormat2 = new SimpleDateFormat(MEDIATIME_PATTERN);
            return dateFormat2.format(dtDst);
        } else // //yyyyMMdd
        {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(MEDIATIME_PATTERN2);
            return dateFormat1.format(dtDst);
        }
    }

    /**
     * 获取指定日期以后（以前）一定天数的日期
     *
     * @param strTime 字符串形式的日期，格式可以为：（1）yyyyMMdd （2） yyyy-MM-dd
     * @param count 要增加（减少）的天数，是负数表示向前的天数
     * @return String 格式与输入的日期格式相同
     */
    public static String dateAddDay(String strTime, int count) {
        if (strTime == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();

        Date dtSrc = parseDate(strTime);
        calendar.setTime(dtSrc);

        calendar.add(Calendar.DAY_OF_MONTH, count);
        Date dtDst = calendar.getTime();

        if (strTime.indexOf('-') >= 0) // yyyy-MM-dd
        {
            SimpleDateFormat dateFormat2 = new SimpleDateFormat(MEDIATIME_PATTERN);
            return dateFormat2.format(dtDst);
        } else // //yyyyMMdd
        {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(MEDIATIME_PATTERN2);
            return dateFormat1.format(dtDst);
        }
    }
    
    public static Date getCurrentTime(){
    	return new Date();
    }
    
    /**
     * 判断 now 是否介与 beginTime 、endTime 之间
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param now 判断的时间
     * @return
     */
	public static boolean betweenDate(String beginTime, String endTime, Date now) throws Exception {
		if (beginTime == null || endTime == null) {
			throw new Exception("开始时间beginTime或者结束时间endTime不能为空");
		}
		boolean flag = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
			Date beginDate = sdf.parse(beginTime);
			Date endDate = sdf.parse(endTime);
			if (now == null) {
				now = new Date();
			}
			if ((beginDate.getTime() < endDate.getTime())) {
				if (beginDate.getTime() <= now.getTime() && now.getTime() <= endDate.getTime()) {
					flag = true;
				}
			}
			return flag;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new Exception("开始时间beginTime或者结束时间endTime不能为空", e);
		}
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * @param seconds 精确到秒的字符串
	 * @return
	 * @author nice
	 */
	public static String timeStamp2Date(String seconds, String format) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		if(format == null || format.isEmpty()){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds+"000")));
	}
	/**
	 * 日期格式字符串转换成时间戳
	 * @param date_str 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * @return
	 */
	public static String timeStamp(){
		long time = System.currentTimeMillis();
		String t = String.valueOf(time/1000);
		return t;
	}
}
