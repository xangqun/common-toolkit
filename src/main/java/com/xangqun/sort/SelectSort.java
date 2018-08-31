package com.xangqun.sort;

import java.util.Comparator;

/**
 * ��ѡ�������㷨
 * @author jzj
 * @date 2009-12-5
 *
 * @param <E>
 */
public class SelectSort<E extends Comparable<E>> extends Sort<E> {

	/**
	 * �����㷨��ʵ�֣���������ָ����Ԫ�ؽ�������
	 * @param array �����������
	 * @param from �����￪ʼ����
	 * @param end �ŵ�����
	 * @param c �Ƚ���
	 */
	@Override
	public void sort(E[] array, int from, int end, Comparator<E> c) {
		int minlIndex;//��С����
		/*
		 * ѭ���������飨��ʵ������Ͻ�Ϊ array.length - 1 ���ɣ���Ϊ�� i= array.length-1
		 * ʱ�����һ��Ԫ�ؾ����������ˣ����Ϊarray.lengthʱ���ڲ�ѭ��������ѭ������ÿ�ּ���
		 * ��һ��Ԫ��Ϊ��СԪ�أ�����ӵ�һԪ�غ���ѡ���ȵ�һ��Ԫ�ظ�СԪ�أ���������СԪ�����һ
		 * ��Ԫ�ؽ���
		 */
		for (int i = from; i <= end; i++) {
			minlIndex = i;//����ÿ�ֵ�һ��Ԫ��Ϊ��СԪ��
			//�Ӽ������СԪ�ص���һԪ�ؿ�ʼѭ��
			for (int j = i + 1; j <= end; j++) {
				//��������бȵ�ǰarray[smallIndex]��СԪ�أ�����¸�Ԫ�ص�������smallIndex��
				if (c.compare(array[j], array[minlIndex]) < 0) {
					minlIndex = j;
				}
			}

			//��ǰֻ�Ǽ�¼��СԪ������������СԪ������ȷ��������ÿ�ֵĵ�һ��Ԫ�ؽ���
			swap(array, i, minlIndex);
		}
	}

	/**
	 * ����
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] intgArr = { 5, 9, 1, 4, 1, 2, 6, 3, 8, 0, 7 };
		SelectSort<Integer> insertSort = new SelectSort<Integer>();
		Sort.testSort(insertSort, intgArr);
		Sort.testSort(insertSort, null);
	}
}