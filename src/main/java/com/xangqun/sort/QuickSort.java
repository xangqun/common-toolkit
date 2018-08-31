package com.xangqun.sort;

import java.util.Comparator;

/**
 * ���������㷨
 * @author jzj
 * @date 2009-12-9
 *
 * @param <E>
 */
public class QuickSort<E extends Comparable<E>> extends Sort<E> {

	/**
	 * �����㷨��ʵ�֣���������ָ����Ԫ�ؽ�������
	 * @param array �����������
	 * @param from �����￪ʼ����
	 * @param end �ŵ�����
	 * @param c �Ƚ���
	 */
	@Override
	public void sort(E[] array, int from, int end, Comparator<E> c) {
		quickSort(array, from, end, c);
	}

	/**
	 * �ݹ��������ʵ��
	 * @param array ����������
	 * @param low ��ָ��
	 * @param high ��ָ��
	 * @param c �Ƚ���
	 */
	private void quickSort(E[] array, int low, int high, Comparator<E> c) {
		/*
		 * ��������еĵ�ָ��С�ڸ�ָ��ʱѭ�������low=higth˵������ֻ��һ��Ԫ�أ������ٴ���
		 * ���low > higth����˵���ϴ���ŦԪ�ص�λ��pivot����low������higth���������
		 * �·������棬Ҳ���账��
		 */
		if (low < high) {
			//�Է���������������
			int pivot = partition1(array, low, high, c);
			/*
			 * ��pivotΪ�߽磬������ֳ�������[low, pivot - 1]��[pivot]��[pivot + 1, high]
			 * ����[pivot]Ϊ��ŦԪ�أ����账���ٶ�[low, pivot - 1]��[pivot + 1, high]
			 * ���Խ��з��������������һ������
			 */
			quickSort(array, low, pivot - 1, c);
			quickSort(array, pivot + 1, high, c);
		}

	}

	/**
	 * ʵ��һ
	 *
	 * @param array ����������
	 * @param low ��ָ��
	 * @param high ��ָ��
	 * @param c �Ƚ���
	 * @return int ����������λ��
	 */
	private int partition1(E[] array, int low, int high, Comparator<E> c) {
		E pivotElem = array[low];//�Ե�һ��Ԫ��Ϊ����Ԫ��
		//��ǰ�������ָ�������Ԫ��С��Ԫ�أ��տ�ʼʱָ�����࣬Ҳ��С�����С�����Ԫ�صķֽ��
		int border = low;

		/*
		 * ������Ԫ�غ����Ԫ���в���С������Ԫ�ص�����Ԫ�أ������δӵڶ���λ�ô�ǰ������
		 * ע���������ʹ��i���ƶ������ֱ���ƶ�low�Ļ������֪������ı߽��ˣ���������Ҫ
		 * ֪������ı߽�
		 */
		for (int i = low + 1; i <= high; i++) {
			//����ҵ�һ��������Ԫ��С��Ԫ��
			if (c.compare(array[i], pivotElem) < 0) {
				swap(array, ++border, i);//borderǰ�ƣ���ʾ��С������Ԫ�ص�Ԫ��
			}
		}
		/*
		 * ���borderû���ƶ�ʱ˵��˵�������Ԫ�ض�������Ԫ��Ҫ��border��low��ȣ���ʱ��
		 * ͬһλ�ý������Ƿ񽻻���û��ϵ����border�Ƶ���highʱ˵������Ԫ�ض�С������Ԫ�أ���
		 * ʱ������Ԫ�������һ��Ԫ�ؽ������ɣ���low��high���н������������Ԫ���Ƶ��� ������
		 * ����� low <minIndex< high���� ����������Ԫ��ǰ����С������Ԫ�أ����󲿷ִ���
		 * ����Ԫ�أ���ʱ����Ԫ����ǰ�������������һ��С������Ԫ�ؽ���λ�ã�ʹ������Ԫ�ط�����
		 * ��ȷ��λ��
		 */
		swap(array, border, low);
		return border;
	}

