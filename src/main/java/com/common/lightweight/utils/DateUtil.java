package com.common.lightweight.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created on 2013-8-15
 * <p>
 * Title: [公共类]_[日期比较，日期之间的时间间隔（天数）]
 * </p>
 * <p>
 * Description: [计算任意2 个日期内的工作日、休息日、天数间隔（没有考虑到国定 假日）]
 * </p>
 * <p>
 * Copyright: xuqb(c) 2013
 * </p>
 * <p>
 * Company: xuqb 软件工作室
 * </p>
 * 
 * @developer xuqb [xgood68@163.com]
 * @version 1.0
 */
public class DateUtil {
	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: [计算2 个日期之间的相隔天数,仅日期的相差，忽略时分秒]
	 * </p>
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @developer: [xuqb][xgood68@163.com]
	 * @update: [日期YYYY-MM-DD][更改人姓名][E-mail]
	 */
	public static int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR)
				- d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: [计算2 个日期之间的相隔天数,仅日期的相差，忽略时分秒]
	 * </p>
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDaysBetween(Date date1 , Date date2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return getDaysBetween(cal1, cal2);
	}

	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: 计算开始时间和结束时间相隔工作日天数，开始时间不算在内
	 * </p>
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getWorkingDay(Calendar d1, Calendar d2) {
		int result = -1;
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int charge_start_date = 0; // 开始日期的日期偏移量
		int charge_end_date = 0; // 结束日期的日期偏移量
		// 日期不在同一个日期内
		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {
			charge_start_date = stmp - 1; // 开始日期为星期六和星期日时偏移量为0
		}
		if (etmp != 0 && etmp != 6) {
			charge_end_date = etmp - 1; // 结束日期为星期六和星期日时偏移量为0
		}
		result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7)
				* 5 + charge_start_date - charge_end_date;
		return result;
	}
	
	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: 计算开始时间和结束时间相隔工作日天数，开始时间不算在内
	 * </p>
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getWorkingDay(Date d1, Date d2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return getWorkingDay(c1, c1);
	}

	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: [计算日期的是星期几]
	 * </p>
	 * 
	 * @param date
	 * @return
	 * @developer: [xuqb][xgood68@163.com]
	 * @update: [日期YYYY-MM-DD][更改人姓名][E-mail]
	 */
	public static String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];
	}

	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: [获得日期的下一个星期一的日期]
	 * </p>
	 * 
	 * @param date
	 * @return
	 * @developer: [xuqb][xgood68@163.com]
	 * @update: [日期YYYY-MM-DD][更改人姓名][E-mail]
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: [方法功能中文描述]
	 * </p>
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @developer: [xuqb][xgood68@163.com]
	 * @update: [日期YYYY-MM-DD][更改人姓名][E-mail]
	 */
	public static int getHolidays(Calendar d1, Calendar d2) {
		return getDaysBetween(d1, d2) - getWorkingDay(d1, d2);
	}
	
	
	/**
	 * 将两个日期的间隔转换成人们口语中的时间间隔
	 * @param pastDate 过去的时间
	 * @param today 当前时间
	 * @return
	 */
	public static String getHumanDaysCompare(Date pastDate,Date today) {
		String descDays="";
		int compareDays = getDaysBetween(pastDate, today);
		if(0==compareDays){
			descDays = "今天";
		}else if(compareDays <=7 && compareDays >=1){
			descDays = "一周内";
		}else if(compareDays >7 && compareDays <=30){
			descDays = "一个月内";
		}else if(compareDays >30 && compareDays <=90){
			descDays = "三个月内";
		}else if(compareDays >90 && compareDays <=180){
			descDays = "半年内";
		}else if(compareDays >180 && compareDays <=365){
			descDays = "一年内";
		}else if(compareDays >365){
			descDays = "一年前";
		}
		return descDays;
	}
	
	public static Date getDateByDays(Integer days) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, -days);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		Date date = today.getTime();
		return date;
	}
	
	/**
	 * 根据时间间隔，获取间隔中间的每一天（包含开始和结束时间）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getEachDateList(Date startDate, Date endDate){
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		List<Date> eachDateList = new ArrayList<Date>();
		Calendar temp = d1;
		while(temp.getTime().getTime() <= d2.getTime().getTime()){
			eachDateList.add(temp.getTime());
			temp.add(Calendar.DAY_OF_YEAR, 1);
		}
		return eachDateList;
	}

    /**
     * java8 根据时间间隔，获取间隔中间的每一天（包含开始和结束时间）
     * @param startDate
     * @param endDate
     * @return
     * zouky
     */
    public static List<LocalDate> getEachDateList(LocalDate startDate, LocalDate endDate) {
        //如果传入的开始日期大于结束日期，交换两个日期
        if (startDate.isAfter(endDate)) {
            LocalDate temp = startDate;
            startDate = endDate;
            endDate = temp;
        }
        LocalDate currentDate = startDate;
        List<LocalDate> listLocalDate = new ArrayList<>();
        Long days = ChronoUnit.DAYS.between(startDate, endDate);
        Integer i = 1;
        while (i < days) {
            currentDate = currentDate.plusDays(1);
            listLocalDate.add(currentDate);
            i++;
        }
        return listLocalDate;
    }
	
	/**
	 * Created on 2013-8-15
	 * <p>
	 * Description: [测试方法]
	 * </p>
	 * 
	 * @param args
	 * @throws ParseException 
	 * @developer: [xuqb][xgood68@163.com]
	 * @update: [日期YYYY-MM-DD][更改人姓名][E-mail]
	 */
	public static void main(String[] args) throws ParseException {
		String time1 = "2016-08-01 23:00:00";
		String time2 = "2017-10-12 00:01:00";
		Date date1 = DateUtils.parseDate(time1, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"});
		Date date2 = DateUtils.parseDate(time2, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"});
		List<Date> eachDateList = DateUtil.getEachDateList(date1, date2);
		System.out.println(eachDateList.size());
		for(Date eachDate : eachDateList){
			System.out.println(DateFormatUtils.format(eachDate, "yyyy/MM/dd"));
		}
		/*	Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Date date2 = StringUtils.parseDate(time2);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		
		System.out.println(DateUtil.getDaysBetween(cal1, cal2));
		try {
			String strDateStart = "2015-12-23"; // 起始日期
			String strDateEnd = "2016-01-6"; // 截至日期

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_start = sdf.parse(strDateStart);
			Date date_end = sdf.parse(strDateEnd);
			Calendar cal_start = Calendar.getInstance();
			Calendar cal_end = Calendar.getInstance();
			
			cal_start.setTime(date_start);
			cal_end.setTime(date_end);
			
			System.out.println("星期-->" + DateUtil.getChineseWeek(cal_start)
					+ " 日期-->" + cal_start.get(Calendar.YEAR) + "-"
					+ (cal_start.get(Calendar.MONTH) + 1) + "-"
					+ cal_start.get(Calendar.DAY_OF_MONTH));
			System.out.println("星期-->" + DateUtil.getChineseWeek(cal_end)
					+ " 日期	-->" + cal_end.get(Calendar.YEAR) + "-"
					+ (cal_end.get(Calendar.MONTH) + 1) + "-"
					+ cal_end.get(Calendar.DAY_OF_MONTH));
			System.out.println("两天相隔-->" + DateUtil.getDaysBetween(cal_start, cal_end));
			System.out.println("工作日为(两天间隔工作日天数)-->"
					+ DateUtil.getWorkingDay(cal_start, cal_end));
			System.out.println(" 休息日-->" + DateUtil.getHolidays(cal_start, cal_end));
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
	}
}
