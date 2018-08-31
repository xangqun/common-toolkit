package com.xangqun.sort;

import java.util.Comparator;

public class HeapSort<E extends Comparable<E>> extends Sort<E> {

	/**
	 * �����㷨��ʵ�֣���������ָ����Ԫ�ؽ�������
	 * @param array �����������
	 * @param from �����￪ʼ����
	 * @param end �ŵ�����
	 * @param c �Ƚ���
	 */
	@Override
	public void sort(E[] array, int from, int end, Comparator<E> c) {
		//������ʼ��
		initialHeap(array, from, end, c);

		/*
		 * �Գ�ʼ�ѽ���ѭ�����Ҵ����һ���ڵ㿪ʼ��ֱ����ֻ�������ڵ�ֹ
		 * ÿ��ѭ���������һ��Ҷ�ӽڵ㣬�ٿ���һ���µ���
		 */
		for (int i = end - from + 1; i >= 2; i--) {
			//���ڵ������һ��Ҷ�ӽڵ㽻��λ�ã��������еĵ�һ��Ԫ�������һ��Ԫ�ػ���
			swap(array, from, i - 1);
			//��������Ҫ���µ�����
			adjustNote(array, 1, i - 1, c);
		}

	}

	/**
	 * ��ʼ����
	 * ����ԭ����Ϊ��7,2,4,3,12,1,9,6,8,5,10,11
	 * ���ʼ��Ϊ��1,2,4,3,5,7,9,6,8,12,10,11
	 * @param arr ��������
	 * @param from ����
	 * @param end ����
	 * @param c �Ƚ���
	 */
	private void initialHeap(E[] arr, int from, int end, Comparator<E> c) {
		int lastBranchIndex = (end - from + 1) / 2;//���һ����Ҷ�ӽڵ�
		//�����еķ�Ҷ�ӽڵ����ѭ�� ���Ҵ���һ����Ҷ�ӽڵ㿪ʼ
		for (int i = lastBranchIndex; i >= 1; i--) {
			adjustNote(arr, i, end - from + 1, c);
		}
	}

	/**
	 * �����ڵ�˳�򣬴Ӹ��������ӽڵ������ڵ���ѡ��һ�����ڵ��븸�ڵ�ת��
	 * @param arr ����������
	 * @param parentNodeIndex Ҫ�����Ľڵ㣬�������ӽڵ�һ����е���
	 * @param len ���Ľڵ���
	 * @param c �Ƚ���
	 */
	private void adjustNote(E[] arr, int parentNodeIndex, int len, Comparator<E> c) {
		int minNodeIndex = parentNodeIndex;
		//�������������i * 2Ϊ���ӽڵ�����
		if (parentNodeIndex * 2 <= len) {
			//������ڵ�С��������ʱ
			if (c.compare(arr[parentNodeIndex - 1], arr[parentNodeIndex * 2 - 1]) < 0) {
				minNodeIndex = parentNodeIndex * 2;//��¼�������Ϊ���ӽڵ�����
			}

			// ֻ�����л�������ǰ���²ſ��������������ٽ�һ�������Ƿ���������
			if (parentNodeIndex * 2 + 1 <= len) {
				//��������������ڵ����
				if (c.compare(arr[minNodeIndex - 1], arr[(parentNodeIndex * 2 + 1) - 1]) < 0) {
					minNodeIndex = parentNodeIndex * 2 + 1;//��¼�������Ϊ���ӽڵ�����
				}
			}
		}

		//����ڸ��ڵ㡢�����ӽڵ������У����ڵ㲻�Ǹ��ڵ�ʱ�轻�����������븸�ڵ㽻���������󶥶�
		if (minNodeIndex != parentNodeIndex) {
			swap(arr, parentNodeIndex - 1, minNodeIndex - 1);
			//�����������Ҫ�ؽ��ѣ�ԭ���ڵ������Ҫ�����³�
			if (minNodeIndex * 2 <= len) {//�Ƿ����ӽڵ㣬ע��ֻ���ж��Ƿ�������������֪��
				adjustNote(arr, minNodeIndex, len, c);
			}
		}
	}

	/**
	* ����
	* @param args
	*/
	public static void main(String[] args) {
		Integer[] intgArr = { 7, 2, 4, 3, 12, 1, 9, 6, 8, 5, 10, 11 };
		HeapSort<Integer> sort = new HeapSort<Integer>();
		HeapSort.testSort(sort, intgArr);
		HeapSort.testSort(sort, null);
	}

}