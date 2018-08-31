package com.xangqun.sort.queue;

import java.util.LinkedList;

/**
 * ʹ�� LinkedList ʵ�ֶ���
 * @author jzj
 *
 * @param <E>
 */
public class LinkedListQueue<E> implements Queue<E> {
	private LinkedList<E> linkedList = new LinkedList<E>();

	//���
	public void enqueue(E e) {
		linkedList.addLast(e);
	}

	//����
	public E dequeue() {
		return linkedList.removeFirst();
	}

	//ȡ���е�һ��
	public E front() {
		return linkedList.getFirst();
	}

	//�����Ƿ�Ϊ��
	public boolean isEmpty() {
		return linkedList.isEmpty();
	}

	//���д�С
	public int size() {
		return linkedList.size();
	}
}