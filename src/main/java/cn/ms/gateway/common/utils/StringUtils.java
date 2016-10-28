package cn.ms.gateway.common.utils;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author lry
 */
public final class StringUtils {

	/**
	 * 判断字符串是否为空或者长度为0.
	 * 
	 * @param str
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断字符串是否不为空或者长度为0.
	 * 
	 * @param str
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 判断字符串是否为空.
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * 采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isRealNumber(String number) {
		// 可以判断正负、整数小数
		return number.matches("-?[0-9]+.*[0-9]*");
	}

	/**
	 * @Title: getMethodName
	 * @Description: 获取对象类型属性的get方法名
	 * @param propertyName
	 *            属性名
	 * @return String "get"开头且参数(propertyName)值首字母大写的字符串
	 */
	public static String convertToReflectGetMethod(String propertyName) {
		return "get" + toFirstUpChar(propertyName);
	}

	/**
	 * @Title: convertToReflectSetMethod
	 * @Description: 获取对象类型属性的set方法名
	 * @param propertyName
	 *            属性名
	 * @return String "set"开头且参数(propertyName)值首字母大写的字符串
	 */
	public static String convertToReflectSetMethod(String propertyName) {
		return "set" + toFirstUpChar(propertyName);
	}

	/**
	 * @Title: toFirstUpChar
	 * @Description: 将字符串的首字母大写
	 * @param target
	 *            目标字符串
	 * @return String 首字母大写的字符串
	 */
	public static String toFirstUpChar(String target) {
		StringBuilder sb = new StringBuilder(target);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}

	/**
	 * @Title: getFileSuffixName
	 * @Description: 获取文件名后缀
	 * @param fileName
	 *            文件名
	 * @return String 文件名后缀。如：jpg
	 */
	public static String getFileSuffixName(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	/**
	 * 
	 * @Title: checkStringIsNotEmpty
	 * @Description:验证字符串是否不为空
	 * @param stringValue
	 *            传入要验证的字符串
	 * @return boolean true：不为 空 或 不为null; false:值为 空 或 为null
	 */
	public static boolean isNotEmpty(String stringValue) {
		if (null == stringValue || "".equals(stringValue.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: getPackageByPath
	 * @Description 通过指定文件获取类全名
	 * @param classFile
	 *            类文件
	 * @return String 类全名
	 */
	public static String getPackageByPath(File classFile, String exclude) {
		if (classFile == null || classFile.isDirectory()) {
			return null;
		}

		String path = classFile.getAbsolutePath().replace('\\', '/');

		path = path.substring(path.indexOf(exclude) + exclude.length())
				.replace('/', '.');
		if (path.startsWith(".")) {
			path = path.substring(1);
		}
		if (path.endsWith(".")) {
			path = path.substring(0, path.length() - 1);
		}

		return path.substring(0, path.lastIndexOf('.'));
	}

	/**
	 * 获得匹配的字符串
	 * 
	 * @param regex
	 *            匹配的正则
	 * @param content
	 *            被匹配的内容
	 * @param groupIndex
	 *            匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(String regex, String content, int groupIndex) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return get(pattern, content, groupIndex);
	}

	/**
	 * 获得匹配的字符串
	 * 
	 * @param pattern
	 *            编译后的正则模式
	 * @param content
	 *            被匹配的内容
	 * @param groupIndex
	 *            匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(Pattern pattern, String content, int groupIndex) {
		if (null == content) {
			return null;
		}
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(groupIndex);
		}
		return null;
	}

	/**
	 * 取得内容中匹配的所有结果
	 * 
	 * @param pattern
	 *            编译后的正则模式
	 * @param content
	 *            被查找的内容
	 * @param group
	 *            正则的分组
	 * @param collection
	 *            返回的集合类型
	 * @return 结果集
	 */
	public static <T extends Collection<String>> T findAll(Pattern pattern,
			String content, int group, T collection) {
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			collection.add(matcher.group(group));
		}
		return collection;
	}

	/**
	 * 取得内容中匹配的所有结果
	 * 
	 * @param regex
	 *            正则
	 * @param content
	 *            被查找的内容
	 * @param group
	 *            正则的分组
	 * @param collection
	 *            返回的集合类型
	 * @return 结果集
	 */
	public static <T extends Collection<String>> T findAll(String regex,
			String content, int group, T collection) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return findAll(pattern, content, group, collection);
	}

	/**
	 * 判断给定对象是否为"空"(NULL或空(0长度)字符串).
	 * 
	 * @param obj
	 *            对象
	 * @return <ul>
	 *         <li><tt>true</tt> 如果对象为null.
	 *         <li><tt>true</tt> 如果对象是空(0长度)字符串.
	 *         <li><tt>true</tt> 如果对象是数组或集合，但个数为0.
	 *         <li><tt>true</tt> 如果对象是数组或集合，且其中的每个元素都是"空".
	 *         <li><tt>true</tt> 如果对象是Map，返回Map.isEmpty().
	 *         <li><tt>false</tt> 对象非字符串或字符串数组或集合.
	 *         <li><tt>false</tt> 其它情况.
	 *         </ul>
	 */
	public static boolean isEmpty(Object obj) {
		return isEmptyOrBlank(obj, false);
	}

	@SuppressWarnings("rawtypes")
	private static boolean isEmptyOrBlank(Object obj, boolean trim) {
		if (obj == null){
			return true;
		}
		if (obj instanceof String) {
			String ss = (String) obj;
			return (trim ? ss.trim() : ss).length() == 0;
		}
		if (obj instanceof Object[]) {
			Object[] oo = (Object[]) obj;
			for (int i = 0; i < oo.length; i++){
				if (!isEmptyOrBlank(oo[i], trim)){
					return false;
				}
			}
			return true;
		}
		if (obj instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> oo = (Collection<Object>) obj;
			for (Iterator<Object> i = oo.iterator(); i.hasNext();){
				if (!isEmptyOrBlank(i.next(), trim)){
					return false;
				}
			}
			return true;
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		return false;
	}

	/**
	 * 判断一个字符串是否都为数字
	 * 
	 * @param strNum
	 * @return
	 */
	public static boolean isDigit(String strNum) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) strNum);
		return matcher.matches();
	}

	/**
	 * 截取数字
	 * 
	 * @param content
	 * @return
	 */
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 截取非数字
	 * 
	 * @param content
	 * @return
	 */
	public static String splitNotNumber(String content) {
		Pattern pattern = Pattern.compile("\\D+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}
}