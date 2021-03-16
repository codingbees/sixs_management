package com.ray.util;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ray.common.model.Dicts;

public class Commen {
	public static String getLoginTimes() {
		try {
			return com.jfinal.plugin.redis.Redis.use("test").getCounter("loginTimes").toString();
		} catch (Exception e) {
		}
		return "0";
	}

	public static String getThisMonthFirstDay() {
		java.text.SimpleDateFormat sm = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar calstr = Calendar.getInstance();
		calstr.add(2, 0);
		calstr.set(5, 1);
		String first = sm.format(calstr.getTime());
		return first;
	}

	public static String getThisMonthLastDay() {
		java.text.SimpleDateFormat sm = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar calast = Calendar.getInstance();
		calast.set(5, calast.getActualMaximum(5));
		String last = sm.format(calast.getTime());
		return last;
	}

	/**
	 * 获取指定月天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 判断审核日期是否是当前天数
	 */
	public static boolean checkdate(String dates, Integer day) {
		String[] examine_dates = dates.split(",");
		for (int i = 0; i < examine_dates.length; i++) {
			if (day == Integer.valueOf(examine_dates[i].split("_")[1])) {
				return true;
			}
		}
		return false;
	}

	public static String getFirstDayOfNextMonth(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, 1);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("static-access")
	public static String addDate(Date now, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(calendar.DAY_OF_MONTH, days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}

	public static void main(String[] args) {
		System.out.println(getFirstDayOfNextMonth("2019-1", "yyyy-MM"));
	}

	/**
	 * 获取字典表-返回name
	 * 
	 * @param value
	 *            值
	 * @param object
	 *            表名
	 * @param field
	 *            字段名
	 * @return
	 */
	public static String getDict(Object value, String object, String field) {
		Dicts dicts = Dicts.dao.findFirst("select name from dicts where value = " + value + " and object = '" + object
				+ "' and field = '" + field + "'");
		if(dicts !=null){
			return dicts.getName();
		}else{
			return "";
		}
	}

	/**
	 * 获取字典表-返回list
	 * 
	 * @param value
	 *            值
	 * @param object
	 *            表名
	 * @param field
	 *            字段名
	 * @return
	 */
	public static List<Dicts> getDictList(String object, String field) {
		List<Dicts> dicts = Dicts.dao
				.find("select * from dicts where object = '" + object + "' and field = '" + field + "'");
		return dicts;
	}
	
	public static boolean isEmptyy(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof Collection) {
			return ((Collection<?>) o).isEmpty();
		}
		if (o instanceof Map) {
			return ((Map<?, ?>) o).isEmpty();
		}
		if (o.getClass().isArray()) {
			return Array.getLength(o) == 0;
		}
		if (o instanceof Iterator) {
			return !((Iterator<?>) o).hasNext();
		}
		if (o instanceof Iterable) {
			return !((Iterable<?>) o).iterator().hasNext();
		}
		if (o instanceof String) {
			if (o.toString().equals("")) {
				return true;
			}
		}
		return false;
	}
}
