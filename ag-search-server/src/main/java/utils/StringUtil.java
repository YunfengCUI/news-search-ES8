package utils;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	/**
	 *
	 * @param type
	 *            1代表SqlServer 2代表Oracle 3代表Mysql 4代表Access 5代表Sqlite 6代表Sybase
	 *            7代表Dbase 8代表Foxpro 10代表Hive
	 * @param ip
	 *            数据库连接ip地址
	 * @param DataBaseName
	 *            数据库名称
	 * @param Port
	 *            端口
	 * @return 返回Url、Driver列表
	 */
	public static List<String> getDBDriver(String type, String ip, String DataBaseName, String Port) {
		List<String> list = new ArrayList<String>();
		if (type.equals("1")) // SqlServer
		{
			String Url = "jdbc:sqlserver://" + ip + ":" + Port + ";" + "DatabaseName=" + DataBaseName;// 拼接URL
			String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			list.add(Url);// 添加到list列表
			list.add(Driver);
			return list;
		}
		if (type.equals("2")) // Orcal
		{
			String Url = "jdbc:oracle:thin:@" + ip + ":" + Port + ":"+DataBaseName;
			String Driver = "oracle.jdbc.OracleDriver";
			list.add(Url);// 添加到list列表
			list.add(Driver);
			return list;
		}
		if (type.equals("3")) // MySql
		{
			String Url = "jdbc:mysql://" + ip + ":" + Port + "/" + DataBaseName;// 拼接URL
			String Driver = "com.mysql.jdbc.Driver";
			list.add(Url);// 添加到list列表
			list.add(Driver);
			return list;
		}
		if (type.equals("4")) // Access
		{
			return null;
		}
		if (type.equals("5")) // Sqllite
		{
			return null;
		}
		if (type.equals("6")) // Sybase
		{
			return null;
		}
		if (type.equals("7")) // Dbase
		{
			return null;
		}
		if (type.equals("8")) // Foxpro
		{
			return null;
		}
		if (type.equals("9")) // Foxpro
		{
			String Url = "jdbc:oscar://" + ip + ":" + Port + "/" + DataBaseName;// 拼接URL
			String Driver = "com.oscar.Driver";
			list.add(Url);// 添加到list列表
			list.add(Driver);
			return list;
		}
		if (type.equals("10")) // Hive
		{
			return null;
		}
		return null;
	}

	/**
	 * 解析时间格式 ZhangSiWei
	 *
	 * @param Excute_Type
	 *            0为每日执行 1为周日执行
	 * @param interval_type
	 *            间隔类型 -1为日 0为周 1为月
	 * @param inteval_value
	 *            具体每个月的哪天执行
	 * @param task_time
	 *            具体执行时间
	 * @return 返回解析过后的时间格式
	 * @throws Exception
	 */
	public static String AnalyticalTime(String Excute_Type, String interval_type, String inteval_value,
			String task_time) throws Exception {
		Date time = null;
		Calendar now = Calendar.getInstance();
		try {
			now.clear();
			time = StringUtil.str2datetime(task_time);
			now.setTime(time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		int YEAR = now.get(Calendar.YEAR);
//		int MONTH = now.get(Calendar.MONTH) + 1;
//		int Day = now.get(Calendar.DAY_OF_MONTH);
		int HOUR = now.get(Calendar.HOUR_OF_DAY);
		int MINUTE = now.get(Calendar.MINUTE);
		int SECOND = now.get(Calendar.SECOND);
		if (Excute_Type.equals("0")) // 每日执行 执行解析task_time
		{
			// 0 15 10 ? * * 每天10点15分触发 每天执行时间书写格式
			return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "*";
		}
		if (Excute_Type.equals("1")) // 周期执行 如果是周期执行则判断间隔类型
		{
			if (interval_type.equals("-1")) // 如果是-1则为每日执行
			{
				return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "*";
			}

			if (interval_type.equals("0")) // 如果是0则为每周执行
			{
				if (inteval_value.equals("1")) {
					return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "MON";
				}
				if (inteval_value.equals("2")) {
					return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "TUE";
				}
				if (inteval_value.equals("3")) {
					return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "WED";
				}
				if (inteval_value.equals("4")) {
					return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "THU";
				}
				if (inteval_value.equals("5")) {
					return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "FRI";
				}
				if (inteval_value.equals("6")) {
					return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "SAT";
				}
				if (inteval_value.equals("7")) {
					return SECOND + " " + MINUTE + " " + HOUR + " " + "? " + "* " + "SUN";
				}
			}
			if (interval_type.equals("1")) // 如果是1则每月执行
			{
				// 0 15 10 15 * ? 每月15号上午10点15分触发
				return SECOND + " " + MINUTE + " " + HOUR + " " + inteval_value + " " + "* " + "?";
			}
		}
		return "";
	}

	/**
	 * 将String字符串转换为java.sql.Timestamp格式日期,用于数据库保存
	 *
	 * @param strDate
	 *            表示日期的字符串
	 * @param dateFormat
	 *            传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
	 * @return java.sql.Timestamp类型日期对象（如果转换失败则返回null）
	 */
	public static Timestamp strToSqlDate(String strDate) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		date = sf.parse(strDate);
		Timestamp dateSQL = new Timestamp(date.getTime());
		return dateSQL;
	}

	/**
	 *
	 * @return
	 */
	public static String dateonly2str(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * jsy 2012-05-08 add
	 *
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String dateonly2strSplit(Date date) throws Exception {//
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public static String date2str(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(date);
	}

	public static Date dateTimeStamp(Date date) throws Exception {
		if (date == null)
			return new Timestamp(new Date().getTime());
		return new Timestamp(date.getTime());
	}

	public static String datetime2str(Date date) throws Exception {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}


	/**
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Date str2date(String str) throws Exception {
		if (str == null || str.isEmpty())
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.parse(str);
	}

	public static Date str2dateDd(String str) throws Exception {
		if (str == null || str.isEmpty())
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(str);
	}

	public static Date str2datetime(String str) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(str);
	}

	public static Date str2date(String str, String style) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.parse(str);
	}

	public static String long2str(long l) throws Exception {
		Long ll = l;
		return ll.toString();
	}

	public static String int2str(int i) throws Exception {
		Integer ii = i;
		return ii.toString();
	}

	public static Date long2date(long str) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(sdf.format(str));
	}

	public static long date2long(Date str) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(sdf.format(str)).getTime();
	}

	/**
	 *
	 * 字符串转换成十六进制字符串
	 *
	 */

	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();

		StringBuilder sb = new StringBuilder("");

		byte[] bs = str.getBytes();

		int bit;

		for (int i = 0; i < bs.length; i++) {

			bit = (bs[i] & 0x0f0) >> 4;

			sb.append(chars[bit]);

			bit = bs[i] & 0x0f;

			sb.append(chars[bit]);

		}

		return sb.toString();

	}

	/**
	 *
	 * 十六进制转换字符串
	 *
	 */

	public static String hexStr2Str(String hexStr) {

		String str = "0123456789ABCDEF";

		char[] hexs = hexStr.toCharArray();

		byte[] bytes = new byte[hexStr.length() / 2];

		int n;

		for (int i = 0; i < bytes.length; i++) {

			n = str.indexOf(hexs[2 * i]) * 16;

			n += str.indexOf(hexs[2 * i + 1]);

			bytes[i] = (byte) (n & 0xff);

		}

		return new String(bytes);

	}

	/**
	 * bytes转换成十六进制字符串
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 * bytes转换成十六进制字符串
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	/**
	 * String的字符串转换成unicode的String
	 */
	public static String stringToUnicode(String strText) throws Exception {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128) {
				strRet += "\\u" + strHex;
			} else {
				// 低位在前面补00
				strRet += "\\u00" + strHex;
			}
		}
		return strRet;
	}

	/**
	 * unicode的String转换成String的字符串
	 */
	public static String unicodeToString(String hex) {
		int t = hex.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = hex.substring(i * 6, (i + 1) * 6);
			// 高位需要补上00再转
			String s1 = s.substring(2, 4) + "00";
			// 低位直接转
			String s2 = s.substring(4);
			// 将16进制的string转为int
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// 将int转换为字符
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}

	public static boolean stringToBool(String s) {
		if (s.toUpperCase().compareTo("FALSE") == 0)
			return false;
		else if (s.isEmpty())
			return false;
		else if (s.compareTo("0") == 0)
			return false;
		return true;
	}

	public static int stringToInt(String s) {
		if (s == null || s.isEmpty())
			return 0;
		try {
			int i = Integer.parseInt(s);
			return i;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static long stringToLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			return 0;
		}
	}

	public static double stringToDouble(String s) {
		if (s == null || s.isEmpty())
			return 0;
		try {
			double d = Double.parseDouble(s);
			return d;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String doubleToString(double d) {
		String s = Double.toString(d);
		while (s.length() > 1
				&& ((s.charAt(s.length() - 1) == '0' && s.indexOf(".") != -1) || s.charAt(s.length() - 1) == '.')) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	public static String currencyToString(double d) {
		String s = String.format("%.2f", d);
		return s;
	}

	public static String getStringKey(int index, int pos) {
		Integer i = index;
		Integer p = pos;
		return i.toString() + "," + p.toString();
	}

	public static String getStringKey(long id, int pos) {
		Long l = id;
		Integer p = pos;
		return l.toString() + "," + p.toString();
	}


	public static boolean isEmpty(String str) {
		return ((str == null) || (str.trim().length() == 0));
	}

	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.trim().length() > 0));
	}

	public static File[] sortFiles(File[] fileList) throws Exception {
		File temp = null;
		for (int i = 0; i < fileList.length - 1; i++) {
			for (int j = i + 1; j < fileList.length; j++) {
				File second = fileList[j];
				if (fileList[i].lastModified() > fileList[j].lastModified()) {
					temp = fileList[i];
					fileList[i] = second;
					fileList[j] = temp;
				}
			}
		}
		return fileList;
	}

	public static String getGuuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取ip地址
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static InetAddress getLocalHostLANAddress() throws Exception {
		try {
			InetAddress candidateAddress = null;
			// 遍历所有的网络接口
			for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// 在所有的接口下再遍历IP
				for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						if (inetAddr.isSiteLocalAddress()) {
							// 如果是site-local地址，就是它了
							return inetAddr;
						} else if (candidateAddress == null) {
							// site-local类型的地址未被发现，先记录候选地址
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress;
			}
			// 如果没有发现 non-loopback地址.只能用最次选的方案
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			return jdkSuppliedAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getIpAddress() throws Exception {
		InetAddress a = getLocalHostLANAddress();
		String ip = a + "";
		String strIp = "";
		if (ip.indexOf("/") != -1) {
			strIp = ip.substring(1, ip.length());
		} else {
			strIp = ip;
		}
		return strIp;
	}


	@SuppressWarnings("resource")
	public static String fileSile(File file) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		FileInputStream fis = new FileInputStream(file);
		 //计算文件大小
        long oldSize = fis.available();
        String size = "";
        if(oldSize/(1024*1024)>0){
        	size = df.format((float)oldSize/(1024*1024)) +"M";
        }else{
        	if(oldSize/1024>0){
            	//格式化小数
            	size = df.format((float)oldSize/1024) +"Kb";
            }else{
            	size += "B";
            }
        }
		return size;
	}

	/**
	 * 解析自动执行任务时间格式 ZhangSiWei
	 *
	 * @param excuteType
	 *            0为每日执行 1为周期执行
	 * @param intervalType
	 *            间隔类型 -1为日 0为周 1为月2季度执行3年执行
	 * @param intevalValue
	 *            具体每个月的哪天执行
	 * @param taskTime
	 *            具体执行时间
	 * @return 返回解析过后的时间格式
	 * @throws Exception
	 */
	public static String analyticalTime(String excuteType, String intervalType, String intevalValue,String taskTime) throws Exception {
		Date time = null;
		Calendar now = Calendar.getInstance();
		try {
			now.clear();
			time = StringUtil.str2datetime(taskTime);
			now.setTime(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);*/
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		if (excuteType.equals("0")) { // 每日执行 执行解析task_time
			// 0 15 10 ? * * 每天10点15分触发 每天执行时间书写格式
			return second + " " + minute + " " + hour + " " + "? " + "* " + "*";
		}
		if (excuteType.equals("1")) { // 周期执行 如果是周期执行则判断间隔类型
			if (excuteType.equals("-1")) { // 如果是-1则为每日执行
				return second + " " + minute + " " + hour + " " + "? " + "* " + "*";
			}
			if (intervalType.equals("0")) {// 如果是0则为每周执行
				if (intevalValue.equals("1")) {
					return second + " " + minute + " " + hour + " " + "? " + "* " + "MON";
				}
				if (intevalValue.equals("2")) {
					return second + " " + minute + " " + hour + " " + "? " + "* " + "TUE";
				}
				if (intevalValue.equals("3")) {
					return second + " " + minute + " " + hour + " " + "? " + "* " + "WED";
				}
				if (intevalValue.equals("4")) {
					return second + " " + minute + " " + hour + " " + "? " + "* " + "THU";
				}
				if (intevalValue.equals("5")) {
					return second + " " + minute + " " + hour + " " + "? " + "* " + "FRI";
				}
				if (intevalValue.equals("6")) {
					return second + " " + minute + " " + hour + " " + "? " + "* " + "SAT";
				}
				if (intevalValue.equals("7")) {
					return second + " " + minute + " " + hour + " " + "? " + "* " + "SUN";
				}
			}
			if (intervalType.equals("1")) { // 如果是1则每月执行
				// 0 15 10 15 * ? 每月15号上午10点15分触发
				return second + " " + minute + " " + hour + " " + intevalValue + " " + "* " + "?";
			}
		}
		return "";
	}

	//流水号生成
    public static String getStreamNumber(){
    	 Date currentTime = new Date();
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
         String dateString = formatter.format(currentTime);
         int x=(int)((Math.random()*9+1)*1000);
         String streamNumber =dateString + x;
         return streamNumber;
    }

	public static String databaseDate(String dateStr) throws Exception {
		//String date = "Thu Jun 27 08:58:21 CST 2019";
		String date = dateStr;
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		Date d = sdf.parse(date);
		String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		return formatDate;
	}/**
	 * @Description: 生分证加密，加密中间6位
	 * @param idc
	 * @return
	 * @author JiangSiYu 2019年1月22日上午11:26:15
	 */
	public static String getEncryptColu(String idc) {
		String returnStr ="";
		if(StringUtil.isNotEmpty(idc)) {
			int istart = idc.length()/3;
			int iend = idc.length()/3;

			//  String strP = "(\\w{"+ istart +"})(\\w+)(\\w{"+iend+"})";//\w相当于a-zA-Z0-9_，匹配单词字符；
			String strP = "(\\S{"+ istart +"})(\\S+)(\\S{"+iend+"})";//匹配所有字符
			Pattern p = Pattern.compile(strP);
			Matcher m = p.matcher(idc);
			int ivau = idc.length()-istart-iend;

			String encryptStr ="";
			if(ivau>=0){
				for(int i=0;i<ivau;i++) {
					encryptStr+="*";
				}
			}
			String starStr="$1";
			String sendStr="$3";
			String string =starStr+encryptStr+sendStr;
			returnStr = m.replaceAll(string);
		}

		return returnStr;
	}

	/**
	 * @Description: 生分证加密，加密中间4位
	 * @param idc
	 * @return
	 * @author JiangSiYu 2019年1月22日上午11:26:15
	 */
	public static String getIDC4(String idc) {

		Pattern p = Pattern.compile("(\\w{7})(\\w+)(\\w{7})");
		Matcher m = p.matcher(idc);

		String returnStr = m.replaceAll("$1****$3");
		return returnStr;
	}

	/**
	 * @Description: yyyy-MM-dd HH:mm:ss.SSS
	 * @param date
	 * @return
	 */
	public static String date2str(Date date, String style)  {
		if (date == null || style == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		return sdf.format(date);
	}
}
