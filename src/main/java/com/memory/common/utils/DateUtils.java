package com.memory.common.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 */
public class DateUtils {

	/**
	 * 获取这个月还剩余多少天，用于一些按天折算的业务
	 * 比如这个月有31天，今天30号，返回值为2
	 * @return
	 */
	public static int getRemainDays(){
		Calendar a = Calendar.getInstance();
		int today = a.get(Calendar.DAY_OF_MONTH);

		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);

		return maxDate - today + 1;
	}

	/**
	 * 获取今天是当月的第几天（几号）
	 * @return
	 */
	public static int getToday(){
		Calendar a = Calendar.getInstance();
		return a.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取这个月总共有多少天
	 * @return
	 */
	public static int getTotalDayOfMonth(){
		Calendar a = Calendar.getInstance();

		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		return a.get(Calendar.DATE);
	}

	/**
	 * 
	 * @param formate
	 *            "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getDate(String formate) {
		DateFormat df = new SimpleDateFormat(formate);
		String date = df.format(new Date());
		return date;
	}
	
	public static String getDefaultCurrentDate(){
		return getDate("yyyy-MM-dd");
	}
    public static String getDayDate(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(calendar.getTime());
        return date;
    }
	public static String getLastDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(calendar.getTime());
		return date;
	}
	
	public static String getLastDateByYMD(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date = df.format(calendar.getTime());
		return date;
	}
	
    public static String getLastMonthByYM(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        DateFormat df = new SimpleDateFormat("yyyyMM");
        String date = df.format(calendar.getTime());
        return date;
    }
	
	public static String getLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		String date = df.format(calendar.getTime());
		return date;
	}
	public static String getCurrentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		return date;
	}

	/**
	 * 
	 * @param
	 *            "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getDate(long timestamp) {
		if (timestamp == -1)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date(timestamp));
		return date;
	}

    public static int getBetweenDays (String begin,String end){
        if (begin == null || end == null) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date beginDate = format.parse(begin);
            Date endDate = format.parse(end);
            return (int)((endDate.getTime() - beginDate.getTime())/(1000*60*60*24));
        }catch (Exception e){
            return 0;
        }
    }

    public static int getBetweenDays2 (String begin,String end){
        if (begin == null || end == null) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        try{
            Date beginDate = format.parse(begin);
            Date endDate = format.parse(end);
            return (int)((endDate.getTime() - beginDate.getTime())/(1000*60*60*24));
        }catch (Exception e){
            return 0;
        }
    }
    
    
    /**
     * 转换日期格式
     * @param date
     * @param oldDateForamt  原日期格式
     * @param dateFormat     目标日期格式
     * @return String new date
     * @throws ParseException
     */
    public static String strToDateFormat(String date,String oldDateForamt,String dateFormat) throws ParseException {
	       SimpleDateFormat formatter = new SimpleDateFormat(oldDateForamt);
	       formatter.setLenient(false);
	       Date newDate= formatter.parse(date);
	       formatter = new SimpleDateFormat(dateFormat);
	       return formatter.format(newDate);
	   }


	public static Date strToDate(String dateTime){
    	Date date = null;
    	try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setLenient(false);
			date =  formatter.parse(dateTime);
		}catch (Exception e){
    		e.printStackTrace();
		}
		return date;
	}

    
    /**
     * 获取两个日期间隔月份
     * @param startTime
     * @param endTime
     * @return
     */
	public static int getTimes(String startTime,String endTime){
		int times = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1;
		try {
			date1 = sdf.parse(startTime);
			Date date2 = sdf.parse(endTime);
			times =(int) Math.round(getDiscrepantDays(date1, date2) / 30);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return times;
	}
	
	/**
	 * 获取两个日期之间的天数
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public static double getDiscrepantDays(Date dateStart, Date dateEnd) {
		return (double) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);
	}
    
    

    public static String addMonth(String beginTime,int num){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String endTime="";
        try{
            Calendar now = Calendar.getInstance();
            now.setTime(format.parse(beginTime));
            now.add(Calendar.MONTH,num);
            endTime=format.format(now.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return endTime;
    }


	/**
	 * calculate the interval between beginTime and endTime
	 * @param beginTime beginTime,format "yyyy-MM-dd HH:mm:ss"
	 * @param endTime endTime,format "yyyy-MM-dd HH:mm:ss"
	 * @return 
	 */
	public static long getDateInterval(String beginTime, String endTime) {
		if (beginTime == null || endTime == null) {
			return 0;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date begin = format.parse(beginTime);
			Date end = format.parse(endTime);
			return (end.getTime() - begin.getTime()) / 1000;
		} catch (ParseException e) {
			return 0;
		}
	}
	
}
