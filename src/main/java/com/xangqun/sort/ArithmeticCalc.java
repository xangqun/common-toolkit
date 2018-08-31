package com.xangqun.sort;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用 LinkedList 计算算术表达试的值
 * @author jzj
 *
 */
public class ArithmeticCalc {
	/**
	 * 提取算术表达式中的括号部分
	 *
	 * @param expression 算术表达式
	 * @return
	 */
	public static String bracket(String expression) {
		Pattern p = Pattern.compile("\\(([^\\(]+?)\\)");

		Matcher m = p.matcher(expression);

		if (m.find()) {
			return m.group(1);
		}
		else {
			return null;
		}
	}

	/**
	 * 分解算术表达式
	 *
	 * @param expression 算术表达式
	 * @return
	 */
	public static LinkedList parse(String expression) {
		LinkedList elemList = new LinkedList();
		//注，减号要放在第一个，放在中间时代表特殊意义
		Pattern p = Pattern.compile("(\\d+)|[-+*/()]");

		Matcher m = p.matcher(expression);
		while (m.find()) {
			elemList.add(m.group(0));
		}
		return elemList;
	}

	/**
	 * 计算不带括号的算术表达式
	 * @param expression 不带括号的算术表达式
	 * @return String 返回计算结果
	 */
	private static String caclExpression(String expression) {
		//分解算术表达式
		LinkedList linkList = parse(expression);

		//先计算乘除
		for (int i = 0; i < linkList.size(); i++) {
			String e = (String) linkList.get(i);
			if ("*".equals(e) || "/".equals(e)) {
				String oprData1 = (String) linkList.remove(i - 1);

				String opr = (String) linkList.remove(i - 1);

				String oprData2 = (String) linkList.remove(i - 1);

				//计算完成后将结果插入到原表达式所在位置
				linkList.add(i - 1, cacl(oprData1, opr, oprData2));

				i = i - 1 - 1;//从结果位置向后计算
			}
		}

		//再算计算加减
		for (int i = 0; i < linkList.size() && linkList.size() > 1; i++) {
			String oprData1 = (String) linkList.remove(i);
			String opr = (String) linkList.remove(i);
			String oprData2 = (String) linkList.remove(i);
			//计算完成后将结果插入到原表达式所在位置

			linkList.add(i, cacl(oprData1, opr, oprData2));
			i = i - 1;//从结果位置向后计算
		}

		return (String) linkList.get(0);
	}

	/**
	 * 两个数计算
	 * @param oprData1 操作数一
	 * @param opr 操作
	 * @param oprData2 操作数二
	 * @return String 返回计算结果
	 */
	private static String cacl(String oprData1, String opr, String oprData2) {
		switch (opr.charAt(0)) {
		case '+':
			return String.valueOf(Integer.parseInt(oprData1)
					+ Integer.parseInt(oprData2));

		case '-':
			return String.valueOf(Integer.parseInt(oprData1)
					- Integer.parseInt(oprData2));
		case '*':
			return String.valueOf(Integer.parseInt(oprData1)
					* Integer.parseInt(oprData2));

		case '/':
			return String.valueOf(Integer.parseInt(oprData1)
					/ Integer.parseInt(oprData2));
		default:
			return "";
		}
	}

	public static void main(String[] args) {
		String expression = "2*2+(2+5/(10-2*(1+1)*2-1))/(7-3*2)*3-2+5";
		String bracketStr;
		//如果算术表达式中有括号，则先计算括号中的表达式
		while ((bracketStr = bracket(expression)) != null) {
			String result = caclExpression(bracketStr);
			System.out.println(bracketStr + "=" + result);
			bracketStr = bracketStr.replaceAll("\\*", "\\\\*").replaceAll(
					"\\+", "\\\\+");
			expression = expression.replaceAll("\\(" + bracketStr + "\\)",
					result);
		}

		System.out.println(expression + "=" + caclExpression(expression));
	}
}
