package com.xangqun.sort.stack;

/**
 * ʹ�� ���� ʵ��ջ
 * @author jzj
 *
 * @param <E>
 */
public class ArrayStack<E> implements Stack<E> {
	private E[] data;
	private int top = -1;//ָ��ջ��Ԫ��
	private int size = 3;

	public ArrayStack() {
		data = (E[]) new Object[size];
	}

	//��ջ
	public void push(E e) {
		if ((top + 1) == size) {
			throw new IllegalStateException("stack full...");
		}
		data[++top] = e;
	}

	//��ջ
	public E pop() {
		if (top == -1) {
			throw new IllegalStateException("stack empty...");
		}
		E tmp = data[top];
		//������ͷŶ��󣬼ӿ��������գ���ֹ��Ķ����÷����ڴ�й¶
		data[top] = null;
		top--;
		return tmp;
	}

	//ȡջ��Ԫ��
	public E peek() {
		if (top == -1) {
			throw new IllegalStateException("stack empty...");
		}
		return data[top];
	}

	//��ǰջ��С
	public int size() {
		return top + 1;
	}

	//ջ�Ƿ�Ϊ��
	public boolean isEmpty() {
		return top == -1;
	}
}