	/**
	 * ʵ�ֶ�
	 *
	 * @param array ����������
	 * @param low ����������ָ��
	 * @param high ����������ָ��
	 * @param c �Ƚ���
	 * @return int ����������λ��
	 */
	private int partition2(E[] array, int low, int high, Comparator<E> c) {
		int pivot = low;//����Ԫ��λ�ã������Ե�һ��Ԫ��Ϊ����Ԫ��
		//�˳���������ֻ������ low = high
		while (true) {
			if (pivot != high) {//�������Ԫ���ڵ�ָ��λ��ʱ�������ƶ���ָ��
				//�����ָ��Ԫ��С������Ԫ��ʱ����������Ԫ�ؽ���
				if (c.compare(array[high], array[pivot]) < 0) {
					swap(array, high, pivot);
					//����������Ԫ���ڸ�ָ��λ����
					pivot = high;
				} else {//���δ�ҵ�С������Ԫ�أ����ָ��ǰ�Ƽ�����
					high--;
				}
			} else {//��������Ԫ���ڸ�ָ��λ��
				//�����ָ��Ԫ�ش�������Ԫ��ʱ����������Ԫ�ؽ���
				if (c.compare(array[low], array[pivot]) > 0) {
					swap(array, low, pivot);
					//����������Ԫ���ڵ�ָ��λ����
					pivot = low;
				} else {//���δ�ҵ���������Ԫ�أ����ָ����Ƽ�����
					low++;
				}
			}
			if (low == high) {
				break;
			}
		}
		//��������Ԫ������λ�ã��Ա��´η���
		return pivot;
	}

	/**
	 * ʵ����
	 *
	 * @param array ����������
	 * @param low ����������ָ��
	 * @param high ����������ָ��
	 * @param c �Ƚ���
	 * @return int ����������λ��
	 */
	private int partition3(E[] array, int low, int high, Comparator<E> c) {
		int pivot = low;//����Ԫ��λ�ã������Ե�һ��Ԫ��Ϊ����Ԫ��
		low++;
		//----�����ߵ�ָ����ָ���Ԫ��˳�򣬰�С������Ԫ�ص��Ƶ�ǰ���֣���������Ԫ�ص��Ƶ����沿��
		//�˳���������ֻ������ low = high

		while (true) {
			//�����ָ��δ������ָ��
			while (low < high) {
				//�����ָ��ָ���Ԫ�ش��ڻ��������Ԫ��ʱ��ʾ�ҵ��ˣ��˳���ע������ʱҲҪ����
				if (c.compare(array[low], array[pivot]) >= 0) {
					break;
				} else {//�����ָ��ָ���Ԫ��С������Ԫ��ʱ������
					low++;
				}
			}

			while (high > low) {
				//�����ָ��ָ���Ԫ��С������Ԫ��ʱ��ʾ�ҵ����˳�
				if (c.compare(array[high], array[pivot]) < 0) {
					break;
				} else {//�����ָ��ָ���Ԫ�ش�������Ԫ��ʱ������
					high--;
				}
			}
			//�˳�����ѭ��ʱ low = high
			if (low == high) {
				break;
			}

			swap(array, low, high);
		}

		//----�ߵ�ָ����ָ���Ԫ��������ɺ󣬻���Ҫ������Ԫ�طŵ��ʵ���λ��
		if (c.compare(array[pivot], array[low]) > 0) {
			//����˳�ѭ��ʱ����Ԫ�ش����˵�ָ����ָ��Ԫ��ʱ������Ԫ������lowԪ�ؽ���
			swap(array, low, pivot);
			pivot = low;
		} else if (c.compare(array[pivot], array[low]) <= 0) {
			swap(array, low - 1, pivot);
			pivot = low - 1;
		}

		//��������Ԫ������λ�ã��Ա��´η���
		return pivot;
	}

	/**
	* ����
	* @param args
	*/
	public static void main(String[] args) {
		Integer[] intgArr = { 3, 1, 1, 1, 1, 1, 1 };
		QuickSort<Integer> sort = new QuickSort<Integer>();
		QuickSort.testSort(sort, intgArr);
		QuickSort.testSort(sort, null);
	}
}
