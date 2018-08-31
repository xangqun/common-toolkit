package com.xangqun.sort;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ʹ�� LinkedList ������������Ե�ֵ
 * @author jzj
 *
 */
public class ArithmeticCalc {
	/**
	 * ��ȡ�������ʽ�е����Ų���
	 *
	 * @param expression �������ʽ
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
	 * �ֽ��������ʽ
	 *
	 * @param expression �������ʽ
	 * @return
	 */
	public static LinkedList parse(String expression) {
		LinkedList elemList = new LinkedList();
		//ע������Ҫ���ڵ�һ���������м�ʱ������������
		Pattern p = Pattern.compile("(\\d+)|[-+*/()]");

		Matcher m = p.matcher(expression);
		while (m.find()) {
			elemList.add(m.group(0));
		}
		return elemList;
	}

	/**
	 * ���㲻�����ŵ��������ʽ
	 * @param expression �������ŵ��������ʽ
	 * @return String ���ؼ�����
	 */
	private static String caclExpression(String expression) {
		//�ֽ��������ʽ
		LinkedList linkList = parse(expression);

		//�ȼ���˳�
		for (int i = 0; i < linkList.size(); i++) {
			String e = (String) linkList.get(i);
			if ("*".equals(e) || "/".equals(e)) {
				String oprData1 = (String) linkList.remove(i - 1);

				String opr = (String) linkList.remove(i - 1);

				String oprData2 = (String) linkList.remove(i - 1);

				//������ɺ󽫽�����뵽ԭ���ʽ����λ��
				linkList.add(i - 1, cacl(oprData1, opr, oprData2));

				i = i - 1 - 1;//�ӽ��λ��������
			}
		}

		//�������Ӽ�
		for (int i = 0; i < linkList.size() && linkList.size() > 1; i++) {
			String oprData1 = (String) linkList.remove(i);
			String opr = (String) linkList.remove(i);
			String oprData2 = (String) linkList.remove(i);
			//������ɺ󽫽�����뵽ԭ���ʽ����λ��

			linkList.add(i, cacl(oprData1, opr, oprData2));
			i = i - 1;//�ӽ��λ��������
		}

		return (String) linkList.get(0);
	}

	/**
	 * ����������
	 * @param oprData1 ������һ
	 * @param opr ����
	 * @param oprData2 ��������
	 * @return String ���ؼ�����
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
		//����������ʽ�������ţ����ȼ��������еı��ʽ
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
