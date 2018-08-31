package com.xangqun.sort.queue;

/**
 * ���нӿ�
 * @author jzj
 *
 * @param <E>
 */
public interface Queue<E> {
	//���
	public void enqueue(E e);

	//����
	public E dequeue();

	//ȡ���е�һ��
	public E front();

	//�����Ƿ�Ϊ��
	public boolean isEmpty();

	//���д�С
	public int size();
}