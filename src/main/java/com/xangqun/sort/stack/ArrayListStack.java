package com.xangqun.sort.stack;

import java.util.ArrayList;

/**
 * ʹ�� ArrayList ʵ��ջ
 * @author jzj
 *
 * @param <E>
 */
public class ArrayListStack<E> implements Stack<E> {
	private ArrayList<E> list = new ArrayList<E>();

	//��ջ
	public void push(E e) {
		list.add(e);//ArrayListĬ�Ͼ���������β����Ԫ�أ��Ӻ����
	}

	//��ջ
	public E pop() {
		return list.remove(list.size() - 1);//��ǰ���
	}

	//ȡջ��Ԫ��
	public E peek() {
		return list.get(list.size() - 1);
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
