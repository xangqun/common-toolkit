package com.xangqun.sort.priorityqueue;

/**
 * ���ȼ����нӿ�
 *
 * @author jzj
 * @data 2010-1-5
 * @param <E>
 */
public interface PriorityQueue<E extends Comparable<E>> {
	int size();

	boolean isEmpty();

	//����м���Ԫ�أ����ʱ�ᰴ�����ȼ�������
	void add(E elem);

	//ȡ���ȸߵ�Ԫ��
	E getMin();

	//�����ȼ�������ɾ�����ȼ���ߵ�Ԫ��
	E removeMin();
}

