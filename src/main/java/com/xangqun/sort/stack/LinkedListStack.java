package com.xangqun.sort.stack;

import java.util.LinkedList;

/**
 * ʹ�� LinkedList ʵ��ջ
 * @author jzj
 *
 * @param <E>
 */
public class LinkedListStack<E> implements Stack<E> {
	private LinkedList<E> list = new LinkedList<E>();

	//��ջ
	public void push(E e) {
		list.addFirst(e);
	}

	//��ջ
	public E pop() {
		return list.removeFirst();
	}

	//ȡջ��Ԫ��
	public E peek() {
		return list.getFirst();
	}

	//��ǰջ��С
	public int size() {
		return list.size();
	}

	//ջ�Ƿ�Ϊ��
	public boolean isEmpty() {
		return list.isEmpty();
	}
}
