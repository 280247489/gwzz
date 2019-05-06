package com.memory.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtil {
	/**
	 * 阿拉伯数字转为中文
	 *
	 * @param decimal 数字
	 * @return String 中文数字
	 */
	public static String intToChineseNumber(int decimal) {
		String[] numArray = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
		char[] val = String.valueOf(decimal).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < len; ++i) {
			int n = Integer.valueOf(val[i] + "").intValue();
			sb.append(numArray[n]);
		}

		return sb.toString();
	}

	/**
	 * 校验输入字符串是否非空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将输入字符串编码进行转换
	 * 
	 * @param str
	 * @param from
	 * @param to
	 * @return
	 */
	public static String changeEncode(String str, String from, String to) {
		String rt = null;
		try {
			rt = new String(str.getBytes(from), to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}

	/**
	 * 将特殊字符进行转义，防止页面恶意代码注入，如script iframe等
	 * 
	 * @param str
	 * @return 过滤后字符串
	 */
	public static String getHtmlIncodeByString(String str) {
		if (null != str && !"".equals(str)) {
			return str.trim().replace("<", "&lt;").replace(">", "&gt;");
		}
		return null;
	}

	/**
	 * 将字符串解析为适用于SQL"in"的字符串
	 * 
	 * @param str 字符串
	 * @param regx 分隔符
	 * @return 字符串
	 */
	public static String parseString(String str, String regx) {
		StringBuffer tmp = new StringBuffer("");
		if (null != str) {
			String[] tmps = str.split(regx);
			for (int i = 0; i < tmps.length; i++) {
				tmp.append("'");
				tmp.append(tmps[i]);
				tmp.append("'");
				if (i != tmps.length - 1)
					tmp.append(",");
			}
		}
		return tmp.toString();
	} 
	/**
	 * 将异常的栈信息打印到字符串中
	 * @param t 异常
	 * @return String 字符串
	 */
	public synchronized static String printStackTrace(Throwable t) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
		} catch (Exception e) {
		}
		return sw.toString();
	} 
}
