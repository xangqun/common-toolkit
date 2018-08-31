package com.xangqun.sort.queue;

import java.util.NoSuchElementException;

/**
 * ʹ�� ѭ��˫���� ʵ�ֶ���
 *
 * @author jzj
 *
 * @param <E>
 */
public class LinkedQueue<E> implements Queue<E> {
	private class Entry {
		E element;//������
		Entry next;//�����ڵ�
		Entry previous;//ǰ���ڵ�

		/**
		 * @param element ������
		 * @param next �����ڵ�
		 * @param previous ǰ���ڵ�
		 */
		Entry(E element, Entry next, Entry previous) {
			this.element = element;
			this.next = next;
			this.previous = previous;
		}
	}

	/*
	 * ͷ�ڵ㣬��Զ����ͷ�������޽��ʱ�ڵ��е�ǰ����ָ����ָ���Լ�������Ԫ��ʱ
	 * ͷ�ڵ��ǰ��ָ����previousָ���������һ���ڵ㣬��nextָ����ָ�����е�
	 * ��һ���ڵ�
	 */
	private Entry header = new Entry(null, null, null);

	private int size = 0;//�����нڵ��������������ͷ�ڵ�header

	public LinkedQueue() {
		//��ʼ��ʱͷ��ǰ����ָ�붼ָ���Լ�
		header.next = header.previous = header;
	}

	//��ӣ��������е����λ�ü���Ԫ�أ��൱�� LinkedList ��add��addBefore��addLast����ʵ��
	public void enqueue(E e) {
		//��˫�����������Ԫ��
		Entry newEntry = new Entry(e, header, header.previous);
		//����Ԫ�ص�ǰ���ڵ㣨����ǰ�������㣩��ǰ��ָ���¼�Ԫ��
		newEntry.previous.next = newEntry;
		//����Ԫ�صĺ����ڵ㣨headerͷ�ڵ㣩��ǰ��ָ���¼�Ԫ��
		newEntry.next.previous = newEntry;
		size++;
	}

	//���ӣ��������еĵ�һ��Ԫ�ؿ�ʼ���ӣ��൱�� LinkedList ��removeFirst����ʵ��
	public E dequeue() {
		//Ҫ���ӣ�ɾ�����Ľڵ��������
		E first = header.next.element;
		Entry e = header.next;
		//Ҫɾ���Ľڵ�Ϊͷ�ڵ�ʱ����ɾ��
		if (e == header) {
			throw new NoSuchElementException();
		}

		//��ɾ���ڵ��ǰ���ڵ��next��ָ��ָ��ɾ���ڵ�ĺ����ڵ�
		e.previous.next = e.next;
		//��ɾ���ڵ�ĺ����ڵ��previous��ָ��ָ��ɾ���ڵ��ǰ���ڵ�
		e.next.previous = e.previous;
		size--;
		return first;
	}

	//ȡ���еĵ�һ��Ԫ�أ��൱�� LinkedList ��getFirst����ʵ��
	public E front() {
		if (size == 0)
			throw new NoSuchElementException();

		return header.next.element;
	}

	//�����Ƿ�Ϊ��
	public boolean isEmpty() {
		return size == 0;
	}

	//���д�С
	public int size() {
		return size;
	}
}
