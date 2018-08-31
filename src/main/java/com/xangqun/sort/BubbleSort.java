package com.xangqun.sort;


import java.util.Comparator;

/**
 * ð�������㷨
 * @author jzj
 * @date 2009-12-9
 *
 * @param <E>
 */
public class BubbleSort<E extends Comparable<E>> extends Sort<E> {

	/**
	 * �����㷨��ʵ�֣���������ָ����Ԫ�ؽ�������
	 * @param array �����������
	 * @param from �����￪ʼ����
	 * @param end �ŵ�����
	 * @param c �Ƚ���
	 */
	@Override
	public void sort(E[] array, int from, int end, Comparator<E> c) {
		//��array.length - 1�ֱȽ�
		for (int k = 1; k < end - from + 1; k++) {
			//ÿ��ѭ���д����һ��Ԫ�ؿ�ʼ��ǰ���ݣ�ֱ��i=kֹ����i�����ִ�ֹ
			for (int i = end - from; i >= k; i--) {
				//����һ�ֹ��򣨺���Ԫ�ز���С��ǰ��Ԫ�أ�����
				if (c.compare(array[i], array[i - 1]) < 0) {
					//�������Ԫ��С���ˣ���Ȼ�Ǵ��ڻ���С��Ҫ���Ƚ���ʵ���ˣ�ǰ���Ԫ�أ���ǰ�󽻻�
					swap(array, i, i - 1);
				}
			}
		}
	}

	/**
	* ����
	* @param args
	*/
	public static void main(String[] args) {
		Integer[] intgArr = { 7, 2, 4, 3, 12, 1, 9, 6, 8, 5, 11, 10 };
		BubbleSort<Integer> sort = new BubbleSort<Integer>();
		BubbleSort.testSort(sort, intgArr);
		BubbleSort.testSort(sort, null);
	}
}
