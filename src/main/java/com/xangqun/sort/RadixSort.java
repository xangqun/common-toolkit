package com.xangqun.sort;


import java.util.Arrays;

public class RadixSort {

	/**
	 * ȡ��x�ϵĵ�dλ����
	 * @param x ��
	 * @param d �ڼ�λ���ӵ�λ����λ
	 * @return
	 */
	public int digit(long x, long d) {

		long pow = 1;
		while (--d > 0) {
			pow *= 10;
		}
		return (int) (x / pow % 10);
	}

	/**
	 * ��������ʵ�֣�������������������е�λ��¼��count�У��ӵ�0��Ԫ�ص���9��Ԫ����������
	 * ��¼��ǰ�Ƚ�λ��0���ж��ٸ�..��9���ж��ٸ�����������ʱ��ӵ�0��Ԫ�ص���9��Ԫ����������
	 * ��¼��ǰ�Ƚ�λ��9���ж��ٸ�..��0���ж��ٸ�����
	 * @param arr ����������
	 * @param digit �������������λ��
	 * @return
	 */
	public long[] radixSortAsc(long[] arr) {
		//�ӵ�λ����λѭ��
		for (int d = 1; d <= getMax(arr); d++) {
			//��ʱ���飬���������������е�����
			long[] tmpArray = new long[arr.length];
			//λ���������ӵ�0��Ԫ�ص���9��Ԫ������������¼��ǰ�Ƚ�λ��0���ж��ٸ�..��9���ж��ٸ���
			int[] count = new int[10];
			//��ʼͳ��0�ж��ٸ������洢�ڵ�0λ����ͳ��1�ж��ٸ������洢�ڵ�1λ..����ͳ�Ƶ�9�ж��ٸ�
			for (int i = 0; i < arr.length; i++) {
				count[digit(arr[i], d)] += 1;
			}
			/*
			 * ����ĳ�ξ�������ͳ�ƺ���Ϊ��[0, 2, 3, 3, 0, 0, 0, 0, 0, 0]�򾭹��������� ���Ϊ��
			 * [0, 2, 5, 8, 8, 8, 8, 8, 8, 8]��ʵ����ֻ������[0, 2, 5, 8, 0, 0, 0, 0, 0, 0]��
			 * ���������õ�����Ϊ����λ�����ڣ����Ƿֱ��ʾ���£�2��ʾ�Ƚ�λΪ1��Ԫ�ؿ��Դ��������Ϊ1��0��
			 * λ�ã�5��ʾ�Ƚ�λΪ2��Ԫ�ؿ��Դ����4��3��2����(5-2=3)λ�ã�8��ʾ�Ƚ�λΪ3��Ԫ�ؿ��Դ����
			 * 7��6��5����(8-5=3)λ��
			 */
			for (int i = 1; i < 10; i++) {
				count[i] += count[i - 1];
			}

			/*
			 * ע������ֻ�ܴ��������ǰѭ������Ϊ����ʱ���豣����ǰ��������õ� ˳�򣬲�Ӧ�ô�
			 * ��ԭ�����źõ��������ǰ����������ԭ����ǰ���ڵ�����ȥ����Ϊ�ڴ���ĳ��
			 * Ԫ�ص�λ��ʱ��λ�������ǴӴ󵽵�С��count[digit(arr[i], d)]--���ķ�ʽ����
			 * ��ģ����ȴ���������Ԫ�أ��ٴ������С��Ԫ�أ�����������һ��Ԫ�ؿ�ʼ����
			 * ����������һ������[212,213,312]��������մӵ�һ��Ԫ�ؿ�ʼѭ���Ļ���������һ��
			 * �󣨸�λ������󣬵õ�����һ������[312,212,213]����һ�κ���ûʲô���⣬�������
			 * �ӵڶ��ֿ�ʼ���֣��ڶ�������󣬻�õ�[213,212,312]��������λΪ3��Ԫ�ر�Ӧ��
			 * ������󣬵������ڶ��ֺ�ȴ������ǰ���ˣ����Գ���������
			 */
			for (int i = arr.length - 1; i >= 0; i--) {//ֻ�ܴ����һ��Ԫ����ǰ����
				//for (int i = 0; i < arr.length; i++) {//���ܴӵ�һ��Ԫ�ؿ�ʼѭ��
				tmpArray[count[digit(arr[i], d)] - 1] = arr[i];
				count[digit(arr[i], d)]--;
			}

			System.arraycopy(tmpArray, 0, arr, 0, tmpArray.length);
		}
		return arr;
	}

	/**
	 * ��������ʵ�֣��Խ���������������е�λ��¼��count�У��ӵ�0��Ԫ�ص���9��Ԫ����������
	 * ��¼��ǰ�Ƚ�λ��0���ж��ٸ�..��9���ж��ٸ�����������ʱ��ӵ�0��Ԫ�ص���9��Ԫ����������
	 * ��¼��ǰ�Ƚ�λ��9���ж��ٸ�..��0���ж��ٸ�����
	 * @param arr ����������
	 * @return
	 */
	public long[] radixSortDesc(long[] arr) {
		for (int d = 1; d <= getMax(arr); d++) {
			long[] tmpArray = new long[arr.length];
			//λ���������ӵ�0��Ԫ�ص���9��Ԫ������������¼��ǰ�Ƚ�λ��9���ж��ٸ�..��0���ж��ٸ���
			int[] count = new int[10];
			//��ʼͳ��0�ж��ٸ������洢�ڵ�9λ����ͳ��1�ж��ٸ������洢�ڵ�8λ..����ͳ��
			//��9�ж��ٸ������洢�ڵ�0λ
			for (int i = 0; i < arr.length; i++) {
				count[9 - digit(arr[i], d)] += 1;
			}

			for (int i = 1; i < 10; i++) {
				count[i] += count[i - 1];
			}

			for (int i = arr.length - 1; i >= 0; i--) {
				tmpArray[count[9 - digit(arr[i], d)] - 1] = arr[i];
				count[9 - digit(arr[i], d)]--;
			}

			System.arraycopy(tmpArray, 0, arr, 0, tmpArray.length);
		}
		return arr;
	}

	private int getMax(long[] array) {
		int maxlIndex = 0;
		for (int j = 1; j < array.length; j++) {
			if (array[j] > array[maxlIndex]) {
				maxlIndex = j;
			}
		}
		return String.valueOf(array[maxlIndex]).length();
	}

	public static void main(String[] args) {
		long[] ary = new long[] { 123, 321, 132, 212, 213, 312, 21, 223 };
		RadixSort rs = new RadixSort();
		System.out.println("�� - " + Arrays.toString(rs.radixSortAsc(ary)));

		ary = new long[] { 123, 321, 132, 212, 213, 312, 21, 223 };
		System.out.println("�� - " + Arrays.toString(rs.radixSortDesc(ary)));
	}
}
