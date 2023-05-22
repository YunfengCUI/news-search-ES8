package utils;

import jdk.internal.dynalink.beans.StaticClass;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	/**
	 * 获取当前时间并保存为YYYY-MM-DD HH:MM:SSS
	 * @return
	 */
	public static Timestamp getCurrentTime() {
		Calendar cal = Calendar.getInstance() ;
		Timestamp currentTime = new Timestamp(cal.getTimeInMillis()) ;
		return currentTime;
	}
	
	/**
	 * 获得当前的年月日分秒分别获取
	 */
	public static Integer getNowYear(){
		Calendar cal=Calendar.getInstance();//使用日历类  
		int year=cal.get(Calendar.YEAR);//得到年   
		return year;
	}
	
	/**
	 * 获得当前的年月日分秒分别获取
	 * 
	 */
	public static Integer getNowMonth(){
		Calendar cal=Calendar.getInstance();//使用日历类  
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1 
		return month;
	}
	
	/***
	 * 获取当前时间,格式:YYYY-MM-DD HH-MM-SS.
	 * 
	 * @return
	 */
	public static Timestamp getNow() {
		return new Timestamp(Calendar.getInstance().getTime().getTime());
	}
	
	public static String returnTime() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
        String tsStr = "";  
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
        try {  
            tsStr = sdf.format(ts);;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return tsStr;
	}
	/***
	 * 获取当前时间,格式:YYYY-MM-dd.
	 * 
	 * @return
	 */
	public static Timestamp getNowDay() {
		DateFormat sdf = new SimpleDateFormat(DateUtil.dateFormats[3]);
		String timeStr = sdf.format(new Timestamp(Calendar.getInstance().getTime().getTime()))+" 00:00:00";
		return Timestamp.valueOf(timeStr);
	}

	/** 格式串 */
	public final static String[] dateFormats = { "yyyy", "yyyy-MM", "yyyy-M-d", "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss" ,"yyyyMMdd", "MM"};

	/**
	 * 返回格式化日期
	 * 
	 * @param date 日期
	 * @param parseStr 格式化串
	 * @return 返回格式串
	 */
	public static String formatDate(Date date, String parseStr) {
		String sdate = null;
		SimpleDateFormat f = new SimpleDateFormat(parseStr);
		try {
			sdate = f.format(date);
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
		return sdate;
	}

	/**
	 * String转Date
	 * 返回格式化日期
	 * 
	 * @param date 日期
	 * @param parseStr 格式化串
	 * @return 返回格式串
	 */
	public static Date parseDate(String date, String parseStr) {
		Date ddate = null;
		SimpleDateFormat f = new SimpleDateFormat(parseStr);
		try {
			ddate = f.parse(date);
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
		return ddate;
	}
	
	/**
	 * String转Timestamp
	 * 返回格式化日期
	 * 
	 * @param date 日期
	 * @param parseStr 格式化串
	 * @return 返回格式串
	 */
	public static Timestamp parseTimeStamp(String date, String parseStr) {
		Date ddate = null;
		SimpleDateFormat f = new SimpleDateFormat(parseStr);
		try {
			ddate = f.parse(date);
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
		return new Timestamp(ddate.getTime());
	}
	

	/**
	 * 返回年份列表
	 */
	public static List<String> getYearList() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		int intyear = Integer.parseInt(year);
		int preyear = Integer.parseInt(year) - 1;
		int nexyear = Integer.parseInt(year) + 1;
		int preyear2 = Integer.parseInt(year) - 2;
		int preyear3 = Integer.parseInt(year) - 3;
		int preyear4 = Integer.parseInt(year) - 4;
		int preyear5 = Integer.parseInt(year) - 5;
		int preyear6 = Integer.parseInt(year) - 6;
		int preyear7 = Integer.parseInt(year) - 7;
		int preyear8 = Integer.parseInt(year) - 8;
		int preyear9 = Integer.parseInt(year) - 9;
		int preyear10 = Integer.parseInt(year) - 10;
		List<String> yearList = new ArrayList<String>();
		yearList.add(String.valueOf(nexyear));
		yearList.add(String.valueOf(intyear));
		yearList.add(String.valueOf(preyear));
		yearList.add(String.valueOf(preyear2));
		yearList.add(String.valueOf(preyear3));
		yearList.add(String.valueOf(preyear4));
		yearList.add(String.valueOf(preyear5));
		yearList.add(String.valueOf(preyear6));
		yearList.add(String.valueOf(preyear7));
		yearList.add(String.valueOf(preyear8));
		yearList.add(String.valueOf(preyear9));
		yearList.add(String.valueOf(preyear10));
		return yearList;
	}

	/**
	 * 返回从1980年到系统年月的后三年
	 */
	public static List<String> getAllYearList(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		int startYear = 2000;
		int endYesar = Integer.parseInt(year)+3;
		List<String> yearList = new ArrayList<String>();
		for(int i=endYesar;i>=startYear;i--){
			yearList.add(String.valueOf(i));
		}
		return yearList;
	}
	
	public static List<String> getNewAllYearList(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		int startYear = 2001;
		int endYesar = Integer.parseInt(year)+3;
		List<String> yearList = new ArrayList<String>();
		for(int i=endYesar;i>=startYear;i--){
			yearList.add(String.valueOf(i));
		}
		return yearList;
	}
	
	/**
	 * 返回从1980年到系统年月的后三年
	 */
	public static List<String> getAllJsonYearList(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		int startYear = 1980;
		int endYesar = Integer.parseInt(year)+3;
		List<String> yearList = new ArrayList<String>();
		for(int i=endYesar;i>=startYear;i--){
			yearList.add(String.valueOf(i));
		}
		return yearList;
	}	
	/**
	 * 返回年份列表
	 */
	public static List<String> getYearStringList() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		int intyear = Integer.parseInt(year);
		int preyear = Integer.parseInt(year) - 1;
		int nexyear = Integer.parseInt(year) + 1;
		int preyear2 = Integer.parseInt(year) - 2;
		int preyear3 = Integer.parseInt(year) - 3;
		int preyear4 = Integer.parseInt(year) - 4;
		int preyear5 = Integer.parseInt(year) - 5;
		int preyear6 = Integer.parseInt(year) - 6;
		int preyear7 = Integer.parseInt(year) - 7;
		int preyear8 = Integer.parseInt(year) - 8;
		int preyear9 = Integer.parseInt(year) - 9;
		int preyear10 = Integer.parseInt(year) - 10;
		List<String> yearList = new ArrayList<String>();
		yearList.add(String.valueOf(nexyear));
		yearList.add(String.valueOf(intyear));
		yearList.add(String.valueOf(preyear));
		yearList.add(String.valueOf(preyear2));
		yearList.add(String.valueOf(preyear3));
		yearList.add(String.valueOf(preyear4));
		yearList.add(String.valueOf(preyear5));
		yearList.add(String.valueOf(preyear6));
		yearList.add(String.valueOf(preyear7));
		yearList.add(String.valueOf(preyear8));
		yearList.add(String.valueOf(preyear9));
		yearList.add(String.valueOf(preyear10));
		return yearList;
	}
	/**
	 * @deprecated
	 * 返回前后N年,年份列表
	 * @描述：以后可能要前后4年或5年，请尽量使用此方法
	 * @yearNum  前后年份数
	 * @date:2010.8.30
	 * @author liull
	 */
	public static List<String> getYearStringList(String yearNum){
		int ye = Integer.parseInt(yearNum);
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		List<String> yearList = new ArrayList<String>();
		for(int i=-ye;i<=ye;i++){
			yearList.add(String.valueOf(Integer.parseInt(year)+i));
		}
		return yearList;
	}
	
	/**
	 * 返回包含当前年在内的前N年
	 *
	 * @param yearNum
	 * @return 
	 */
	public static List<String> getPreNYear(String yearNum){
		int ye = Integer.parseInt(yearNum)-1;
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		List<String> yearList = new ArrayList<String>();
		for(int i=-ye;i<=0;i++){
			yearList.add(String.valueOf(Integer.parseInt(year)+i));
		}
		return yearList;
	}

	/**
	 * 返回当前年
	 * 
	 */
	public static String getCurrentYear() {
		String year = "";
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		year = df.format(new Date());
		return year;
	}

	/**
	 * 返回年份列表方便combo获取数据
	 */
	public static List<String> getYearStringListJson(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		String year = df.format(date);
		int intyear = Integer.parseInt(year);
		int preyear = Integer.parseInt(year) - 1;
		int nexyear = Integer.parseInt(year) + 1;
		List<String> yearList = new ArrayList<String>();
		yearList.add("{value:" + String.valueOf(preyear) + "}");
		yearList.add("{value:" + String.valueOf(intyear) + "}");
		yearList.add("{value:" + String.valueOf(nexyear) + "}");
		return yearList;
	}
	
	/**
	 * 返回上一年
	 * 
	 * @param year 当前年
	 * @return preDate 上一年
	 */
	public static Date getPreYear(int year) {
		int preyear = year - 1;
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		Date preDate = null;
		try {
			preDate = df.parse(String.valueOf(preyear));
		} catch (ParseException e) {
			return null;
		}
		return preDate;
	}

	/**
	 * 将字符串年度转换为Date类型
	 *
	 * @param year
	 * @return Date类型年度
	 */
	public static Date getYear(String year) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		Date curDate = null;
		try {
			curDate = df.parse(year);
		} catch (ParseException e) {
			// TODO 注意消除资源(关闭I/O等)
			e.printStackTrace();
		}
		return curDate;
	}

	/**
	 * 返回当前年
	 * 
	 * @param year 当前年
	 * @return 当前年
	 */
	public static Date getYear(int year) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		Date curDate = null;
		try {
			curDate = df.parse(String.valueOf(year));
		} catch (ParseException e) {
			// TODO 注意消除资源(关闭I/O等)
			e.printStackTrace();
		}
		return curDate;
	}

	/**
	 * 返回下一年
	 * 
	 * @param year 当前年
	 * @return nexDate 下一年
	 */
	public static Date getNexYear(int year) {
		int nexyear = year + 1;
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		Date nexDate = null;
		try {
			nexDate = df.parse(String.valueOf(nexyear));
		} catch (ParseException e) {
			// TODO 注意消除资源(关闭I/O等)
			e.printStackTrace();
		}
		return nexDate;
	}

	/**
	 * getCurrentData 的功能描述  返回当前日期对象 
	 *
	 * @return 
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 返回下一年
	 * 
	 */
	public static String getNextYear() {
		String year = "";
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		year = df.format(new Date());
		int nexyear = Integer.parseInt(year) + 1;
		return String.valueOf(nexyear);
	}
	/**
	 * 返回下一年
	 * 
	 */
	public static Date getNextYearDate() {
		String year = "";
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		year = df.format(new Date());
		int nextyear = Integer.parseInt(year) + 1;
		Date nextYearDate = DateUtil.getYear(nextyear) ;
		return nextYearDate;
	}
	
	/**
	 * 返回前一年
	 * 
	 */
	public static String getPreYear() {
		String year = "";
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		year = df.format(new Date());
		int preYear = Integer.parseInt(year) - 1;
		return String.valueOf(preYear);
	}
	
	/**
	 * 返回当前年
	 * 
	 */
	public static Date getCurrentYearDate() {
		String year = "";
		SimpleDateFormat df = new SimpleDateFormat(dateFormats[0]);
		year = df.format(new Date());
		return DateUtil.getYear(year);
	}
	
	/**
	 * 
	 * 计算两个日期之间的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getTwoDay(String date1, String date2) {   
       SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");   
       long day = 0;   
       try {   
         Date date = myFormatter.parse(date1);
         Date mydate = myFormatter.parse(date2);
         day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);   
        } catch (Exception e) {   
        return "";   
        }   
       return day + "";   
    }

	/**
	 * 计算两个日期相隔的秒数
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
    public static String getSecodeByDate(Date startDate,Date endDate){
		long startDateTime = startDate.getTime();
		long endDateTime = endDate.getTime();
		int res = (int)((endDateTime - startDateTime) / 1000);
		return res + "";
	}

	/**
	 *  比较两个日期之间的差值天数
	 * @param endTime
	 * @param beginTime
	 * @return
	 */
	public static int getTwoDayTime(Timestamp endTime, Timestamp beginTime) {  
		DateFormat sdf = new SimpleDateFormat(DateUtil.dateFormats[3]); 
	    String beginTimeStr = sdf.format(beginTime);
		String endTimeStr = sdf.format(endTime);
	    long day = 0;   
		try {
			Date date = Timestamp.valueOf(endTimeStr);
			Date mydate = Timestamp.valueOf(beginTimeStr);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return 0;
		}
		return Integer.parseInt(day+"");
    } 
	

	public static Date addDays(Date date,Integer addDays){
		Calendar calendar = new GregorianCalendar(); 
		if(date!=null){
			calendar.setTime(date); 
			calendar.add(Calendar.DATE,addDays);//把日期往后增加一天.整数往后推,负数往前移动 
			date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		}
	     return date;
	}
	
	
	/**
	 * 获取两个工作日之间的工作天数
	 * @param holidyList
	 * @param adjustList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getWorkDayByDate(List<Date> holidyList,List<Date> adjustList,Date startDate, Date endDate) {
		int workDay = getWorkDays(startDate,endDate);
		boolean flag = workDay>0?true:false;
		workDay = Math.abs(workDay);
		Calendar c1 = Calendar.getInstance();
		if(!flag){
			c1.setTime(startDate);
		}else {
			c1.setTime(endDate);
		}
		int work = workDay;
		for (int i = 0; i < workDay; i++) {
			//判断是否是节假日，如果是节假日则减一天
			if (holidyList.contains(c1.getTime())) {
				work=work-1;
			}
			//判断当前时间是否为周末
			else if (Calendar.SATURDAY == c1.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)){
				//如果是周末，并且不是在调班时间里
				if (!adjustList.contains(c1.getTime())) {
					work=work-1;
				}
			}
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
		}
		if(!flag){
			work = 0-work;
		}
		return work;
	}
	
	public static int getWorkDays(Date startDate,Date endDate) { 
		int result = 0; 
		long diff = endDate.getTime() - startDate.getTime();
		result = (int)(diff / (1000 * 60 * 60 * 24));
		return result; 
	}
}
